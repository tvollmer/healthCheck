package com.avacodo.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.avacodo.util.EnvironmentAttributeReader;


public class EnvironmentAttributeReaderTest {

    EnvironmentAttributeReader attributeReader;
    
    @Mock
    ServletContext context;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        attributeReader = new EnvironmentAttributeReader("com\\.avacodo.*");
        
        System.setProperty("com.tomato.env", "DEV");
        System.setProperty("com.avacodo.env", "DEV");
        
        InputStream inputStream = new FileInputStream("src/test/resources/META-INF/MANIFEST.MF");
        when(context.getResourceAsStream(anyString())).thenReturn(inputStream);
    }
    
    @Test
    public void shouldNotPrintNonMatchingSystemProps() throws IOException {
        String html = attributeReader.generateHtmlSnippet(context);
        assertFalse(html.contains("com.tomato.env"));
    }
    
    @Test
    public void shouldPrintSystemProps() throws IOException {
        String html = attributeReader.generateHtmlSnippet(context);
        assertTrue(html.contains("System Properties"));
        assertTrue(html.contains("com.avacodo.env"));
    }

}
