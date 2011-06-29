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

import java.util.List;

/**
 * 
 * @author jpaoletti
 */
public class PMCache extends PMCoreObject {

    private Entity entity;
    private List<?> list;
    private int modification;

    public PMCache(final Entity entity) throws Exception {
        this.entity = entity;
    }

    public void reloadList() throws Exception {
        final PMContext ctx = new PMContext();
        getPresentationManager().getPersistenceManager().init(ctx);
        try {
            ctx.put(PM_ENTITY, getEntity());
            this.list = getEntity().getDataAccess().list(ctx, null, null, null);
        } finally {
            getPresentationManager().getPersistenceManager().finish(ctx);
        }
    }

    /**
     * Do NOT use this getter to modify the list content. Use setList instead.
     */
    public List<?> getList() {
        return list;
    }

    public List<?> getList(PMContext ctx, EntityFilter filter, Integer from, Integer count) {
        if (from != null && count != null) {
            final int to = (from + count >= list.size()) ? list.size() : from + count;
            return list.subList(from, to);
        } else {
            return list;
        }
    }

    public void setList(List<?> list) {
        this.list = list;
        modification++;
    }

    public int getModification() {
        return modification;
    }

    public void setModification(int modification) {
        this.modification = modification;
    }

    protected Entity getEntity() {
        return entity;
    }
}
