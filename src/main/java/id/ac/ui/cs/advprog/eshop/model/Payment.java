package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    Order order;
    @Setter
    String status;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
    }
}