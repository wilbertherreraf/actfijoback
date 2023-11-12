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
package gob.gamo.activosf.app.depre.model.nil;

import org.javamoney.moneta.Money;

import gob.gamo.activosf.app.depre.model.FixedAsset;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This object contains fields for an improperly setup fixedAsset items with
 * their default values, and is used whenever a NullPointerSituation is likely
 * to occur
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class NilFixedAsset extends FixedAsset {

    /**
     * <p>
     * Constructor for NilFixedAsset.
     * </p>
     */
    public NilFixedAsset() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSolId() {
        return "998";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBarcode() {
        return "ABC000####";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAssetDescription() {
        return "Nil Asset Item. Please add details";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getPurchaseDate() {
        return LocalDate.now();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCategory() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Money getPurchaseCost() {
        return Money.of(0.00, "BBS");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Money getNetBookValue() {
        return Money.of(0.00, "KES");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return 0;
    }

/* 
    @Override
    public int getVersion() {
        return 0;
    }

   
    @Override
    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }

   
    @Override
    public LocalDateTime getModifiedAt() {
        return LocalDateTime.now();
    }

   
    @Override
    public String getCreatedBy() {
        return "developer";
    }

   
    @Override
    public String getLastModifiedBy() {
        return "developer";
    } */
}
