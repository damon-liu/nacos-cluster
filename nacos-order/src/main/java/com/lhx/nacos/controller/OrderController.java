package com.lhx.nacos.controller;

import com.lhx.nacos.fegin.PaymentClient;
import com.lhx.pojo.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 *
 * @author damon.liu
 * Date 2022-12-12 6:58
 */
@RestController
@RequestMapping(value = "/order")
@RefreshScope
public class OrderController {

    @Value("${depart.name}")
    private String departName;
    @Autowired
    private PaymentClient paymentClient;

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Integer id) {
        System.out.println("departName： " + departName);
        ResponseEntity<Payment> payment1 = paymentClient.payment(id);
        Payment payment = payment1.getBody();
        return ResponseEntity.ok(payment);
    }

}
