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

import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.security.core.PMSecurityConnector;
import org.jpos.ee.pm.security.core.PMSecurityService;

/**
 *
 * @author jpaoletti
 */
public class SecurityOperation extends OperationCommandSupport {

    public SecurityOperation(String operationId) {
        super(operationId);
    }

    @Override
    protected boolean checkEntity() {
        return true;
    }

    @Override
    protected boolean checkSelected() {
        return true;
    }

    @Override
    protected boolean openTransaction() {
        return true;
    }

    protected PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }
}
