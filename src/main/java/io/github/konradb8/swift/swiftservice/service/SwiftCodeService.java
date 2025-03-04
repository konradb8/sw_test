package io.github.konradb8.swift.swiftservice.service;


import io.github.konradb8.swift.swiftservice.model.SwiftCode;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeRequest;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeResponse;
import io.github.konradb8.swift.swiftservice.model.SwiftCodesByCountryResponse;
import io.github.konradb8.swift.swiftservice.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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

//    public ResponseEntity<Object> newSwift(SwiftCode swift) {
//        swiftRepository.save(swift);
//        return new ResponseEntity<>(swift, HttpStatus.CREATED);
//    }


//    public SwiftCodeResponse getSwiftCodeDetails(String swiftCode) {
//        Swift entity = swiftRepository.findBySwiftCode(swiftCode)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SWIFT code not found"));
//
//        SwiftCodeResponse response = SwiftCodeResponse.fromEntity(entity);
//        response.setHeadquarter(isHeadquarter(swiftCode));
//
//        if (response.isHeadquarter()) {
//            List<SwiftCodeResponse> branches = swiftRepository.findBySwiftCodeStartingWith(swiftCode.substring(0, 8)).stream()
//                    .filter(branch -> !branch.getSwiftCode().endsWith("XXX"))
//                    .map(SwiftCodeResponse::fromEntity)
//                    .collect(Collectors.toList());
//            response.setBranches(branches);
//        }
//        return response;
//    }

    public SwiftCodeResponse getSwiftCodeDetails(String swiftCode) {
        SwiftCode entity = swiftRepository.findBySwiftCode(swiftCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SWIFT code not found"));

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


    public List<SwiftCodeResponse> getSwiftCodesByCountry(String countryISO2) {
        return swiftRepository.findByCountryISO2(countryISO2).stream()
                .map(entity -> {
                    SwiftCodeResponse response = SwiftCodeResponse.fromEntity(entity);
                    response.setHeadquarter(isHeadquarter(entity.getSwiftCode()));
                    return response;
                })
                .collect(Collectors.toList());
    }
//    public SwiftCodesByCountryResponse getSwiftCodesByCountry(String countryISO2) {
//        List<SwiftCodeResponse> swiftCodes = swiftRepository.findByCountryISO2(countryISO2).stream()
//                .map(entity -> {
//                    SwiftCodeResponse response = SwiftCodeResponse.fromEntity(entity);
//                    response.setHeadquarter(isHeadquarter(entity.getSwiftCode()));
//                    return response;
//                })
//                .collect(Collectors.toList());
//
//        // Pobranie nazwy kraju (zakładam, że pierwszy wynik ma poprawną nazwę)
//        String countryName = swiftCodes.isEmpty() ? null : swiftCodes.get(0).getCountryName();
//
//        return new SwiftCodesByCountryResponse(countryISO2, countryName, swiftCodes);
//    }



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