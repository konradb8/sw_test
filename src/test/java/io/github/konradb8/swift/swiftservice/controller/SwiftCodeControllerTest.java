package io.github.konradb8.swift.swiftservice.controller;

import io.github.konradb8.swift.swiftservice.model.SwiftCode;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeRequest;
import io.github.konradb8.swift.swiftservice.model.SwiftCodeResponse;
import io.github.konradb8.swift.swiftservice.repository.SwiftCodeRepository;
import io.github.konradb8.swift.swiftservice.service.SwiftCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(SwiftCodeController.class)
@ExtendWith(SpringExtension.class)
public class SwiftCodeControllerTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;


    private SwiftCodeService swiftCodeService;

    @BeforeEach
    void setUp() {
        swiftCodeService = new SwiftCodeService(swiftCodeRepository);
    }
    @Test
    void testGetSwiftCodeDetails() {
        SwiftCode swiftCode = new SwiftCode();
        swiftCode.setSwiftCode("TEST1234");

        when(swiftCodeRepository.findBySwiftCode("TEST1234")).thenReturn(Optional.of(swiftCode));

        SwiftCodeResponse result = swiftCodeService.getSwiftCodeDetails("TEST1234");
        assertNotNull(result);
        assertEquals("TEST1234", result.getSwiftCode());
    }
}