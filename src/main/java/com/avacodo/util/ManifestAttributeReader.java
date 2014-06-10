package com.avacodo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

public class ManifestAttributeReader
    extends AbstractAttributeReader
{    
    private static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";
    private final AtomicReference<String> manifestHtmlSnippet = new AtomicReference<String>();
    
    public void appendAttributes(StringBuilder html, ServletContext context) {
        String result = manifestHtmlSnippet.get();
        if (result == null) {
            result = initialize(context);
            if (!manifestHtmlSnippet.compareAndSet(null, result)){
                // another thread has initialized the reference
                result = manifestHtmlSnippet.get();
            }
        }
        html.append(result);
    }

    private String initialize(ServletContext context) {
        try {
            InputStream inputStream = context.getResourceAsStream(MANIFEST_PATH);
            Manifest manifest = new Manifest(inputStream);
            Attributes attr = manifest.getMainAttributes();

            StringBuilder readerSnippet = new StringBuilder();
            readerSnippet.append(LINE_SEPARATOR).append("Manifest Properties:");
            readerSnippet.append(LINE_SEPARATOR).append("<div><ul>");
            for (Map.Entry<Object,Object> entry: attr.entrySet()) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                appendKeyAndValue(readerSnippet, key, value); 
            }
            readerSnippet.append(LINE_SEPARATOR).append("</ul></div>");
            
            return readerSnippet.toString();
        } catch (IOException e) {
            throw new RuntimeException("unable to initialize reader", e);
        }
    }
}
