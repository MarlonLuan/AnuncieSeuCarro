package com.marlonluan.anuncieseucarro.util;

import com.marlonluan.anuncieseucarro.*;
import com.marlonluan.anuncieseucarro.carro.*;
import com.marlonluan.anuncieseucarro.mapas.*;

import java.text.DecimalFormat;

public final class Auxiliar {

    public static String FormataDinheiro(Double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return "R$ " + decimalFormat.format(valor);
    }
}
