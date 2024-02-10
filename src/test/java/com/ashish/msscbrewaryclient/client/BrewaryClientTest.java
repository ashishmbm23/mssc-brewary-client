package com.ashish.msscbrewaryclient.client;

import com.ashish.msscbrewaryclient.web.v1.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class BrewaryClientTest {

    @Autowired
    BrewaryClient brewaryClient;
    @BeforeEach
    void setUp() {
    }

    @Test
    void getBeerById() {
        BeerDto beerDto = brewaryClient.getBeerById(UUID.randomUUID());
        assertNotNull(beerDto);
    }

    @Test
    void saveBeer(){
        BeerDto beerDto = BeerDto.builder().beerName("Pilsner").upc(11231321331l).build();
        URI uri = brewaryClient.saveBeer(beerDto);
        assertNotNull(uri);
        System.out.println(uri.toString());
    }
}