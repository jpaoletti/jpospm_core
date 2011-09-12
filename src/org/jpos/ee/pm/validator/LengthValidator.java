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

/**Validate the length of the string.
 * max-length: maximum length of the string
 * max-length-msg: message to show if the name is too long
 * min-length: minimum length of the string
 * min-length-msg:  message to show if the name is too short
 * 
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 */
public class LengthValidator extends ValidatorSupport {

    /**The validate method*/
    @Override
    public ValidationResult validate(PMContext ctx) {
        final ValidationResult res = new ValidationResult();
        final Object object = ctx.get(PMCoreObject.PM_FIELD_VALUE);
        final String fieldId = ctx.getField().getId();

        if (object instanceof String) {
            String fieldvalue = (String) object;
            res.setSuccessful(true);
            Integer len = fieldvalue.length();
            Integer maxl = getInt("max-length");
            if (maxl != null) {
                if (len > maxl) {
                    res.setSuccessful(false);
                    res.getMessages().add(new PMMessage(fieldId,get("max-length-msg", "pm_core.validator.toolong"), fieldId, len.toString(), maxl.toString()));
                }
            }
            Integer minl = getInt("min-length");
            if (minl != null) {
                if (len < minl) {
                    res.setSuccessful(false);
                    res.getMessages().add(new PMMessage(fieldId,get("min-length-msg", "pm_core.validator.tooshort"), fieldId, len.toString(), minl.toString()));
                }
            }
        } else {
            res.setSuccessful(false);
            res.getMessages().add(new PMMessage(fieldId, "pm_core.validator.fieldnotstring", fieldId));
        }
        return res;
    }
}
