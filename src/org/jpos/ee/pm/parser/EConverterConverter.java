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
package org.jpos.ee.pm.parser;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.ConverterWrapper;
import org.jpos.ee.pm.core.PresentationManager;

/**
 *
 * @author jpaoletti
 */
public class EConverterConverter implements com.thoughtworks.xstream.converters.Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        final ConverterWrapper result = new ConverterWrapper();
        result.setId(reader.getAttribute("id"));
        reader.moveDown();
        final String clazz = reader.getAttribute("class");
        try {
            final Converter c = (Converter) uc.convertAnother(result, Class.forName(clazz));
            result.setConverter(c);
        } catch (Exception ex) {
            PresentationManager.getPm().warn("External converter not found: " + clazz);
        } finally {
            reader.moveUp();
        }
        return result;
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(ConverterWrapper.class);
    }
}
