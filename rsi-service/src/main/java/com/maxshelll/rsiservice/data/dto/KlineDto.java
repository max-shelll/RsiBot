package com.maxshelll.rsiservice.data.dto;

import lombok.Data;

@Data
public class KlineDto {
    private final long openTime;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;
}
