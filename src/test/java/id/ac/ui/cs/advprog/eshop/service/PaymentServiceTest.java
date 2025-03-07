package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
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

        this.payments = new ArrayList<>();
        Payment payment1 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078", PaymentMethod.VOUCHER.getValue(), voucherDetails, order1);
        payments.add(payment1);
        Payment payment2 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e", PaymentMethod.BANK.getValue(), bankDetails, order2);
        payments.add(payment2);
    }

    @Test
    void testCreatePayment() {
        Payment payment = payments.get(1);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(1);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), updatedPayment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(1);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment savedPayment = invocation.getArgument(0);
            return savedPayment;
        });

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusInvalidStatus() {
        Payment payment = payments.get(1);

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "INVALID_STATUS"));

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testSetStatusNullPayment() {
        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus(null, PaymentStatus.SUCCESS.getValue()));

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testGetPaymentByIdIfIdFound() {
        Payment payment = payments.get(1);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentByIdIfIdNotFound() {
        when(paymentRepository.findById("invalid_id")).thenReturn(null);

        Payment result = paymentService.getPayment("invalid_id");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.getAll()).thenReturn(payments);

        Collection<Payment> allPayments = paymentService.getAllPayments();

        assertEquals(2, allPayments.size());
        assertTrue(allPayments.containsAll(payments));
    }
}
