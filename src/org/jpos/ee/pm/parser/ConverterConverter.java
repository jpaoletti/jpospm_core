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
package org.jpos.ee.pm.parser;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.util.ArrayList;
import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.Converters;
import org.jpos.ee.pm.converter.ExternalConverter;
import org.jpos.ee.pm.core.PresentationManager;

/**
 * This is not a recursive class! This is an xstream converter for the
 * pm.core.converter.Converter class.
 * 
 * @author jpaoletti
 */
public class ConverterConverter implements com.thoughtworks.xstream.converters.Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
        final Converters converters = (Converters) o;
        writer.startNode("converters");
        for (Converter c : converters.getConverters()) {
            writer.startNode("converter");
            mc.convertAnother(c);
            writer.endNode();
        }
        for (ExternalConverter c : converters.getExternalConverters()) {
            writer.startNode("econverter");
            mc.convertAnother(c);
            writer.endNode();
        }
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        final Converters converters = new Converters();
        converters.setConverters(new ArrayList<Converter>());
        converters.setExternalConverters(new ArrayList<ExternalConverter>());
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if (reader.getNodeName().equals("converter")) {
                final String clazz = reader.getAttribute("class");
                try {
                    final Converter c = (Converter) uc.convertAnother(converters, Class.forName(clazz));
                    converters.getConverters().add(c);
                } catch (Exception ex) {
                    PresentationManager.getPm().warn("Converter not found: " + clazz);
                }
            } else if (reader.getNodeName().equals("econverter")) {
                ExternalConverter c = (ExternalConverter) uc.convertAnother(converters, ExternalConverter.class);
                converters.getExternalConverters().add(c);
            }
            reader.moveUp();
        }
        return converters;
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(Converters.class);
    }
}
