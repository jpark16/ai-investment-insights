package com.example.demo.service;

import com.example.demo.model.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@Service
public class PortfolioService {

    private final AssetRepository repo;
    private final OpenAIService openAIService;
    private final MarketDataService marketDataService;

    public PortfolioService(AssetRepository repo,
            OpenAIService openAIService,
            MarketDataService marketDataService) {
        this.repo = repo;
        this.openAIService = openAIService;
        this.marketDataService = marketDataService;
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

    public String generateInsights() {
        List<Asset> assets = repo.findAll();

        if (assets.isEmpty()) {
            return "Portfolio is empty.";
        }

        StringBuilder prompt = new StringBuilder("Analyze this investment portfolio:\n");

        for (Asset a : assets) {
            prompt.append(a.getSymbol())
                    .append(" - value: ")
                    .append(a.getQuantity() * a.getPrice())
                    .append(", sector: ")
                    .append(a.getSector())
                    .append("\n");
        }

        prompt.append("Provide a short professional investment risk and diversification insight.");

        return openAIService.getAIInsights(prompt.toString());
    }

    public Map<String, Double> getSectorAllocation() {
        List<Asset> assets = repo.findAll();

        double total = assets.stream()
                .mapToDouble(a -> a.getQuantity() * a.getPrice())
                .sum();

        return assets.stream()
                .collect(Collectors.groupingBy(
                        Asset::getSector,
                        Collectors.summingDouble(a -> a.getQuantity() * a.getPrice())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (e.getValue() / total) * 100));
    }

    public void refreshPrices() {
        Map<String, Double> prices = marketDataService.fetchPrices();

        List<Asset> assets = repo.findAll();

        for (Asset asset : assets) {
            if (prices.containsKey(asset.getSymbol())) {
                asset.setPrice(prices.get(asset.getSymbol()));
            }
        }

        repo.saveAll(assets);
    }
}
