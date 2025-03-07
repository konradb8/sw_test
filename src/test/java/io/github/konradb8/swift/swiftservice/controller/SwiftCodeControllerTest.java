package io.github.konradb8.swift.swiftservice.controller;

import io.github.konradb8.swift.swiftservice.model.SwiftCode;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeRequest;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeResponse;
import io.github.konradb8.swift.swiftservice.repository.SwiftCodeRepository;
import io.github.konradb8.swift.swiftservice.service.SwiftCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SwiftCodeController.class)
@ExtendWith(SpringExtension.class)
public class SwiftCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Wstrzykujemy mock serwisu, który jest zależnością kontrolera.
    @Autowired
    private SwiftCodeService swiftCodeService;

    // Jeśli serwis używa repozytorium, możemy je również zasymulować (np. przy testach serwisowych)
    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    // Przykładowy test dla metody serwisowej (niekoniecznie test kontrolera)
    @Test
    void testGetSwiftCodeDetailsService() {
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setSwiftCode("TEST1234");

        when(swiftCodeRepository.findBySwiftCode("TEST1234"))
                .thenReturn(Optional.of(swiftCode));

        // Tworzymy instancję serwisu korzystając z mockowanego repozytorium
        SwiftCodeService service = new SwiftCodeService(swiftCodeRepository);
        SwiftCodeResponse result = service.getSwiftCodeDetails("TEST1234");

        assertNotNull(result);
        assertEquals("TEST1234", result.getSwiftCode());
    }

    // Test importu CSV (np. upload pliku)
//    @Test
//    void importCsv() throws Exception {
//        // Przygotowanie przykładowej zawartości CSV
//        String csvContent = "swiftCode,country\nTEST1234,US";
//        MockMultipartFile file = new MockMultipartFile(
//                "file", "swiftcodes.csv", "text/csv", csvContent.getBytes());
//
//        when(swiftCodeService.importCsv(any()))
//                .thenReturn("CSV imported successfully");
//
//        mockMvc.perform(multipart("/swift-codes/import").file(file))
//                .andExpect(status().isOk())
//                .andExpect(content().string("CSV imported successfully"));
//    }

    // Test pobierania szczegółów SwiftCode przez GET /swift-codes/{swiftCode}
//    @Test
//    void getSwiftCodeDetails() throws Exception {
//        SwiftCodeResponse response = new SwiftCodeResponse();
//        response.setSwiftCode("TEST1234");
//
//        when(swiftCodeService.getSwiftCodeDetails("TEST1234"))
//                .thenReturn(response);
//
//        mockMvc.perform(get("/swift-codes/{swiftCode}", "TEST1234"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.swiftCode").value("TEST1234"));
//    }

    // Test pobierania listy SwiftCode według kraju przez GET /swift-codes?country=PL
//    @Test
//    void getSwiftCodesByCountry() throws Exception {
//        SwiftCodeResponse response = new SwiftCodeResponse();
//        response.setSwiftCode("TESTPL");
//
//        List<SwiftCodeResponse> responses = Arrays.asList(response);
//        when(swiftCodeService.getSwiftCodesByCountry("PL"))
//                .thenReturn(responses);
//
//        mockMvc.perform(get("/swift-codes").param("country", "PL"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].swiftCode").value("TESTPL"));
//    }

    // Test dodawania nowego SwiftCode przez POST /swift-codes
//    @Test
//    void addSwiftCode() throws Exception {
//        // Przykładowe dane wejściowe (zakładamy, że obiekt request zawiera swiftCode oraz country)
//        String requestJson = "{\"swiftCode\":\"NEW1234\", \"country\":\"US\"}";
//
//        SwiftCodeResponse response = new SwiftCodeResponse();
//        response.setSwiftCode("NEW1234");
//
//        when(swiftCodeService.addSwiftCode(any()))
//                .thenReturn(response);
//
//        mockMvc.perform(post("/swift-codes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.swiftCode").value("NEW1234"));
//    }

    // Test usuwania SwiftCode przez DELETE /swift-codes/{swiftCode}
    @Test
    void deleteSwiftCode() throws Exception {
        doNothing().when(swiftCodeService).deleteSwiftCode("TEST1234");

        mockMvc.perform(delete("/swift-codes/{swiftCode}", "TEST1234"))
                .andExpect(status().isNoContent());
    }
}
