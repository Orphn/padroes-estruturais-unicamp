package br.unicamp.padroesestruturais.legacy.ajuste;

public class AjusteSeguro implements AjusteValor {

    private static final double VALOR_SEGURO = 4.90;

    @Override
    public double aplicar(double valor) {
        return valor + VALOR_SEGURO;
    }
}
