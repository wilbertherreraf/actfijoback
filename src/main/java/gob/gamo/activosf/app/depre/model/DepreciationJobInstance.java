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
package gob.gamo.activosf.app.depre.model;


import java.time.YearMonth;
import java.util.Comparator;
import java.util.Objects;

import jakarta.persistence.Column;

/**
 * A record of a depreciation job that is to be saved in the database
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
//@Entity(name = "DepreciationJobInstance")
public class DepreciationJobInstance  implements Comparable<DepreciationJobInstance> {

    @Column(name = "month")
    private YearMonth month;

    /**
     * <p>Constructor for DepreciationJobInstance.</p>
     */
    public DepreciationJobInstance() {
    }

    /**
     * <p>Constructor for DepreciationJobInstance.</p>
     *
     * @param month a {@link java.time.YearMonth} object.
     */
    public DepreciationJobInstance(YearMonth month) {
        this.month = month;
    }

    /**
     * <p>Getter for the field <code>month</code>.</p>
     *
     * @return a {@link java.time.YearMonth} object.
     */
    public YearMonth getMonth() {
        return month;
    }

    /**
     * <p>Setter for the field <code>month</code>.</p>
     *
     * @param month a {@link java.time.YearMonth} object.
     */
    public void setMonth(YearMonth month) {
        this.month = month;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DepreciationJobInstance that = (DepreciationJobInstance) o;
        return Objects.equals(month, that.month);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), month);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DepreciationJobInstance{");
        sb.append("month=").append(month);
        sb.append('}');
        return sb.toString();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(DepreciationJobInstance o) {
        return Comparator.comparing(DepreciationJobInstance::getMonth).compare(this, o);
    }
}
