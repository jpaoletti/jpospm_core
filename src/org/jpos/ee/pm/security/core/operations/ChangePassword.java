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

import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityUser;

/**
 *
 * @author jpaoletti
 */
public class ChangePassword extends SecurityOperation {

    public ChangePassword(String operationId) {
        super(operationId);
    }

    @Override
    protected boolean checkSelected() {
        return false;
    }

    @Override
    protected boolean prepare(PMContext ctx) throws PMException {
        super.prepare(ctx);
        return finished(ctx);
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        final PMSecurityUser u = ctx.getUser();

        final String actual = (String) ctx.getParameter("actual");
        assertNotNull(actual, "chpass.actual.not.null");

        final String newpass = (String) ctx.getParameter("newpass");
        assertNotNull(newpass, "chpass.newpass.not.null");

        final String newrep = (String) ctx.getParameter("newrep");
        assertNotNull(newrep, "chpass.newrep.not.null");

        assertTrue(newpass.equals(newrep), "chpass.newrep.diferent");
        assertTrue(!newpass.equals(actual), "chpass.repeated.passw");

        try {
            getConnector(ctx).changePassword(u.getUsername(), actual, newpass);
        } catch (PMSecurityException e) {
            throw new PMException("pm_security.password.invalid");
        }
    }
}
