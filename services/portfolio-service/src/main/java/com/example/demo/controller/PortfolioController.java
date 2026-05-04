package com.example.demo.controller;

import com.example.demo.model.Asset;
import com.example.demo.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService service;

    public PortfolioController(PortfolioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Asset> getAll() {
        return service.getAllAssets();
    }

    @PostMapping
    public Asset add(@RequestBody Asset asset) {
        return service.addAsset(asset);
    }

    @GetMapping("/value")
    public double totalValue() {
        return service.calculateTotalValue();
    }

    @GetMapping("/allocation")
    public Map<String, Double> allocation() {
        return service.getAllocation();
    }

    @GetMapping("/risk")
    public String concentrationRisk() {
        return service.getConcentrationRisk();
    }

    @GetMapping("/insights")
    public String insights() {
        return service.generateInsights();
    }

    @GetMapping("/sector-allocation")
    public Map<String, Double> sectorAllocation() {
        return service.getSectorAllocation();
    }

    @PostMapping("/refresh")
    public String refreshPrices() {
        service.refreshPrices();
        return "Prices updated from market data.";
    }
}