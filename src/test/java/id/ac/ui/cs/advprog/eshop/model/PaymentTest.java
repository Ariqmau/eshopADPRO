package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentTest {
    private List<Product> products;
    private Order order;
    private Map<String, String> voucherDetails;
    private Map<String, String> bankDetails;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708560000L, "Safira Sudrajat");

        this.voucherDetails = new HashMap<>();
        this.voucherDetails.put("voucherCode", "ESHOP1234ABC5678");

        this.bankDetails = new HashMap<>();
        this.bankDetails.put("bankName", "BANK_NAME");
        this.bankDetails.put("referenceCode", "REFERENCE_CODE");
    }

    @Test
    void testCreatePaymentWithValidVoucherDetails() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), this.voucherDetails, this.order);

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(this.voucherDetails, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherLengthTooLong() {
        Map<String, String> voucherTooLong = new HashMap<>();
        voucherTooLong.put("voucherCode", "ESHOP1234ABC5678910");
        Payment paymentTooLong = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), voucherTooLong, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentTooLong.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherLengthTooShort() {
        Map<String, String> voucherTooShort = new HashMap<>();
        voucherTooShort.put("voucherCode", "ESHOP1234ABC56");
        Payment paymentTooShort = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), voucherTooShort, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentTooShort.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherNotStartWithESHOP() {
        Map<String, String> voucherNotStartWithESHOP = new HashMap<>();
        voucherNotStartWithESHOP.put("voucherCode", "1234ABC5678910");
        Payment paymentTooShort = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), voucherNotStartWithESHOP, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentTooShort.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherInvalidNumerical() {
        Map<String, String> voucherNotEightNumerical = new HashMap<>();
        voucherNotEightNumerical.put("voucherCode", "ABCDEFG5678910");
        Payment paymentTooShort = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getValue(), voucherNotEightNumerical, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentTooShort.getStatus());
    }

    @Test
    void testCreatePaymentWithValidBankDetails() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK.getValue(), this.bankDetails, this.order);

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.BANK.getValue(), payment.getMethod());
        assertEquals(this.bankDetails, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyBankName() {
        Map<String, String> bankEmptyName = new HashMap<>();
        bankEmptyName.put("bankName", "");
        bankEmptyName.put("referenceCode", "REFERENCE_CODE");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK.getValue(), bankEmptyName, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithEmptyBankReferenceCode() {
        Map<String, String> bankEmptyName = new HashMap<>();
        bankEmptyName.put("bankName", "BANK_NAME");
        bankEmptyName.put("referenceCode", "");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK.getValue(), bankEmptyName, this.order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    "INVALID_METHOD", this.voucherDetails, this.order);
        });
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        this.voucherDetails.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    PaymentMethod.VOUCHER.getValue(), this.voucherDetails, this.order);
        });
    }

    @Test
    void testCreatePaymentNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    PaymentMethod.VOUCHER.getValue(), this.voucherDetails, null);
        });
    }
}
