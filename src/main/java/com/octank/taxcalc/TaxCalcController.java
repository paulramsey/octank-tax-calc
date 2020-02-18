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

		// Generate some load by calculating pi
		Pi.computePi(20000);

		// Generate random tax rate
		double taxRate = Math.random() * 9 + 1;
		if (taxRate < 5) {
			taxRate = taxRate + 5;
		}
		taxRate = taxRate/100;
		taxRate = Math.round(taxRate * 10000.0) / 10000.0;

		// Generate random total tax
		double totalTax = Math.random() * 2000 + 1;
		totalTax = totalTax * taxRate;
		totalTax = Math.round(totalTax * 100.0) / 100.0;

		String rtrn = "{\"taxRate\": \"" + String.valueOf(taxRate) + "\", \"totalTax\": \"" + String.valueOf(totalTax) + "\"}";
		//System.out.println(rtrn);
		
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