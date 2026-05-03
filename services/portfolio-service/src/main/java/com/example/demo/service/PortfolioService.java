package com.example.demo.service;

import com.example.demo.model.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final AssetRepository repo;

    public PortfolioService(AssetRepository repo) {
        this.repo = repo;
    }

    public List<Asset> getAllAssets() {
        return repo.findAll();
    }

    public Asset addAsset(Asset asset) {
        return repo.save(asset);
    }

    public double calculateTotalValue() {
        return repo.findAll()
                .stream()
                .mapToDouble(a -> a.getQuantity() * a.getPrice())
                .sum();
    }

    public Map<String, Double> getAllocation() {
        List<Asset> assets = repo.findAll();

        double total = assets.stream()
                .mapToDouble(a -> a.getQuantity() * a.getPrice())
                .sum();

        return assets.stream()
                .collect(Collectors.toMap(
                        Asset::getSymbol,
                        a -> (a.getQuantity() * a.getPrice()) / total * 100));
    }

    public String getConcentrationRisk() {
        List<Asset> assets = repo.findAll();

        double total = assets.stream()
                .mapToDouble(a -> a.getQuantity() * a.getPrice())
                .sum();

        if (total == 0) {
            return "Portfolio is empty.";
        }

        double maxAllocation = assets.stream()
                .mapToDouble(a -> (a.getQuantity() * a.getPrice()) / total)
                .max()
                .orElse(0);

        if (maxAllocation > 0.5) {
            return "High concentration risk: more than 50% in a single asset.";
        } else if (maxAllocation > 0.3) {
            return "Moderate concentration risk: consider diversifying.";
        } else {
            return "Low concentration risk: portfolio is well diversified.";
        }
    }

}
