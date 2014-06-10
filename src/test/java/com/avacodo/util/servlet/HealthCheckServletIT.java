package com.avacodo.util.servlet;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.avacodo.util.servlet.HealthCheckServlet;
import com.avacodo.util.servlet.mock.ChainableMockServletConfig;

public class HealthCheckServletIT {

	HealthCheckServlet healthCheckServlet;
	HealthCheckServlet healthCheckServletWithParams;
	
	MockHttpServletRequest request;
	MockHttpServletResponse response;
	
	@BeforeClass
	public static void initializeEnvVariables(){
		System.setProperty("com.avacodo.env", "DEV");
	}
	
	@AfterClass
	public static void tearDownEnvVariables(){
		System.clearProperty("com.avacodo.env");
	}
	
	@Before
	public void setUp() throws Exception {
		
		healthCheckServlet = new HealthCheckServlet();
		healthCheckServlet.init(new ChainableMockServletConfig());
		
		healthCheckServletWithParams = new HealthCheckServlet();
		healthCheckServletWithParams.init(new ChainableMockServletConfig("envPropsRegex", "com\\.avacodo.*"));
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() throws IOException {
		healthCheckServlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		assertNotNull(response.getContentAsString());
		assertFalse(response.getContentAsString().contains("System Properties:"));
	}

	@Test
	public void shouldUtilizeServletInitParams() throws IOException {
		healthCheckServletWithParams.doGet(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		assertNotNull(response.getContentAsString());
		assertTrue(response.getContentAsString().contains("System Properties:"));
		assertTrue(response.getContentAsString().contains("com.avacodo.env : DEV"));
	}

}
