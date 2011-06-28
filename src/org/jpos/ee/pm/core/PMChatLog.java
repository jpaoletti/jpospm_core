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
package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author jpaoletti
 */
public class PMChatLog extends Observable {

    private List<String> lines;

    public PMChatLog() {
        this.lines = new ArrayList<String>();
    }

    public void println(final String line){
        lines.add(line);
        notifyObservers();
    }

    public List<String> getLines() {
        return lines;
    }
}
