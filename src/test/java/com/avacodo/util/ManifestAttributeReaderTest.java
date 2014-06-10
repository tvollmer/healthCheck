package com.avacodo.util;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.avacodo.util.AttributeReader;
import com.avacodo.util.ManifestAttributeReader;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ManifestAttributeReaderTest {

    AttributeReader manifestAttributeReader;
    
    @Mock
    ServletContext context;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        manifestAttributeReader = new ManifestAttributeReader();
        
        System.setProperty("com.avacodo.env", "DEV");
        
        InputStream inputStream = new FileInputStream("src/test/resources/META-INF/MANIFEST.MF");
        when(context.getResourceAsStream(anyString())).thenReturn(inputStream);
    }
    
    @Test
    public void shouldReadAttributesAsNewLinesForBashScripts() throws IOException {
        String html = manifestAttributeReader.generateHtmlSnippet(context);
        assertTrue(html.contains("\n"));
    }
    
    @Test
    public void shouldReadDateAttribute() throws IOException {
        String html = manifestAttributeReader.generateHtmlSnippet(context);
        assertTrue(html.contains("CDT"));
    }
    
    @Test
    public void shouldReadJdkAttribute() throws IOException {
        String html = manifestAttributeReader.generateHtmlSnippet(context);
        assertTrue(html.contains("Jdk"));
    }
    
    @Test
    public void shouldReadPomVersionAttribute() throws IOException {
        String html = manifestAttributeReader.generateHtmlSnippet(context);
        assertTrue(html.contains("Implementation-Version"));
    }

}
