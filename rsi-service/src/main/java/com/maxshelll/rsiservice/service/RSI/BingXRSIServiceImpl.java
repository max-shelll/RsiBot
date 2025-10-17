package com.maxshelll.rsiservice.service.RSI;

import com.maxshelll.rsiservice.data.dto.BingXRSIDto;
import com.maxshelll.rsiservice.data.dto.KlineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BingXRSIServiceImpl implements RSIService {

    private static final int DEFAULT_RSI_PERIOD = 14;
    private static final int DEFAULT_KLINES_LIMIT = 20;

    private final WebClient webClient;

    @Override
    public Double fetchRSI(String symbol, String interval) {
        try {
            List<KlineDto> klines = fetchKlineDataFromBingX(symbol, interval);

            if (klines == null || klines.size() < DEFAULT_RSI_PERIOD + 1) {
                log.warn("Not enough data to calculate RSI for {} (received: {})", symbol, klines != null ? klines.size() : 0);
                return null;
            }

            return calculateRSI(klines);

        } catch (Exception e) {
            log.error("Error fetching RSI for {}: {}", symbol, e.getMessage());
            return null;
        }
    }

    private List<KlineDto> fetchKlineDataFromBingX(String symbol, String interval) {
        try {
            Mono<BingXRSIDto> responseMono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("symbol", symbol)
                            .queryParam("interval", interval)
                            .queryParam("limit", DEFAULT_KLINES_LIMIT)
                            .build())
                    .retrieve()
                    .bodyToMono(BingXRSIDto.class);

            BingXRSIDto response = responseMono.block();

            if (response == null) {
                log.error("Empty response from BingX for {}", symbol);
                return null;
            } else if (response.getCode() != 0) {
                log.error("BingX API error for {}: {}", symbol, response.getMsg());
                return null;
            }

            return response.getData();

        } catch (Exception e) {
            log.error("Exception fetching data from BingX for {}: {}", symbol, e.getMessage());
            return null;
        }
    }

    private Double calculateRSI(List<KlineDto> klines) {
        if (klines.size() < DEFAULT_RSI_PERIOD + 1) {
            return null;
        }

        try {
            List<Double> closes = new ArrayList<>();
            for (KlineDto kline : klines) {
                closes.add(kline.getClose());
            }

            List<Double> deltas = new ArrayList<>();
            for (int i = 1; i < closes.size(); i++) {
                deltas.add(closes.get(i) - closes.get(i - 1));
            }

            List<Double> gains = new ArrayList<>();
            List<Double> losses = new ArrayList<>();

            for (double delta : deltas) {
                gains.add(Math.max(delta, 0.0));
                losses.add(Math.max(-delta, 0.0));
            }

            double avgGain = gains.subList(0, DEFAULT_RSI_PERIOD).stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            double avgLoss = losses.subList(0, DEFAULT_RSI_PERIOD).stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            for (int i = DEFAULT_RSI_PERIOD; i < gains.size(); i++) {
                avgGain = (avgGain * (DEFAULT_RSI_PERIOD - 1) + gains.get(i)) / DEFAULT_RSI_PERIOD;
                avgLoss = (avgLoss * (DEFAULT_RSI_PERIOD - 1) + losses.get(i)) / DEFAULT_RSI_PERIOD;
            }

            if (avgLoss == 0) {
                return 100.0;
            }

            double rs = avgGain / avgLoss;
            double rsi = 100.0 - (100.0 / (1.0 + rs));

            return Math.round(rsi * 100.0) / 100.0;

        } catch (Exception e) {
            log.error("Error calculating RSI: {}", e.getMessage());
            return null;
        }
    }
}
