package com.furiafan.chat.util;

public class CpfValidator {

    public static boolean isValidCPF(String cpf) {
        if (cpf == null) return false;

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++)
                sum += (cpf.charAt(i) - '0') * (10 - i);
            int check1 = 11 - (sum % 11);
            check1 = (check1 >= 10) ? 0 : check1;
            if (check1 != (cpf.charAt(9) - '0')) return false;

            sum = 0;
            for (int i = 0; i < 10; i++)
                sum += (cpf.charAt(i) - '0') * (11 - i);
            int check2 = 11 - (sum % 11);
            check2 = (check2 >= 10) ? 0 : check2;
            return check2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}
