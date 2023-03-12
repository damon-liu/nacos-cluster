package com.lhx.nacos.fegin;

import com.lhx.pojo.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-01-04 3:59
 */
@FeignClient(value = "msc-payment", fallback = PaymentClient.Fallback.class)
@RequestMapping("/payment")
public interface PaymentClient {

    @GetMapping("/{id}")
    ResponseEntity<Payment> payment(@PathVariable("id") int id);

    // @PostMapping("/save")
    // boolean savePayment(@RequestBody Payment payment);
    //
    // @DeleteMapping("/del/{id}")
    // boolean removePaymentById(@PathVariable("id") int id);
    //
    // @PutMapping("/update")
    // boolean modifyPayment(@RequestBody Payment payment);
    //
    // @GetMapping("/list")
    // List<Payment> listPayment();

    @Component
    @RequestMapping("/fallback_payment")
    class Fallback implements PaymentClient {

        @Override
        public ResponseEntity<Payment> payment(int id) {
            Payment payment = new Payment(id, "熔断降级方法返回");
            return ResponseEntity.ok(payment);
        }
    }
}
