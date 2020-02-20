package com.octank.taxcalc;

import java.util.HashMap;
import java.util.Map;

// Class to allow Spring to auto-format the reponse as JSON
public class ResponseObject {
    String taxRate;
    String totalTax;

    // Constructor
    public ResponseObject(String tr, String tt) {
        this.taxRate = tr;
        this.totalTax = tt;
    }

    public Map<String, String> getResponseObject() {
        Map<String, String> returnObject = new HashMap<String, String>();
        returnObject.put("taxRate", this.taxRate);
        returnObject.put("totalTax", this.totalTax);
        return returnObject;
    }
    
}