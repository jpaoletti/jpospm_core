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
package org.jpos.ee.pm.security.core;

import org.jpos.ee.pm.core.PMContext;
import org.jpos.util.Log;

/**
 * This is an abstract implementation for security connectors that uses BCrypt
 * algotithm
 *
 * @author jpaoletti
 */
public abstract class PMSecurityAbstractConnector implements PMSecurityConnector {

    private PMSecurityService service;
    private PMContext ctx;

    @Override
    public PMSecurityUser authenticate(String username, String password) throws PMSecurityException {
        final PMSecurityUser user = getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.isDeleted()) {
            throw new UserNotFoundException();
        }
        if (!user.isActive()) {
            throw new UserNotActiveException();
        }
        if (isLoggedIn(user) && !service.isMultipleLogin()) {
            throw new UserAlreadyLogged();
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    @Override
    public void changePassword(String username, String password, String newpassword) throws PMSecurityException {
        final PMSecurityUser user = getUser(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.isDeleted()) {
            throw new UserNotFoundException();
        }
        if (password != null && !BCrypt.checkpw(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        user.setPassword(encrypt(newpassword));
        updateUser(user);
    }

    protected String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public void setService(PMSecurityService service) {
        this.service = service;
    }

    @Override
    public void setContext(PMContext ctx) {
        this.ctx = ctx;
    }

    public PMContext getCtx() {
        return ctx;
    }

    public PMSecurityService getService() {
        return service;
    }

    protected Log getLog() {
        return service.getLog();
    }

    protected void checkUserRules(String username, String password) throws PMSecurityException {
        if (username == null) {
            throw new InvalidUserException();
        }
        if (password == null) {
            throw new InvalidPasswordException();
        }
        //TODO Check rules
    }

    @Override
    public void removeUser(PMSecurityUser user) throws PMSecurityException {
        user.setDeleted(true);
        updateUser(user);
    }

    private boolean isLoggedIn(PMSecurityUser user) {
        return ctx.getPresentationManager().getSessionByUser(user.getUsername()) != null;
    }
}
