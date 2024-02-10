package com.ashish.msscbrewaryclient.client;

import com.ashish.msscbrewaryclient.web.v1.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@ConfigurationProperties(value = "mssc.brewary.client")
public class BrewaryClient {
    private String apihost;
    private RestTemplate restTemplate;
    private final String BEER_BASE_URL = "/beer";

    public BrewaryClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public BeerDto getBeerById(UUID id){
        return restTemplate.getForObject(apihost + "/" + BEER_BASE_URL
        + "/" + id.toString(), BeerDto.class);
    }

    public void setApihost(String apihost){
        this.apihost = apihost;
    }


}
