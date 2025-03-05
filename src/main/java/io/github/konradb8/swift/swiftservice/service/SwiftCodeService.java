package io.github.konradb8.swift.swiftservice.service;


import io.github.konradb8.swift.swiftservice.model.SwiftCode;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeRequest;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeResponse;
import io.github.konradb8.swift.swiftservice.model.SwiftCodesByCountryResponse;
import io.github.konradb8.swift.swiftservice.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwiftCodeService {

    private final SwiftCodeRepository swiftRepository;

    @Autowired
    public SwiftCodeService(SwiftCodeRepository swiftRepository) {
        this.swiftRepository = swiftRepository;
    }

    public SwiftCodeResponse getSwiftCodeDetails(String swiftCode) {
        SwiftCode entity = swiftRepository.findBySwiftCode(swiftCode)
                .orElseThrow(() -> {
                        System.out.println("SWIFT code nie znaleziony: " + swiftCode);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "SWIFT code not found");
                });
        SwiftCodeResponse response = new SwiftCodeResponse(entity);
        response.setAddress(entity.getAddress());
        response.setBankName(entity.getName());
        response.setCountryISO2(entity.getCountryISO2());
        response.setCountryName(entity.getCountryName());
        response.setHeadquarter(isHeadquarter(swiftCode));
        response.setSwiftCode(entity.getSwiftCode());

        if (response.isHeadquarter()) {
            List<SwiftCodeResponse> branches = swiftRepository.findBySwiftCodeStartingWith(swiftCode.substring(0, 8)).stream()
                    .filter(branch -> !branch.getSwiftCode().endsWith("XXX"))
                    .map(branch -> {
                        SwiftCodeResponse branchResponse = new SwiftCodeResponse(entity);
                        branchResponse.setAddress(branch.getAddress());
                        branchResponse.setBankName(branch.getName());
                        branchResponse.setCountryISO2(branch.getCountryISO2());
                        branchResponse.setHeadquarter(false);
                        branchResponse.setSwiftCode(branch.getSwiftCode());
                        return branchResponse;
                    })
                    .collect(Collectors.toList());
            response.setBranches(branches);
        }
        return response;
    }


    public SwiftCodesByCountryResponse getSwiftCodesByCountry(String countryISO2) {
        List<SwiftCode> entities = swiftRepository.findByCountryISO2(countryISO2);

        if (entities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Swift code not found");
        }

        String countryName = entities.get(0).getCountryName();

        SwiftCodesByCountryResponse response = new SwiftCodesByCountryResponse(countryISO2, countryName);
        response.setCountryISO2(countryISO2);
        response.setCountryName(countryName);


        List<SwiftCodeResponse> swiftCodes = entities.stream()
                .map(swiftCode -> {
                    SwiftCodeResponse swiftCodeResponse = new SwiftCodeResponse(entities.get(0));
                    swiftCodeResponse.setAddress(swiftCode.getAddress());
                    swiftCodeResponse.setBankName(swiftCode.getName());
                    swiftCodeResponse.setCountryISO2(swiftCode.getCountryISO2());
                    swiftCodeResponse.setHeadquarter(swiftCode.getHeadquarter());
                    swiftCodeResponse.setSwiftCode(swiftCode.getSwiftCode());
                    return swiftCodeResponse;
                })
                .collect(Collectors.toList());

        response.setSwiftCodes(swiftCodes);
        return response;
    }



    public void deleteSwiftCode(String swiftCode) {
        SwiftCode entity = swiftRepository.findBySwiftCode(swiftCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SWIFT code not found"));
        swiftRepository.delete(entity);
    }

    private boolean isHeadquarter(String swiftCode) {
        return swiftCode.endsWith("XXX");
    }

    public void addSwiftCode(SwiftCodeRequest request) {
        SwiftCode Swift = new SwiftCode();
        Swift.setSwiftCode(request.getSwiftCode());
        Swift.setAddress(request.getAddress());
        Swift.setName(request.getBankName());
        Swift.setCountryISO2(request.getCountryISO2().toUpperCase());
        Swift.setCountryName(request.getCountryName().toUpperCase());
        Swift.setHeadquarter(isHeadquarter(request.getSwiftCode()));

        swiftRepository.save(Swift);
    }

}