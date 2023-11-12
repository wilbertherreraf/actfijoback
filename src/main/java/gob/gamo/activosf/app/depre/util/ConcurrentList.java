/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package gob.gamo.activosf.app.depre.util;

import com.google.common.collect.ForwardingList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import gob.gamo.activosf.app.depre.exception.*;

/**
 * This is a list backed by a concurrent hashmap making it effectively
 * concurrent. The list is also substantially logged with 'tracer' methods to
 * effectively log, trace and debug complex operations.
 * <br>
 * If the list is supposed to remain empty, invoking the list through the
 * {@link io.github.fasset.fasset.kernel.util.ConcurrentList#empty()} method
 * ensures the {@link
 * io.github.fasset.fasset.kernel.util.ConcurrentList#add(Object)} and the
 * {@link ConcurrentList#add(int, Object)} methods do not throw an excetion when
 * called
 *
 * @param <T> Type of elements to be stored in the list
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class ConcurrentList<T> extends ForwardingList<T> implements List<T> {

    private static final Logger log = LoggerFactory.getLogger(ConcurrentList.class);
    // means the list is supposed to remain empty
    private static boolean shouldRemainEmpty;
    private final Map<Integer, T> mapList = new ConcurrentHashMap<>();
    private int index;

    /**
     * <p>
     * Constructor for ConcurrentList.
     * </p>
     */
    public ConcurrentList() {

        index = 0;

        shouldRemainEmpty = false;

        log.trace("New list initialised {}", this);
    }

    private ConcurrentList(boolean shouldRemainEmpty) {

        index = 0;

        this.shouldRemainEmpty = shouldRemainEmpty;

        log.trace("New list initialised {}", this);
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param elements a T object.
     * @param <T>      a T object.
     * @return a {@link java.util.List} object.
     */
    @SafeVarargs
    public static <T> List<T> of(T... elements) {

        final List<T> newList = new ConcurrentList<>();

        newList.addAll(Arrays.asList(elements));

        return newList;
    }

    /**
     * <p>
     * empty.
     * </p>
     *
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> empty() {

        return new ConcurrentList<>(true);
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param listItems a {@link java.util.List} object.
     * @param <T>       a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(List<T> listItems) {

        List<T> newList = ConcurrentList.newList();

        newList.addAll(listItems);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1) {

        final List<T> newList = ConcurrentList.newList();

        newList.add(t1);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param t5  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4, T t5) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);
        newList.add(t5);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param t5  a T object.
     * @param t6  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4, T t5, T t6) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);
        newList.add(t5);
        newList.add(t6);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param t5  a T object.
     * @param t6  a T object.
     * @param t7  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);
        newList.add(t5);
        newList.add(t6);
        newList.add(t7);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param t5  a T object.
     * @param t6  a T object.
     * @param t7  a T object.
     * @param t8  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);
        newList.add(t5);
        newList.add(t6);
        newList.add(t7);
        newList.add(t8);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param t5  a T object.
     * @param t6  a T object.
     * @param t7  a T object.
     * @param t8  a T object.
     * @param t9  a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);
        newList.add(t5);
        newList.add(t6);
        newList.add(t7);
        newList.add(t8);
        newList.add(t9);

        return newList;
    }

    /**
     * <p>
     * of.
     * </p>
     *
     * @param t1  a T object.
     * @param t10 a T object.
     * @param t2  a T object.
     * @param t3  a T object.
     * @param t4  a T object.
     * @param t5  a T object.
     * @param t6  a T object.
     * @param t7  a T object.
     * @param t8  a T object.
     * @param t9  a T object.
     * @param t10 a T object.
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> of(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {

        final List<T> newList = new ConcurrentList<>();

        newList.add(t1);
        newList.add(t2);
        newList.add(t3);
        newList.add(t4);
        newList.add(t5);
        newList.add(t6);
        newList.add(t7);
        newList.add(t8);
        newList.add(t9);
        newList.add(t10);

        return newList;
    }

    /**
     * <p>
     * newList.
     * </p>
     *
     * @param <T> a T object.
     * @return a {@link java.util.List} object.
     */
    public static <T> List<T> newList() {
        return new ConcurrentList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<T> delegate() {
        return new CopyOnWriteArrayList<>();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a sequential {@code Stream} with this collection as its source.
     * <p>
     * This method should be overridden when the {@link #spliterator()} method
     * cannot return a spliterator that is {@code IMMUTABLE}, {@code CONCURRENT}, or
     * <em>late-binding</em>. (See {@link
     * #spliterator()} for details.)
     *
     * @since 1.8
     */
    @Override
    public Stream<T> stream() {

        return new CopyOnWriteArrayList<>(this.mapList.values()).stream();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a possibly parallel {@code Stream} with this collection as its
     * source. It is allowable for this method to return a sequential stream.
     * <p>
     * This method should be overridden when the {@link #spliterator()} method
     * cannot return a spliterator that is {@code IMMUTABLE}, {@code CONCURRENT}, or
     * <em>late-binding</em>. (See {@link
     * #spliterator()} for details.)
     *
     * @since 1.8
     */
    @Override
    public Stream<T> parallelStream() {

        return this.mapList.values().parallelStream();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates a {@link Spliterator} over the elements in this list.
     * <p>
     * The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#ORDERED}. Implementations should document the reporting of
     * additional characteristic values.
     *
     * @since 1.8
     */
    @Override
    public Spliterator<T> spliterator() {

        return this.mapList.values().spliterator();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Performs the given action for each element of the {@code Iterable} until all
     * elements have been processed or the action throws an exception. Unless
     * otherwise specified by the implementing
     * class, actions are performed in the order of iteration (if an iteration order
     * is specified). Exceptions thrown by the action are relayed to the caller.
     *
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super T> action) {

        this.mapList.values().forEach(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> collection) {

        if (shouldRemainEmpty) {
            return false;
        }

        return collection.stream().allMatch(this::remove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {

        if (shouldRemainEmpty) {
            // throw new RuntimeException(index, collection.stream().findFirst().get());
        }

        return collection.stream().allMatch(this::add);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object object) {

        if (shouldRemainEmpty) {
            return false;
        }

        T val = (T) object;

        log.trace("Checking if the list contains element : {}", val);

        return mapList.containsValue(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T set(int index, T element) {

        if (shouldRemainEmpty) {
            // throw new RuntimeException(index, element);
        }

        log.trace("Adding element {} @ index {}", element, index);

        this.mapList.put(index, element);

        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {

        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns zero id the element is missing in the list
     */
    @Override
    public int indexOf(Object element) {

        if (shouldRemainEmpty) {
            // throw new ElementNotFoundException(element);
        }

        log.trace("Checking the index of the element {}", element);

        int index = 0;

        if (!mapList.containsValue(element)) {
            log.trace("The list does not contain the element {}", element);
            // throw new ElementNotFoundException(element);
        }

        for (int i = 0; i < mapList.size(); i++) {

            if (mapList.get(i) == element) {
                index = i;
                log.trace("Element {} found at index {}", element, index);
            }
        }

        log.trace("Element {} found at index {}", element, index);

        return index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {

        log.trace("Returning size of the concurrent list {}", mapList.size());

        return mapList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {

        return mapList.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, T element) {

        if (shouldRemainEmpty) {
            // throw new RuntimeException(index, element);
        }

        log.trace("Adding the element {} to index position {}", element, index);

        this.mapList.put(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object object) {

        if (shouldRemainEmpty) {
            // throw new ElementNotFoundException((T) object);
        }

        if (!mapList.containsValue((T) object)) {
            return false;
        }

        Integer ok = mapList.keySet().stream().filter(k -> {
            return mapList.get(k) == (T) object;
        }).findFirst().orElse(0);

        mapList.remove(ok);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(int index) {

        if (shouldRemainEmpty) {
            // throw new ElementNotFoundException(index);
        }

        T removable = mapList.remove(index);

        log.trace("Element {} has been removed from index {}", removable, index);

        return removable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(int index) {

        if (shouldRemainEmpty) {
            // throw new ElementNotFoundException(index);
        }

        T atIndex = mapList.get(index);

        log.trace("The element {} has been fetched at index {}", atIndex, index);

        return atIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T element) {

        if (shouldRemainEmpty) {
            // throw new RuntimeException(index, element);
        }

        try {
            mapList.put(index++, element);
        } catch (RuntimeException e) {
            // throw new ConcurrentListException(index, element);
        }

        log.trace("The element {} has been added at index {}", element, index);

        return mapList.size() >= index;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes all of the elements of this collection that satisfy the given
     * predicate. Errors or runtime exceptions thrown during iteration or by the
     * predicate are relayed to the caller.
     *
     * @since 1.8
     */
    @Override
    public boolean removeIf(Predicate<? super T> filter) {

        return this.mapList.values().removeIf(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> elements) {

        if (index > this.index) {
            // throw new IndexBeyondBoundsException(index, this.index,
            // elements.stream().findFirst().get());
        }

        log.trace("Adding {} elements from index {}", elements.size(), index);

        return elements.stream().allMatch(element -> this.add0(index, element));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {

        final List<T> subList = ConcurrentList.newList();

        int sublistSize = toIndex - fromIndex + 1;

        for (int i = 0; i < sublistSize; i++) {

            subList.add(mapList.get(fromIndex++));
        }

        return subList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        final int[] hashcode = { 0 };

        mapList.values().forEach(i -> hashcode[0] = +i.hashCode() * 31);

        return hashcode[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {

        if (!(object instanceof List)) {
            return false;
        }

        List<T> equiv;

        try {
            equiv = (List<T>) object;
        } catch (Exception e) {
            return false;
        }

        return equiv.containsAll(this.mapList.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> collection) {

        return collection.stream().allMatch(this::contains);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {

        log.trace("Clearing up the list...");

        if (shouldRemainEmpty) {

            return;
        }

        this.index = 0;

        this.mapList.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<T> listIterator() {

        return new CopyOnWriteArrayList<T>(this.mapList.values()).listIterator();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sorts this list according to the order induced by the specified
     * {@link Comparator}.
     * <p>
     * All elements in this list must be <i>mutually comparable</i> using the
     * specified comparator (that is, {@code c.compare(e1, e2)} must not throw a
     * {@code ClassCastException} for any elements
     * {@code e1} and {@code e2} in the list).
     * <p>
     * If the specified comparator is {@code null} then all elements in this list
     * must implement the {@link Comparable} interface and the elements'
     * {@linkplain Comparable natural ordering} should
     * be used.
     * <p>
     * This list must be modifiable, but need not be resizable.
     *
     * @since 1.8
     */
    @Override
    public void sort(Comparator<? super T> c) {

        List<T> originalValues = new CopyOnWriteArrayList<>(this.mapList.values());

        originalValues.sort(c);

        this.clear();

        this.addAll(originalValues);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {

        return this.mapList.values().toArray();
    }

    private boolean add0(int index, T element) {
        try {
            add(index, element);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
