package io.github.konradb8.swift.swiftservice.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
@AllArgsConstructor
@JsonPropertyOrder({"address", "bankName", "countryISO2", "isHeadquarter", "swiftCode", "branches"})
public class SwiftCodeShortResponse {
    private String address;
    private String bankName;
    private String countryISO2;
    private String swiftCode;
    @JsonProperty("isHeadquarter")
    private boolean headquarter;

    public SwiftCodeShortResponse(SwiftCode swiftCode) {
        this.address = swiftCode.getAddress();
        this.bankName = swiftCode.getName();
        this.countryISO2 = swiftCode.getCountryISO2();
        this.headquarter = swiftCode.getHeadquarter();
        this.swiftCode = swiftCode.getSwiftCode();
    }

    public static SwiftCodeShortResponse fromEntity(SwiftCode entity) {
        return new SwiftCodeShortResponse(entity);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public boolean isHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(boolean isheadquarter) {
        headquarter = isheadquarter;
    }

}
