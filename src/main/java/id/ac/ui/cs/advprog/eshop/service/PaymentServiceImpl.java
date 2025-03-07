package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String id = UUID.randomUUID().toString();
        Payment payment = new Payment(id, method, paymentData, order);
        if (payment.getStatus().equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (payment == null) {
            throw new NoSuchElementException();
        }
        payment.setStatus(status);

        updateOrderStatus(payment);

        return paymentRepository.save(payment);
    }

    private void updateOrderStatus(Payment payment) {
        if (payment == null || payment.getOrder() == null) return;

        OrderStatus orderStatus = payment.getStatus().equals(PaymentStatus.SUCCESS.getValue())
                ? OrderStatus.SUCCESS
                : OrderStatus.FAILED;

        payment.getOrder().setStatus(orderStatus.getValue());
    }

    @Override
    public Payment getPayment(String id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Collection<Payment> getAllPayments() {
        return paymentRepository.getAll();
    }
}