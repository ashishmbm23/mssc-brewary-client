package com.ashish.msscbrewaryclient.client;

import com.ashish.msscbrewaryclient.web.v1.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class BrewaryClientTest {

    @Autowired
    BrewaryClient brewaryClient;

    @Value("${test.uuid.value:546baee0-c9e2-4a93-803b-e2ff8d4398d4}")
    private String id ;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getBeerById() {
        UUID uuid = UUID.fromString(id);
        BeerDto beerDto = brewaryClient.getBeerById(uuid);
        assertNotNull(beerDto);
    }

    @Test
    void saveBeer(){
        BeerDto beerDto = BeerDto.builder()
                .beerName("Pilsner")
                .upc(11231321331l)
                .quantityOnHand(100l)
                .beerStyle("LAGER")
                .createdDate(OffsetDateTime.of(2024, 2, 10, 12, 0, 0, 0, ZoneOffset.UTC))
                .lastUpdatedDate(OffsetDateTime.of(2024, 2, 10, 12, 0, 0, 0, ZoneOffset.UTC))
                .build();
        URI uri = brewaryClient.saveBeer(beerDto);
        assertNotNull(uri);
        System.out.println(uri.toString());
    }

    @Test
    void beanValidation(){
        BeerDto beerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Pilsner")
                .build();
        brewaryClient.saveBeer(beerDto);
    }
}