package com.avacodo.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class CompositeAttributeReader
    implements AttributeReader {

    private List<AttributeReader> readers = new ArrayList<AttributeReader>();
    
    public final CompositeAttributeReader addReader(AttributeReader reader) {
        this.readers.add(reader);
        return this;
    }

    @Override
    public String generateHtmlSnippet(ServletContext context) {
        StringBuilder html = new StringBuilder();
        for (AttributeReader reader : readers){
            html.append(reader.generateHtmlSnippet(context));
        }
        return html.toString();
    }

}
