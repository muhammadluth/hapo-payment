package com.pulsa.utils;

import java.text.DecimalFormat;

public class NumberFormat {

    public static String setNumberformat(String numberformat) {
        String minus = "";
        String fee = "";
        if (numberformat.contains("-")) {
            minus = "-";
        }

        if (numberformat.contains("fee")) {
            fee = " (Fee)";
        }
        try {
            String value = numberformat.replaceAll("[^0-9]", "");
            double amount = Double.parseDouble(value);
            DecimalFormat formatter = null;

            if (value != null && !value.equals("")) {
                formatter = new DecimalFormat("#,###");
            }

            if (!value.equals(""))
                return "Rp " + minus + formatter.format(amount) + fee;
        } catch (Exception ex) {
            return "Rp " + numberformat + fee;
        }
        return "Rp " + "-" + fee;
    }
}
