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
package org.jpos.ee.pm.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.jpos.ee.pm.core.*;

/**
 *
 * @author jpaoletti
 */
public class DataAccessPMSession implements DataAccess {

    private Entity entity;

    @Override
    public Object getItem(PMContext ctx, String property, String value) throws PMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<?> list(PMContext ctx, EntityFilter filter, ListSort sort, Integer from, Integer count) throws PMException {
        final List<PMSession> res = new ArrayList<PMSession>();
        for (Entry<String, PMSession> entry : PresentationManager.getPm().getSessions().entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }

    @Override
    public Long count(PMContext ctx) throws PMException {
        return new Long(PresentationManager.getPm().getSessions().size());
    }

    @Override
    public void delete(PMContext ctx, Object object) throws PMException {
        final PMSession session = (PMSession) object;
        PresentationManager.getPm().removeSession(session.getId());
    }

    @Override
    public void update(PMContext ctx, Object instance) throws PMException {
    }

    @Override
    public void add(PMContext ctx, Object instance) throws PMException {
    }

    @Override
    public Object refresh(PMContext ctx, Object o) throws PMException {
        return o;
    }

    @Override
    public EntityFilter createFilter(PMContext ctx) throws PMException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
