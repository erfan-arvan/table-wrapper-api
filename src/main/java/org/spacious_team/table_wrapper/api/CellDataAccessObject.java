/*
 * Table Wrapper API
 * Copyright (C) 2021  Spacious Team <spacious-team@ya.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.spacious_team.table_wrapper.api;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.spacious_team.table_wrapper.api.CellDataAccessObjectHelper.*;

/**
 * @apiNote Impl may have parameters that affect how the value is parsed,
 * for example DataTimeFormat that changes behavior of date time value parser.
 */
public interface CellDataAccessObject<C, R extends ReportPageRow> {

    @Nullable
    C getCell(R row, Integer cellIndex);

    @Nullable
    Object getValue(C cell);

    /**
     * @throws RuntimeException if method can't extract int value
     */
    default int getIntValue(C cell) {
        return (int) getLongValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract long value
     */
    default long getLongValue(C cell) {
        @Nullable Object value = getValue(cell);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value != null) {
            String str = spacePattern.matcher(value.toString()).replaceAll("");
            return Long.parseLong(str);
        } else {
            throw new NullPointerException(NO_CELL_VALUE_EXCEPTION_MESSAGE);
        }
    }

    /**
     * @throws RuntimeException if method can't extract Double value
     */
    default double getDoubleValue(C cell) {
        @Nullable Object value = getValue(cell);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value != null) {
            String str = spacePattern.matcher(value.toString()).replaceAll("");
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                if (str.indexOf(',') != -1) {
                    return Double.parseDouble(str.replace(',', '.'));
                }
                throw e;
            }
        } else {
            throw new NullPointerException(NO_CELL_VALUE_EXCEPTION_MESSAGE);
        }
    }

    /**
     * @throws RuntimeException if method can't extract BigDecimal value
     * @see <a href="https://stackoverflow.com/questions/6787142/bigdecimal-equals-versus-compareto">Stack overflow</a>
     * for BigDecimal values equality
     */
    default BigDecimal getBigDecimalValue(C cell) {
        String number = getStringValue(cell);
        number = spacePattern.matcher(number).replaceAll("");
        number = number.replace(',', '.');
        return (Objects.equals(number, "0") || Objects.equals(number, "0.0") || Objects.equals(number, "0.00")) ?
                BigDecimal.ZERO : new BigDecimal(number);
    }

    /**
     * @throws RuntimeException if method can't extract string value
     */

    default String getStringValue(C cell) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        Object value = requireNonNull(getValue(cell), "Not a string");
        return value.toString();
    }

    /**
     * @throws RuntimeException if method can't extract instant value
     */
    Instant getInstantValue(C cell);

    /**
     * Returns local date time at system default time zone.
     *
     * @throws RuntimeException if method can't extract local date time value
     */
    default LocalDateTime getLocalDateTimeValue(C cell) {
        return getLocalDateTimeValue(cell, defaultZoneId);
    }

    /**
     * @throws RuntimeException if method can't extract local date time value
     */
    default LocalDateTime getLocalDateTimeValue(C cell, ZoneId zoneId) {
        return getInstantValue(cell)
                .atZone(zoneId)
                .toLocalDateTime();
    }

    default @Nullable Object getValue(R row, Integer cellIndex) {
        @Nullable C cell = getCell(row, cellIndex);
        return (cell == null) ? null : getValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract int value
     */
    default int getIntValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getIntValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract long value
     */
    default long getLongValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getLongValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract Double value
     */
    default double getDoubleValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getDoubleValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract BigDecimal value
     */
    default BigDecimal getBigDecimalValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getBigDecimalValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract string value
     */
    default String getStringValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getStringValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract instant value
     */
    default Instant getInstantValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getInstantValue(cell);
    }

    /**
     * Returns local date time at default time zone.
     *
     * @throws RuntimeException if method can't extract local date time value
     */
    default LocalDateTime getLocalDateTimeValue(R row, Integer cellIndex) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getLocalDateTimeValue(cell);
    }

    /**
     * @throws RuntimeException if method can't extract local date time value
     */
    @SuppressWarnings("UnusedReturnValue")
    default LocalDateTime getLocalDateTimeValue(R row, Integer cellIndex, ZoneId zoneId) {
        @SuppressWarnings({"nullness", "ConstantConditions"})
        C cell = requireNonNull(getCell(row, cellIndex), "Cell is not found");
        return getLocalDateTimeValue(cell, zoneId);
    }
}
