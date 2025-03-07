package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product1);
        products.add(product2);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("7f9e15bb-012a-4c07-b546-8ee697ecbf1e",
                products, 1708560000L, "Bambang Sudrajat");

        Map<String, String> voucherDetails = new HashMap<>();
        voucherDetails.put("voucherCode", "ESHOP1234ABC5678");
        Map<String, String> bankDetails = new HashMap<>();
        bankDetails.put("bankName", "BANK_NAME");
        bankDetails.put("referenceCode", "REFERENCE_CODE");

        Payment payment1 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078", PaymentMethod.VOUCHER.getValue(), voucherDetails, order1);
        payments.add(payment1);
        Payment payment2 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e", PaymentMethod.BANK.getValue(), bankDetails, order2);
        payments.add(payment2);
    }

    @Test
    void testSavePayment() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(result.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
    }

    @Test
    void testUpdatePayment() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Map<String, String> newPaymentDetail = new HashMap<>();
        newPaymentDetail.put("bankName", "BANK_NAME");
        newPaymentDetail.put("referenceCode", "REFERENCE_CODE");
        Payment newPayment = new Payment("7148aec0-26ea-47a1-aafe-c9eb598985f8",
                PaymentMethod.BANK.getValue(), newPaymentDetail, payment.getOrder());
        Payment result = paymentRepository.save(newPayment);
        Payment findResult = paymentRepository.findById(result.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertEquals(PaymentMethod.BANK.getValue(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
    }

    @Test
    void testFindPaymentByIdAndFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findPayment = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findPayment.getId());
        assertEquals(payments.get(1).getPaymentData(), findPayment.getPaymentData());
        assertEquals(payments.get(1).getOrder(), findPayment.getOrder());
        assertEquals(payments.get(1).getStatus(), findPayment.getStatus());
        assertEquals(payments.get(1).getMethod(), findPayment.getMethod());
    }

    @Test
    void testFindPaymentByIdAndNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findPayment = paymentRepository.findById("INVALID_ID");
        assertNull(findPayment);
    }

    @Test
    void testFindAllPaymentsIfEmpty() {
        Iterator<Payment> paymentIterator = paymentRepository.getAll();
        assertFalse(paymentIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOnePayment() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Iterator<Payment> paymentIterator = paymentRepository.getAll();
        assertTrue(paymentIterator.hasNext());
        Payment firstPayment = paymentIterator.next();
        assertEquals(payments.get(0).getId(), firstPayment.getId());
        assertEquals(payments.get(0).getPaymentData(), firstPayment.getPaymentData());
        assertEquals(payments.get(0).getOrder(), firstPayment.getOrder());
        assertEquals(payments.get(0).getStatus(), firstPayment.getStatus());
        assertEquals(payments.get(0).getMethod(), firstPayment.getMethod());
        Payment secondPayment = paymentIterator.next();
        assertEquals(payments.get(1).getId(), secondPayment.getId());
        assertEquals(payments.get(1).getPaymentData(), secondPayment.getPaymentData());
        assertEquals(payments.get(1).getOrder(), secondPayment.getOrder());
        assertEquals(payments.get(1).getStatus(), secondPayment.getStatus());
        assertEquals(payments.get(1).getMethod(), secondPayment.getMethod());
        assertFalse(paymentIterator.hasNext());
    }
}
