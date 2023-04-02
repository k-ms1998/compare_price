package com.example.comparePrice.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface LowestPriceService {

    Set getZSetValue(String key);

}
