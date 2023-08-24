/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.utils.TransactionUtil;

/**
 *
 * @author wherrera
 */
public abstract class AbstractDao<E> {

    private Class<E> clazz;
    private Field primaryKey;

    public AbstractDao(Class<E> clazz) {
        this.clazz = clazz;

        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Id.class) != null) {
                    primaryKey = field;
                    primaryKey.setAccessible(true);
                    break;
                }
            }
        } catch (SecurityException | IllegalArgumentException ex) {
            throw new DataException(
                    "Excepción al intentar obtener la llave primaria de la entidad: " + clazz
                            + " . ¿La entidad tiene un atributo anotado con '@Id'?",
                    ex);
        }
    }

    protected abstract EntityManager getEntityManager();

    public void delete(E e, TxTransaccion tx) {
        try {
            Method method = clazz.getMethod("setEstado", String.class);
            method.invoke(e, StatusEnum.INACTIVE.getStatus());
            merge(e, tx, false);
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            throw new DataException(
                    "Excepción al intentar establecer el estado de la entidad: " + clazz
                            + " . ¿La entidad tiene el metodo 'setEstado(String)' ?",
                    ex);
        }
    }

    public void merge(E e, TxTransaccion tx, boolean refresh) {
        Object pk = null;
        try {
            pk = primaryKey.get(e);
            if (pk == null) {
                throw new DataException("Excepción al intentar obtener la llave primaria de la entidad: " + clazz
                        + " . La llave primaria recuperada es null de la Entidad: " + e);
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException ex) {
            throw new DataException(
                    "Excepción al intentar obtener la llave primaria de la entidad: " + clazz
                            + " . ¿La entidad tiene un atributo anotado con '@Id'? Entidad: " + e,
                    ex);
        }
        E other = findByPk(pk);
        if (other != null) {
            TransactionUtil.setUpdateTransactionData(e, tx);
            getEntityManager().persist(e);
            if (refresh) {
                getEntityManager().flush();
                getEntityManager().refresh(e);
            }
        } else {
            throw new DataException("No se puede modificar un registro que no existe, la entidad: " + clazz
                    + " con PK: " + pk + " . No existe en la Base de Datos.");
        }
    }

    public E findByPk(Object pk) {
        return getEntityManager().find(clazz, pk);
    }

    public void remove(E e) {
        getEntityManager().remove(e);
        getEntityManager().flush();
    }

    public void persist(E e, TxTransaccion tx, boolean refresh) {
        // TransactionUtil.setInitTransactionData(e, tx);
        try {
            Method method = clazz.getMethod("setEstado", String.class);
            method.invoke(e, StatusEnum.ACTIVE.getStatus());
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            throw new DataException(
                    "Excepción al intentar establecer el estado de la entidad: " + clazz
                            + " . ¿La entidad tiene el metodo 'setEstado(String)' ?",
                    ex);
        }
        getEntityManager().persist(e);
        if (refresh) {
            getEntityManager().flush();
            getEntityManager().refresh(e);
        }
    }

    public List<E> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> rootEntry = cq.from(clazz);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }

    public List<E> findAllActives() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> rootEntry = cq.from(clazz);
        Predicate activeStatus = cb.equal(rootEntry.get("estado"), StatusEnum.ACTIVE.getStatus());
        cq.where(activeStatus);
        CriteriaQuery<E> all = cq.select(rootEntry).orderBy(cb.desc(rootEntry.get(primaryKey.getName())));
        TypedQuery<E> allQuery = getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }

    public List<E> findAllActives(int offset, Integer pageSize) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> rootEntry = cq.from(clazz);
        Predicate activeStatus = cb.equal(rootEntry.get("estado"), StatusEnum.ACTIVE.getStatus());
        cq.where(activeStatus);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = getEntityManager().createQuery(all);
        allQuery.setFirstResult(offset);
        allQuery.setMaxResults(pageSize);
        return allQuery.getResultList();
    }

    public List<E> findAllActives(int offset, Integer pageSize, Map<String, Object> filter) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(clazz);
        Root<E> rootEntry = cq.from(clazz);
        Predicate activeStatus = cb.equal(rootEntry.get("estado"), StatusEnum.ACTIVE.getStatus());
        predicates.add(activeStatus);
        CriteriaQuery<E> all = cq.select(rootEntry);
        Set<String> keys = filter.keySet();
        for (String key : keys) {
            if (filter.get(key) instanceof String) {
                String pattern = (String) filter.get(key);
                Predicate like = cb.like(rootEntry.<String>get(key), removeWildcard(pattern) + "%");
                predicates.add(like);
            } else {
                Predicate equal = cb.equal(rootEntry.get(key), filter.get(key));
                predicates.add(equal);
            }
        }
        cq.where((Predicate[]) predicates.toArray());
        TypedQuery<E> allQuery = getEntityManager().createQuery(all);
        allQuery.setFirstResult(offset);
        allQuery.setMaxResults(pageSize);
        return allQuery.getResultList();
    }

    protected String removeWildcard(String string) {
        return string.replace('_', '\u0000').replace('%', '\u0000').trim();
    }
}
