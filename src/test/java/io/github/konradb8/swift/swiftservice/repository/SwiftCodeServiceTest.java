package io.github.konradb8.swift.swiftservice.repository;


import io.github.konradb8.swift.swiftservice.model.SwiftCode;
import io.github.konradb8.swift.swiftservice.repository.SwiftCodeRepository;
import io.github.konradb8.swift.swiftservice.service.SwiftCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeServiceTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private SwiftCodeService swiftCodeService;

    @Test
    void testGetSwiftCodeDetails() {
        String swiftCode = "BANKUS33XXX";
        SwiftCode swift = new SwiftCode();
        swift.setSwiftCode(swiftCode);

        when(swiftCodeRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(swift));

        assertNotNull(swiftCodeService.getSwiftCodeDetails(swiftCode));
    }
}