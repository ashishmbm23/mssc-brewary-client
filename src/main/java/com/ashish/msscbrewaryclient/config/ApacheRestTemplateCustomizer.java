package com.ashish.msscbrewaryclient.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ApacheRestTemplateCustomizer implements RestTemplateCustomizer {

    @Value("${conn.manager.total.connection:100}")
    private Integer totalConnection;

    @Value("${conn.manager.conn.per.route:20}")
    private Integer maxConnectionPerRoute;

    @Value("${req.config.conn.req.timeout:3000}")
    private Long connectionRequestTimeout;

    @PostConstruct
    public void init(){
        log.info("totalConnection:" + totalConnection);
        log.info("maxConnectionPerRoute:" + maxConnectionPerRoute);
        log.info("connectionRequestTimeout:" + connectionRequestTimeout);
    }
    public ClientHttpRequestFactory getClientHttpRequestFactory(){
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(totalConnection);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxConnectionPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(Timeout.of(connectionRequestTimeout, TimeUnit.MILLISECONDS))
                .build();

        CloseableHttpClient closeableHttpClient = HttpClients
                .custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);

    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
    }
}
