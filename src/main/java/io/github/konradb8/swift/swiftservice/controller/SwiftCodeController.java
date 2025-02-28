package io.github.konradb8.swift.swiftservice.controller;

import io.github.konradb8.swift.swiftservice.model.SwiftCodeRequest;
import io.github.konradb8.swift.swiftservice.service.CSVImportService;
import io.github.konradb8.swift.swiftservice.service.SwiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @Autowired
    private CSVImportService csvImportService;

    @PostMapping(value = "/import-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importCsv(file);
            return ResponseEntity.ok("CSV file imported successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error importing CSV file: " + e.getMessage());
        }
    }

    // Endpoint 1: return swift code details
    @GetMapping("/{swiftCode}")
    public ResponseEntity<?> getSwiftCodeDetails(@PathVariable String swiftCode) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCodeDetails(swiftCode));
    }

    // Endpoint 2: Return all SWIFT codes for a specific country
    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<?> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        return ResponseEntity.ok(swiftCodeService.getSwiftCodesByCountry(countryISO2));
    }

    // Endpoint 3: Adds new SWIFT code entries
    @PostMapping
    public ResponseEntity<?> addSwiftCode(@RequestBody SwiftCodeRequest request) {
        swiftCodeService.addSwiftCode(request);
        return ResponseEntity.ok(Collections.singletonMap("message", "SWIFT code added successfully"));
    }

    // Endpoint 4: Deletes a SWIFT code
    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<?> deleteSwiftCode(@PathVariable String swiftCode) {
        swiftCodeService.deleteSwiftCode(swiftCode);
        return ResponseEntity.ok(Collections.singletonMap("message", "SWIFT code deleted successfully"));
    }
}
