package com.avacodo.util.servlet.mock;

import javax.servlet.ServletContext;

import org.springframework.mock.web.MockServletConfig;

public class ChainableMockServletConfig extends MockServletConfig {
	
	public ChainableMockServletConfig() {
		super();
	}

	public ChainableMockServletConfig(ServletContext servletContext,
			String servletName) {
		super(servletContext, servletName);
	}

	public ChainableMockServletConfig(ServletContext servletContext) {
		super(servletContext);
	}

	public ChainableMockServletConfig(String servletName) {
		super(servletName);
	}

	public ChainableMockServletConfig(String name, String value){
		super.addInitParameter(name, value);
	}
	
	public ChainableMockServletConfig withInitParameter(String name, String value){
		super.addInitParameter(name, value);
		return this;
	}
}