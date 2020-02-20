package com.octank.taxcalc;

import com.amazonaws.xray.AWSXRay;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TaxCalcController {
	
	@GetMapping
	public String index() {

		// Generate some load by calculating pi
		AWSXRay.beginSubsegment("Running calulations");
		Pi.computePi(20000);
		AWSXRay.endSubsegment();

		// Generate random tax rate
		AWSXRay.beginSubsegment("Deriving tax rate");
		double taxRate = Math.random() * 9 + 1;
		if (taxRate < 5) {
			taxRate = taxRate + 5;
		}
		taxRate = taxRate/100;
		taxRate = Math.round(taxRate * 10000.0) / 10000.0;
		AWSXRay.endSubsegment();

		// Generate random total tax
		AWSXRay.beginSubsegment("Calculate total tax");
		double totalTax = Math.random() * 2000 + 1;
		totalTax = totalTax * taxRate;
		totalTax = Math.round(totalTax * 100.0) / 100.0;
		AWSXRay.endSubsegment();

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