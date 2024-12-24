package com.maojie.trading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.maojie.trading.domain.OrderStatus;
import com.maojie.trading.domain.OrderType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private BigDecimal price;

    private LocalDateTime localDateTime = LocalDateTime.now();

    @Column(nullable = false)
    private OrderStatus status;

    @OneToOne (mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;

}
