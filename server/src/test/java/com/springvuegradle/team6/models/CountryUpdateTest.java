package com.springvuegradle.team6.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class CountryUpdateTest {

    private final CountryRepository countryRepository;

    CountryUpdateTest(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Test
    void updateCountryRepository() {
        // TODO(Connor): Add tests for auto update
    }
}