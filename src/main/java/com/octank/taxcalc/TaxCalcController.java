package com.octank.taxcalc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

@RestController
@RequestMapping("/")
public class TaxCalcController {
	
	@GetMapping
	public String index() {
		String rtrn = "You called the Tax Calculation microservice!";
		return rtrn;
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
	webServerFactoryCustomizer() {
		
		// Set the contextPath depending on whether this is running locally or in a container
		// This is necessary to keep the expected URI /tax-calc across environments
		String contextPath;
		String envVars = System.getenv().toString();
		if (envVars.contains("apple")) {
			contextPath = "/tax-calc";
		} else {
			contextPath = "";
		}		

		return factory -> factory.setContextPath(contextPath);
	}
}