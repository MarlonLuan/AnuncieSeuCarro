package com.marlonluan.anuncieseucarro.util;

import com.marlonluan.anuncieseucarro.*;

import java.text.DecimalFormat;

public class Auxiliar {

    public static String FormataDinheiro(Double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return "R$ " + decimalFormat.format(valor);

    }
}
