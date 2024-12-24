package com.maojie.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class HuobiCoin {

    @Id
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("ask")
    private double ask;

}
