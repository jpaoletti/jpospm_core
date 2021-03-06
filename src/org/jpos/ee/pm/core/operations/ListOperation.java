/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
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
package org.jpos.ee.pm.core.operations;

import org.jpos.ee.pm.core.*;

/**
 *
 * @author jpaoletti
 */
public class ListOperation extends OperationCommandSupport {

    public ListOperation(String operationId) {
        super(operationId);
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        super.doExecute(ctx);
        final ListManager listManager = new ListManager();
        final Operations operations = (Operations) ctx.get(OPERATIONS);

        PaginatedList pmlist = ctx.getList();
        if (pmlist == null) {
            pmlist = listManager.initList(ctx, operations);
        }

        configureOrder(ctx, pmlist);
        final Integer page = (Integer) ctx.get("page");
        if (page != null) {
            pmlist.setPage(page);
        }
        final Integer rpp = (Integer) ctx.get("rowsPerPage");
        if (rpp != null) {
            pmlist.setRowsPerPage(rpp);
        }
        listManager.configureList(ctx, pmlist, operations);
    }

    public void configureOrder(PMContext ctx, PaginatedList pmlist) {
        final String o = ctx.getString("order");
        if (o != null) {
            pmlist.getSort().setFieldId(o);
        } else {
            if (pmlist.getSort().isSorted()) {
                pmlist.getSort().setFieldId(ctx.getEntity().getOrderedFields().get(0).getId());
            }
        }
        final Object d = ctx.getParameter("desc");
        if (d != null) {
            if ("true".equals(d)) {
                pmlist.getSort().setDirection(ListSort.SortDirection.DESC);
            } else {
                pmlist.getSort().setDirection(ListSort.SortDirection.ASC);
            }
        }
    }
}
