package com.maojie.trading.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Coin {

    @Id
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("bidPrice")
    private double bidPrice;

    @JsonProperty("askPrice")
    private double askPrice;

    @JsonProperty("lastUpdated")
    private Date lastUpdated;
}
