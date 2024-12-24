package com.maojie.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BinanceCoin {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("bidPrice")
    private double bidPrice;

    @JsonProperty("askPrice")
    private double askPrice;

}
