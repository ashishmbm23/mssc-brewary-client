package com.ashish.msscbrewaryclient.client;

import com.ashish.msscbrewaryclient.web.v1.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "mssc.brewary.client", ignoreUnknownFields = false)
public class BrewaryClient {
    private String apihost;
    private RestTemplate restTemplate;
    private final String BEER_BASE_URL = "/beer";

    public BrewaryClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public BeerDto getBeerById(UUID id){
        return restTemplate.getForObject(getServerBaseUrl()
                + "/" + id.toString(), BeerDto.class);
    }

    private String getServerBaseUrl() {
        return apihost + "/" + BEER_BASE_URL;
    }

    public URI saveBeer(BeerDto beerDto){
        return restTemplate.postForLocation(getServerBaseUrl(), beerDto);
    }

    public void updateBeer(BeerDto beerDto, UUID id){
        restTemplate.put(getServerBaseUrl() + "/" + id, beerDto);
    }

    public void deleteBeer(UUID id){
        restTemplate.delete(getServerBaseUrl() + "/" + id);
    }

    public void setApihost(String apihost){
        this.apihost = apihost;
    }


}
