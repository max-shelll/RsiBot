package com.maxshelll.rsiservice.controller;

import com.maxshelll.rsiservice.service.RSI.RSIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bingX/rsi")
public class BingXRsiController {

    private final RSIService rsiService;

    @GetMapping
    public ResponseEntity<Double> get(@RequestParam String symbol, @RequestParam String interval) {
        return ResponseEntity.ok(rsiService.fetchRSI(symbol, interval));
    }
}
