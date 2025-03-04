package io.github.konradb8.swift.swiftservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SwiftCodesByCountryResponse {
    private String countryISO2;
    private String countryName;
    private List<SwiftCodeResponse> swiftCodes;

    public SwiftCodesByCountryResponse(String countryISO2,String countryName, List<SwiftCodeResponse> swiftCodes ) {
        this.countryISO2 = countryISO2;
        this.swiftCodes = swiftCodes;
        this.countryName = countryName;
    }
}
