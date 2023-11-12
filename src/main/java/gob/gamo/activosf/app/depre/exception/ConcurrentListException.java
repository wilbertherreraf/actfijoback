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
package gob.gamo.activosf.app.depre.exception;

class ConcurrentListException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    ConcurrentListException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified location (index) where the element is being added and the element itself
     *
     * @param index   where we are attempting to add an element
     * @param element The element being added to the list
     * @param <T>     Type of element being added to the list
     */
    <T> ConcurrentListException(int index, T element) {
        super(String.format("Exception encountered while adding %s to index %s", element, index));
    }
}
