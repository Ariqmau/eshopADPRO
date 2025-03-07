package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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

        String[] methodList = {"VOUCHER", "BANK"};
        if (Arrays.stream(methodList).noneMatch(item -> (item.equals(method)))) {
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

        if (method.equals("VOUCHER") && isVoucherDataValid()) {
            this.status = "SUCCESS";
        } else if (method.equals("BANK") && isBankDataValid()) {
            this.status = "SUCCESS";
        } else {
            this.status = "REJECTED";
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