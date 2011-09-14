/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2011 Alejandro P. Revilla
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.core;

/**
 * Entity list order
 *
 * @author jpaoletti
 */
public class ListSort {

    private String fieldId;
    private SortDirection direction;

    public ListSort(String fieldId, SortDirection direction) {
        this.fieldId = fieldId;
        this.direction = direction;
    }

    public boolean isDesc() {
        return getDirection().equals(SortDirection.DESC);
    }

    public boolean isAsc() {
        return getDirection().equals(SortDirection.ASC);
    }

    public boolean isSorted() {
        return getFieldId() != null;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public static enum SortDirection {

        ASC, DESC;
    }
}
