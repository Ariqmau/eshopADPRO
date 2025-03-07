package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentRepository {
    private final Map<String, Payment> payments = new HashMap<>();

    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(String id) {
        return payments.get(id);
    }

    public Collection<Payment> getAll() {
        return payments.values();
    }
}
