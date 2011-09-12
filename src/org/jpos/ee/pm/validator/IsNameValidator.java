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
package org.jpos.ee.pm.validator;

import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMCoreObject;
import org.jpos.ee.pm.core.PMMessage;

/**Validate that the field value is a valid name, so it cant use special characters. 
 * Properties are:
 * msg: the message to show when there is an invalid character. This should be a key for messages properties file
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 */
public class IsNameValidator extends ValidatorSupport {

    /**The validate method*/
    @Override
    public ValidationResult validate(PMContext ctx) {
        final ValidationResult res = new ValidationResult();
        final String fieldvalue = (String) ctx.get(PMCoreObject.PM_FIELD_VALUE);
        res.setSuccessful(true);
        if (!isName(fieldvalue)) {
            res.setSuccessful(false);
            res.getMessages().add(new PMMessage(ctx.getField().getId(), get("msg", "")));
        }
        return res;
    }
}
