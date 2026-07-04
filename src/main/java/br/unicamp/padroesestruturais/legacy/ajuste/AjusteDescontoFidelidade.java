package br.unicamp.padroesestruturais.legacy.ajuste;

public class AjusteDescontoFidelidade implements AjusteValor {

    private static final double TAXA_DESCONTO_FIDELIDADE = 0.05;

    @Override
    public double aplicar(double valor) {
        return valor - (valor * TAXA_DESCONTO_FIDELIDADE);
    }
}
