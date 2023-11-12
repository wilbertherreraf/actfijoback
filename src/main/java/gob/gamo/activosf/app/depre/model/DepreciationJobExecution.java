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


import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;

import jakarta.persistence.Entity;

/**
 * Model that represents a depreciation unit of work done that can be recorded in the database
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Entity(name = "DepreciationJobExecution")
public class DepreciationJobExecution  {

    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private LocalDateTime lastUpdatedTime;
    private String status;
    private int noOfItems;
    private YearMonth period;

    /**
     * <p>Constructor for DepreciationJobExecution.</p>
     */
    public DepreciationJobExecution() {
    }

    /**
     * <p>Getter for the field <code>startTime</code>.</p>
     *
     * @return a {@link java.time.LocalDateTime} object.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * <p>Setter for the field <code>startTime</code>.</p>
     *
     * @param startTime a {@link java.time.LocalDateTime} object.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * <p>Getter for the field <code>finishTime</code>.</p>
     *
     * @return a {@link java.time.LocalDateTime} object.
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * <p>Setter for the field <code>finishTime</code>.</p>
     *
     * @param finishTime a {@link java.time.LocalDateTime} object.
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * <p>Getter for the field <code>lastUpdatedTime</code>.</p>
     *
     * @return a {@link java.time.LocalDateTime} object.
     */
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * <p>Setter for the field <code>lastUpdatedTime</code>.</p>
     *
     * @param lastUpdatedTime a {@link java.time.LocalDateTime} object.
     */
    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /**
     * <p>Getter for the field <code>status</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>Setter for the field <code>status</code>.</p>
     *
     * @param status a {@link java.lang.String} object.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>Getter for the field <code>noOfItems</code>.</p>
     *
     * @return a int.
     */
    public int getNoOfItems() {
        return noOfItems;
    }

    /**
     * <p>Setter for the field <code>noOfItems</code>.</p>
     *
     * @param noOfItems a int.
     */
    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    /**
     * <p>Getter for the field <code>period</code>.</p>
     *
     * @return a {@link java.time.YearMonth} object.
     */
    public YearMonth getPeriod() {
        return period;
    }

    /**
     * <p>Setter for the field <code>period</code>.</p>
     *
     * @param period a {@link java.time.YearMonth} object.
     */
    public void setPeriod(YearMonth period) {
        this.period = period;
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
        DepreciationJobExecution that = (DepreciationJobExecution) o;
        return noOfItems == that.noOfItems && Objects.equals(startTime, that.startTime) && Objects.equals(finishTime, that.finishTime) && Objects.equals(lastUpdatedTime, that.lastUpdatedTime) &&
            Objects.equals(status, that.status) && Objects.equals(period, that.period);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startTime, finishTime, lastUpdatedTime, status, noOfItems, period);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DepreciaionJobExecution{");
        sb.append("startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", lastUpdatedTime=").append(lastUpdatedTime);
        sb.append(", status='").append(status).append('\'');
        sb.append(", noOfItems=").append(noOfItems);
        sb.append(", period=").append(period);
        sb.append('}');
        return sb.toString();
    }
}
