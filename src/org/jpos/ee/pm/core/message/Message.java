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
package org.jpos.ee.pm.core.message;

import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Field;

/**
 * This class represents any message that system want to send to the user. It
 * may be system wide, entity o field scoped, an error, warning or information
 * message.
 *
 * @author jpaoletti
 * @since 13/09/2011
 * @version v1.1
 *
 */
public class Message {

    private MessageType type;
    private Entity entity;
    private Field field;
    private String key;
    private String[] args;

    /**
     * Determine if this message is system scoped.
     *
     * @return true when message is not asociated with an entity or field
     */
    public boolean isSystemScoped() {
        return getEntity() == null && getField() == null;
    }

    /**
     * Determine if this message is entity scoped.
     *
     * @return true when message is asociated with an entity but not to a field
     */
    public boolean isEntityScoped() {
        return getEntity() != null && getField() == null;
    }

    /**
     * Determine if this message is field scoped.
     *
     * @return true when message is asociated with an entity and field
     */
    public boolean isFieldScoped() {
        return getEntity() != null && getField() != null;
    }

    public boolean isError() {
        return getType() == MessageType.ERROR;
    }

    public boolean isWarn() {
        return getType() == MessageType.WARN;
    }

    public boolean isInfo() {
        return getType() == MessageType.INFO;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
