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



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

/**
 * This utility is used in a Collector operation in a lambda to generate an immutable collection
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class ImmutableListCollector {

    /**
     * <p>toImmutableList.</p>
     *
     * @param <t> a t object.
     * @return a {@link java.util.stream.Collector} object.
     */
    public static <t> Collector<t, List<t>, List<t>> toImmutableList() {
        return Collector.of(ArrayList::new, List::add, (left, right) -> {
            left.addAll(right);
            return left;
        }, Collections::unmodifiableList, Collector.Characteristics.CONCURRENT);
    }

    /**
     * <p>toImmutableFastList.</p>
     *
     * @param <t> a t object.
     * @return a {@link java.util.stream.Collector} object.
     */
    public static <t> Collector<t, List<t>, List<t>> toImmutableFastList() {
        return Collector.of(ArrayList::new, List::add, (left, right) -> {
            left.addAll(right);
            return left;
        }, Collections::unmodifiableList, Collector.Characteristics.CONCURRENT);
    }
}
