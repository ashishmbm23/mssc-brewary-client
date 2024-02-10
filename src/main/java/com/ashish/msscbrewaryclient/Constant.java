package com.ashish.msscbrewaryclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
    @Value("${mssc.brewary.client}")
    public static String CLIENT_API;
}
