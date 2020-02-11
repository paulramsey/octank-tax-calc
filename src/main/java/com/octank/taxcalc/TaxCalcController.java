package com.octank.taxcalc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TaxCalcController {

	@RequestMapping("/tax-calc")
	public String index() {
		return "You called the Tax Calculation microservice!";
	}

}