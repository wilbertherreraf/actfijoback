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
package gob.gamo.activosf.app.depre.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Converts java.time.LocalDate to java.time.YearMonth
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Component("localDateToYearMonthConverter")
public class LocalDateToYearMonthConverter implements Converter<LocalDate, YearMonth> {

    private static final Logger log = LoggerFactory.getLogger(LocalDateToYearMonthConverter.class);

    /**
     * {@inheritDoc}
     * <p>
     * Convert the source object of type {@code S} to target type {@code T}.
     */
    @Override
    public YearMonth convert(LocalDate source) {

        YearMonth convertedMonth;

        log.trace("Converting {} to YearMonth", source.toString());

        try {
            checkIfSourceIsNull(source);
        } catch (Exception e) {
            return convert(); //TODO ... Will review this later
        }

        try {

            convertedMonth = YearMonth.from(Objects.requireNonNull(source)); // Jus being careful

        } catch (Throwable e) {
            throw new ConverterException(String.format("Exception thrown while converting %s to YearMonth", source), e);
        }

        return convertedMonth;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws java.lang.IllegalArgumentException if the source cannot be converted to the desired target type
     */
    public YearMonth convert() {
        return convert(LocalDate.now());
    }

    private void checkIfSourceIsNull(LocalDate date) {
        if (date == null) {
            throw new ConverterException("The date provided is null, kindly review the source data again...");
        }
    }
}
