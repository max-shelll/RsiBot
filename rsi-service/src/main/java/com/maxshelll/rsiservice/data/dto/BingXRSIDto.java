package com.maxshelll.rsiservice.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class BingXRSIDto {
    private int code;
    private String msg;
    private List<KlineDto> data;
}
