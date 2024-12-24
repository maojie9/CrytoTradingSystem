package com.maojie.trading.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maojie.trading.domain.OrderStatus;
import com.maojie.trading.domain.OrderType;
import com.maojie.trading.model.Asset;
import com.maojie.trading.model.Coin;
import com.maojie.trading.model.Order;
import com.maojie.trading.model.OrderItem;
import com.maojie.trading.repository.OrderItemRepository;
import com.maojie.trading.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AssetService assetService;




    @Override
    public com.maojie.trading.model.Order createOrder(OrderItem orderItem, OrderType orderType) {
        
        double price = (orderType.equals(OrderType.BUY)?
        orderItem.getCoin().getAskPrice():
        orderItem.getCoin().getBidPrice())*orderItem.getQuantity();

        Order order = new Order();
        order.setWallet(walletService.getUserWallet());
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public com.maojie.trading.model.Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public List<com.maojie.trading.model.Order> getAllOrder(OrderType orderType, String assetSymbol) {
        return orderRepository.findAll();
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity) throws Exception{
        if(quantity <= 0) {
            throw new Exception("Quantity must be more than 0");
        }

        double buyPrice = coin.getAskPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
        Order order = createOrder(orderItem, OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        Order saveOrder = orderRepository.save(order);

        Asset oldAsset = assetService.findAssetByCoin(coin.getSymbol());

        if(oldAsset == null){
            assetService.createAsset(orderItem.getCoin(), orderItem.getQuantity());
        }else {
            assetService.updateAsset(oldAsset.getId(), quantity);
        }


        return saveOrder;
    }

    @Transactional
    public Order sellAsset(Coin coin, double quantity) throws Exception{
        if(quantity <= 0) {
            throw new Exception("Quantity must be more than 0");
        }

        Asset assetToSell = assetService.findAssetByCoin(coin.getSymbol());

        if(assetToSell == null)
            throw new Exception("Unable to sell asset, asset not found");

        double sellPrice = coin.getBidPrice();
        double buyPrice = assetToSell.getBuyPrice ();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);

        Order order = createOrder(orderItem, OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity()<quantity)
            throw new Exception("Insufficient quantity to Sell");

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.SELL);
        
        Order saveOrder = orderRepository.save(order);
        walletService.payOrderPayment(order);

        Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
        if(updatedAsset.getQuantity()<=0){
            assetService.deleteAsset(updatedAsset.getId());
        }
        return saveOrder;
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType) throws Exception {
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin, quantity);
        }else if (orderType.equals(OrderType.SELL)){
            return sellAsset(coin, quantity);
        }
        throw new Exception("Invalid Order Type");
    }

}
