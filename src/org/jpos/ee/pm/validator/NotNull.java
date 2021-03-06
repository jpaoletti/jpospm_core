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
import org.jpos.ee.pm.core.message.MessageFactory;

/**Validate that the specified field is not null
 * 
 * <h2>Simple entity configuration file</h2>
 * <pre>
 * {@code
 * <field>
 *     <id>some_id</id>
 *     ....
 *     <validator class="org.jpos.ee.pm.validator.NotNull" />
 * </field>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class NotNull extends ValidatorSupport {

    /**The validation method*/
    @Override
    public ValidationResult validate(PMContext ctx) {
        final ValidationResult res = new ValidationResult();
        final Object fieldvalue = ctx.getFieldValue();
        res.setSuccessful(fieldvalue != null);
        if (!res.isSuccessful()) {
            res.getMessages().add(MessageFactory.error(
                    ctx.getEntity(), ctx.getField(),
                    get("msg", "void"),
                    ctx.getField().getId()));
        }
        return res;
    }
}
