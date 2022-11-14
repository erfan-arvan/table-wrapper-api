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
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@ToString(of = "column")
@EqualsAndHashCode(of = "column")
@RequiredArgsConstructor(access = PRIVATE)
public class OptionalTableColumn implements TableColumn {
    private final TableColumn column;
    private final AnyOfTableColumn anyOfTableColumn;

    public static OptionalTableColumn of(TableColumn column) {
        return new OptionalTableColumn(column, AnyOfTableColumn.of(column, TableColumn.NOCOLUMN));
    }

    @Override
    public int getColumnIndex(int firstColumnForSearch, ReportPageRow... headerRows) {
        try {
            return anyOfTableColumn.getColumnIndex(firstColumnForSearch, headerRows);
        } catch (Exception e) {
            throw new OptionalTableColumnNotFound(e);
        }
    }
}
