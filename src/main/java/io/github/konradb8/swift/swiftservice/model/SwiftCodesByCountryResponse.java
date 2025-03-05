package io.github.konradb8.swift.swiftservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SwiftCodesByCountryResponse {
    private String countryISO2;
    private String countryName;
    private List<SwiftCodeResponse> swiftCodes;

    public SwiftCodesByCountryResponse(String countryISO2, String countryName) {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = new ArrayList<>();
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public List<SwiftCodeResponse> getSwiftCodes() {
        return swiftCodes;
    }

    public void setSwiftCodes(List<SwiftCodeResponse> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
