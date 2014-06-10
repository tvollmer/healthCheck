package com.avacodo.util;

import javax.servlet.ServletContext;

public abstract class AbstractAttributeReader
    implements AttributeReader {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public final String generateHtmlSnippet(ServletContext context) {
        StringBuilder html = new StringBuilder();
        appendAttributes(html, context);
        return html.toString();
    }
    
    public abstract void appendAttributes(StringBuilder html, ServletContext context);
    
    protected void appendKeyAndValue(StringBuilder html, String key, String value) {
        html.append(LINE_SEPARATOR)
        	.append("<li>")
        	.append(key).append(" : ").append(value)
        	.append("</li>");
    }

}
