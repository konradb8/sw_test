package io.github.konradb8.swift.swiftservice.model;

import java.util.List;


public class SwiftCodeResponseWrapper {
    private String countryISO2;
    private String countryName;
    private List<SwiftCodeResponse> swiftCodes;

    public SwiftCodeResponseWrapper(String countryISO2, String countryName, List<SwiftCodeResponse> swiftCodes) {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = swiftCodes;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public List<SwiftCodeResponse> getSwiftCodes() {
        return swiftCodes;
    }
}