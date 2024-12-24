package com.maojie.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BinanceCoin {

    @Id
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("bidPrice")
    private double bidPrice;

    @JsonProperty("askPrice")
    private double askPrice;

}
