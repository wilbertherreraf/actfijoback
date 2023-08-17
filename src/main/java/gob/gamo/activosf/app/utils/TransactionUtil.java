package gob.gamo.activosf.app.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import gob.gamo.activosf.app.domain.TxTransaccion;


public class TransactionUtil {
	private TransactionUtil() {
	}
	
	static public void setInitTransactionData(Object e, TxTransaccion tx){
		Class clazz =  e.getClass();
		try {
			Method setIdTransaccion = clazz.getMethod("setIdTransaccion", int.class);
			setIdTransaccion.invoke(e, tx.getIdTransaccion().intValue());
			Method setTxFchIni = clazz.getMethod("setTxFchIni", Date.class);
			setTxFchIni.invoke(e, tx.getTxFecha());
			Method setTxUsrIni = clazz.getMethod("setTxUsrIni", int.class);
			setTxUsrIni.invoke(e, tx.getTxUsuario());
			Method setTxHostIni = clazz.getMethod("setTxHostIni", String.class);
			setTxHostIni.invoke(e, tx.getTxHost());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new RuntimeException("Excepción al intentar llenar los atributos transaccionales de la entidad: " + clazz 
					+ " ¿Esta seguro que esta entidad tiene los atributos transaccionales?",ex);
		}
	}
	
	static public void setUpdateTransactionData(Object e, TxTransaccion tx){
		Class clazz =  e.getClass();
		try {
			Method setIdTransaccion = clazz.getMethod("setIdTransaccion", int.class);
			setIdTransaccion.invoke(e, tx.getIdTransaccion().intValue());
			Method setTxFchMod = clazz.getMethod("setTxFchMod", Date.class);
			setTxFchMod.invoke(e, tx.getTxFecha());
			Method setTxUsrMod = clazz.getMethod("setTxUsrMod", Integer.class);
			setTxUsrMod.invoke(e, new Integer(tx.getTxUsuario()));
			Method setTxHostMod = clazz.getMethod("setTxHostMod", String.class);
			setTxHostMod.invoke(e, tx.getTxHost());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new RuntimeException("Excepción al intentar llenar los atributos transaccionales de la entidad: " + clazz 
					+ " ¿Esta seguro que esta entidad tiene los atributos transaccionales?",ex);
		}
	}
	
}
