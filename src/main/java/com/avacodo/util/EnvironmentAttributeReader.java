package com.avacodo.util;

import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

public class EnvironmentAttributeReader extends AbstractAttributeReader 
{
    private final Pattern ENV_PROPS_PTTRN;

    public EnvironmentAttributeReader(final String envPropsRegex){
        this.ENV_PROPS_PTTRN = Pattern.compile(envPropsRegex);
    }
    
    public final void appendAttributes(StringBuilder html, ServletContext context) {
        if (ENV_PROPS_PTTRN == null){
            return;
        }
        html.append(LINE_SEPARATOR).append("System Properties:");
        html.append(LINE_SEPARATOR).append("<div><ul>");
        for (Map.Entry<Object,Object> entry : System.getProperties().entrySet()){
            String key = String.valueOf(entry.getKey());
            if (ENV_PROPS_PTTRN.matcher(key).matches()){
                String value = String.valueOf(entry.getValue());
                appendKeyAndValue(html, key, value);
            }
        }
        html.append(LINE_SEPARATOR).append("</ul></div>");
    }

}
