package io.github.konradb8.swift.swiftservice.service;

import io.github.konradb8.swift.swiftservice.model.SwiftCode;
import io.github.konradb8.swift.swiftservice.repository.SwiftCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class SwiftCodeRepositoryTest {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Test
    void testFindBySwiftCode() {
        SwiftCode swift = new SwiftCode();
        swift.setSwiftCode("BANKUS33XXX");
        swiftCodeRepository.save(swift);

        assertTrue(swiftCodeRepository.findBySwiftCode("BANKUS33XXX").isPresent());
    }
}