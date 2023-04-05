/*
 * Table Wrapper API
 * Copyright (C) 2022  Spacious Team <spacious-team@ya.ru>
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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AbstractTableCellTest {

    @Mock
    CellDataAccessObject<Object, ?> dao;
    @Mock
    Object cellValue;
    TableCellTestImpl cell;

    @BeforeEach
    void setUp() {
        cell = new TableCellTestImpl(cellValue, dao);
    }

    @Test
    void getValue() {
        cell.getValue();
        Mockito.verify(dao).getValue(cellValue);
    }

    @Test
    void getIntValue() {
        cell.getIntValue();
        Mockito.verify(dao).getIntValue(cellValue);
    }

    @Test
    void getLongValue() {
        cell.getLongValue();
        Mockito.verify(dao).getLongValue(cellValue);
    }

    @Test
    void getDoubleValue() {
        cell.getDoubleValue();
        Mockito.verify(dao).getDoubleValue(cellValue);
    }

    @Test
    void getBigDecimalValue() {
        cell.getBigDecimalValue();
        Mockito.verify(dao).getBigDecimalValue(cellValue);
    }

    @Test
    void getStringValue() {
        cell.getStringValue();
        Mockito.verify(dao).getStringValue(cellValue);
    }

    @Test
    void getInstantValue() {
        cell.getInstantValue();
        Mockito.verify(dao).getInstantValue(cellValue);
    }

    @Test
    void getLocalDateTimeValue() {
        cell.getLocalDateTimeValue();
        Mockito.verify(dao).getLocalDateTimeValue(cellValue);
    }

    @Test
    void getCell() {
        assertEquals(cellValue, cell.getCell());
    }

    @Test
    void getDao() {
        assertEquals(dao, cell.getCellDataAccessObject());
    }

    @Test
    void withCellAccessDataObject_sameDao() {
        assertSame(cell, cell.withCellDataAccessObject(cell.getCellDataAccessObject()));
    }

    @Test
    void withCellAccessDataObject_differentDao() {
        //noinspection unchecked
        CellDataAccessObject<Object, ?> otherDao = mock(CellDataAccessObject.class);

        AbstractTableCell<Object, CellDataAccessObject<Object, ?>> actual = cell.withCellDataAccessObject(otherDao);

        assertNotSame(cell, actual);
        assertSame(cell.getCell(), actual.getCell());
        assertSame(otherDao, actual.getCellDataAccessObject());
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier
                .forClass(AbstractTableCell.class)
                .suppress(STRICT_INHERITANCE) // no subclass for test
                .verify();
    }

    @Test
    void testToString() {
        assertEquals("AbstractTableCell(cell=cellValue, dao=dao)", cell.toString());
    }

    static class TableCellTestImpl extends AbstractTableCell<Object, CellDataAccessObject<Object, ?>> {

        protected TableCellTestImpl(Object cell, CellDataAccessObject<Object, ?> dao) {
            super(cell, dao);
        }

        @Override
        public int getColumnIndex() {
            throw new UnsupportedOperationException();
        }


        @Override
        protected AbstractTableCell<Object, CellDataAccessObject<Object, ?>>
        createWithCellDataAccessObject(CellDataAccessObject<Object, ?> dao) {
            return new TableCellTestImpl(getCell(), dao);
        }
    }
}