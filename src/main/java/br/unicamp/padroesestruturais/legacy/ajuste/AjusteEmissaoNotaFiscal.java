package br.unicamp.padroesestruturais.legacy.ajuste;

public class AjusteEmissaoNotaFiscal implements AjusteValor {

    private static final double VALOR_NOTA_FISCAL = 2.50;

    @Override
    public double aplicar(double valor) {
        return valor + VALOR_NOTA_FISCAL;
    }
}
