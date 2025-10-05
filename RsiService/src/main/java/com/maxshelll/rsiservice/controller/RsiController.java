package com.maxshelll.rsiservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rsi")
public class RsiController {

    @GetMapping
    public ResponseEntity<Integer> get(@RequestParam String symbol, @RequestParam String interval)
    {
        return ResponseEntity.ok(25);
    }
}
