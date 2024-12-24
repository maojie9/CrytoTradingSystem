package com.maojie.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HuobiCoin {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("ask")
    private double ask;

}
