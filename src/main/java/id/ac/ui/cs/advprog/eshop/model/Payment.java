package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    Order order;
    String status;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;

        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException();
        } else {
            this.method = method;
        }

        if (paymentData == null || paymentData.isEmpty() || order == null) {
            throw new IllegalArgumentException();
        } else {
            this.paymentData = paymentData;
            this.order = order;
        }

        if (method.equals(PaymentMethod.VOUCHER.getValue()) && isVoucherDataValid()) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else if (method.equals(PaymentMethod.BANK.getValue()) && isBankDataValid()) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    private boolean isVoucherDataValid() {
        String voucher = paymentData.get("voucherCode");
        return (voucher != null && voucher.length() == 16) && voucher.startsWith("ESHOP") && countDigit(voucher) == 8;
    }

    private boolean isBankDataValid() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        return bankName != null && referenceCode != null && !bankName.isEmpty() && !referenceCode.isEmpty();
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }

    private int countDigit(String code){
        int count = 0;
        for (int i = 0; i < code.length(); i++) {
            if (Character.isDigit(code.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}