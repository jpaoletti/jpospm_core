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
package org.jpos.ee.pm.security.core.operations;

import java.util.UUID;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.security.core.PMSecurityUser;

/**
 *
 * @author jpaoletti
 */
public class ResetPassword extends SecurityOperation {

    public ResetPassword(String operationId) {
        super(operationId);
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        final PMSecurityUser u = (PMSecurityUser) ctx.getSelected().getInstance();
        if (ctx.getUser().equals(u)) {
            throw new PMException("pm.user.cant.reset.his.psw");
        }
        final String generatedpsw = UUID.randomUUID().toString().substring(0, 8);
        getConnector(ctx).changePassword(u.getUsername(), null, generatedpsw);
        ctx.put("generatedpsw", generatedpsw);
        ctx.put("username", u.getUsername());
    }
}
