// Generated by delombok at Tue Aug 20 15:02:38 EDT 2024
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
import javax.annotation.Nullable;
public class ConstantPositionTableColumn implements TableColumn {

    private final int columnIndex;

    @Override
    public int getColumnIndex(@Nullable int firstColumnForSearch, @Nullable ReportPageRow... headerRows) {
        return columnIndex;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ConstantPositionTableColumn(columnIndex=" + this.columnIndex + ")";
    }

    @java.lang.SuppressWarnings("all")
    private ConstantPositionTableColumn(final int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @java.lang.SuppressWarnings("all")
    public static ConstantPositionTableColumn of(final int columnIndex) {
        return new ConstantPositionTableColumn(columnIndex);
    }
}
