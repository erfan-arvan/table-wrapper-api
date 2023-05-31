/*
 * Table Wrapper API
 * Copyright (C) 2020  Spacious Team <spacious-team@ya.ru>
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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static java.lang.Integer.MIN_VALUE;

/**
 * Zero-based table cell range
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class TableCellRange {
    public static final TableCellRange EMPTY_RANGE = new EmptyTableCellRange();

    private final int firstRow;
    private final int lastRow;
    private final int firstColumn;
    private final int lastColumn;

    public static TableCellRange of(TableCellAddress upperLeft, TableCellAddress bottomRight) {
        return of(
                upperLeft.getRow(),
                bottomRight.getRow(),
                upperLeft.getColumn(),
                bottomRight.getColumn());
    }

    public boolean contains(TableCellAddress address) {
        return containsRow(address.getRow()) && containsColumn(address.getColumn());
    }

    public boolean containsRow(int row) {
        return firstRow <= row && row <= lastRow;
    }

    public boolean containsColumn(int column) {
        return firstColumn <= column && column <= lastColumn;
    }

    /**
     * Adds rows without range check. First rows index of range may become negative.
     *
     * @param number positive or negative values
     */
    public TableCellRange addRowsToTop(int number) {
        return TableCellRange.of(firstRow - number, lastRow, firstColumn, lastColumn);
    }

    /**
     * @param number positive or negative values
     */
    public TableCellRange addRowsToBottom(int number) {
        return TableCellRange.of(firstRow, lastRow + number, firstColumn, lastColumn);
    }

    /**
     * Adds columns without range check. First column index of range may become negative.
     *
     * @param number positive or negative values
     */
    @SuppressWarnings("unused")
    public TableCellRange addColumnsToLeft(int number) {
        return TableCellRange.of(firstRow, lastRow, firstColumn - number, lastColumn);
    }

    /**
     * @param number positive or negative values
     */
    @SuppressWarnings("unused")
    public TableCellRange addColumnsToRight(int number) {
        return TableCellRange.of(firstRow, lastRow, firstColumn, lastColumn + number);
    }

    private static class EmptyTableCellRange extends TableCellRange {

        private EmptyTableCellRange() {
            super(MIN_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE);
        }

        @Override
        public boolean contains(TableCellAddress address) {
            return false;
        }

        @Override
        public boolean containsRow(int row) {
            return false;
        }

        @Override
        public boolean containsColumn(int column) {
            return false;
        }

        @Override
        public TableCellRange addRowsToTop(int number) {
            return this;
        }

        @Override
        public TableCellRange addRowsToBottom(int number) {
            return this;
        }

        @Override
        public TableCellRange addColumnsToLeft(int number) {
            return this;
        }

        @Override
        public TableCellRange addColumnsToRight(int number) {
            return this;
        }
    }
}
