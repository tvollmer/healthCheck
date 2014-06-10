package com.avacodo.util.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.avacodo.util.AttributeReader;
import com.avacodo.util.CompositeAttributeReader;
import com.avacodo.util.EnvironmentAttributeReader;
import com.avacodo.util.ManifestAttributeReader;

public class HealthCheckServlet extends HttpServlet
{
    private static final long serialVersionUID = 7810353353007157449L;
    private AttributeReader attributeReader;;
    
    public final void setAttributeReader(AttributeReader attributeReader){
        this.attributeReader = attributeReader;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    	super.init(servletConfig);
        String envPropsRegex = servletConfig.getInitParameter("envPropsRegex");
        this.attributeReader = new CompositeAttributeReader()
            .addReader(new ManifestAttributeReader());
        
        if (envPropsRegex != null && !"".equals(envPropsRegex)){
        	AttributeReader reader = new EnvironmentAttributeReader(envPropsRegex);
            ((CompositeAttributeReader)this.attributeReader).addReader(reader);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException 
    {
    	ServletConfig servletConfig = getServletConfig();
    	ServletContext servletContext = servletConfig.getServletContext();
    	
    	String content = new StringBuilder()
    		.append("<html><body>")
    		.append("SUCCESS")
    		.append(attributeReader.generateHtmlSnippet(servletContext))
    		.append("</body></html>")
    		.toString();
    	
        PrintWriter out = response.getWriter();
        out.println(content);
        out.flush();
        out.close();
    }
}
