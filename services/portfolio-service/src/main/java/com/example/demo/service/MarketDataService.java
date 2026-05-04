package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarketDataService {

    // Mock API (replace later with real API)
    public Map<String, Double> fetchPrices() {
        Map<String, Double> prices = new HashMap<>();

        prices.put("AAPL", 185.0);
        prices.put("MSFT", 310.0);
        prices.put("GOOGL", 140.0);
        prices.put("JPM", 150.0);

        return prices;
    }
}