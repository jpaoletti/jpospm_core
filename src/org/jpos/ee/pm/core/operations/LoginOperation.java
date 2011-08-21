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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.core.operations;

import org.jpos.ee.BLException;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMSession;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.menu.Menu;
import org.jpos.ee.pm.menu.MenuSupport;
import org.jpos.ee.pm.security.core.InvalidPasswordException;
import org.jpos.ee.pm.security.core.InvalidUserException;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityException;
import org.jpos.ee.pm.security.core.PMSecurityService;
import org.jpos.ee.pm.security.core.PMSecurityUser;
import org.jpos.ee.pm.security.core.UserAlreadyLogged;
import org.jpos.ee.pm.security.core.UserNotFoundException;

/**
 *
 * @author jpaoletti
 */
public class LoginOperation extends OperationCommandSupport {

    public LoginOperation(String operationId) {
        super(operationId);
    }

    @Override
    protected boolean prepare(PMContext ctx) throws PMException {
        return true;
    }

    @Override
    protected void doExecute(PMContext ctx) throws PMException {
        final PresentationManager pm = ctx.getPresentationManager();
        final PMSession session = pm.registerSession(null);
        ctx.setSessionId(session.getId());
        if (pm.isLoginRequired()) {
            try {
                final PMSecurityUser u = authenticate(ctx);
                session.setUser(u);
                session.setMenu(loadMenu(session, u));
            } catch (UserNotFoundException e) {
                pm.removeSession(session.getId());
                throw new PMException("pm_security.user.not.found");
            } catch (UserAlreadyLogged e) {
                pm.removeSession(session.getId());
                throw new PMException("pm_security.user.already.logged");
            } catch (InvalidPasswordException e) {
                pm.removeSession(session.getId());
                throw new PMException("pm_security.password.invalid");
            } catch (Exception e) {
                pm.error(e);
                pm.removeSession(session.getId());
                throw new PMException("pm_core.unespected.error");
            }
        } else {
            final PMSecurityUser user = new PMSecurityUser();
            user.setName(" ");
            session.setUser(user);
            session.setMenu(loadMenu(session, user));
        }
    }

    private Menu loadMenu(PMSession session, PMSecurityUser u) throws PMException {
        final Menu menu = MenuSupport.getMenu(u.getPermissionList());
        session.put("user", u);
        session.put("menu", menu);
        return menu;
    }

    /**
     * @param ctx The context with all the parameters
     * @return The user
     * @throws BLException
     */
    private PMSecurityUser authenticate(PMContext ctx) throws PMSecurityException {
        PMSecurityUser u = null;
        final Object username = ctx.getParameter("username");
        final Object password = ctx.getParameter("password");
        if (username == null || password == null) {
            throw new InvalidUserException();
        }
        u = getConnector(ctx).authenticate(
                username.toString(),
                password.toString());
        return u;
    }

    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }

    @Override
    protected boolean checkUser() {
        return false;
    }
}
