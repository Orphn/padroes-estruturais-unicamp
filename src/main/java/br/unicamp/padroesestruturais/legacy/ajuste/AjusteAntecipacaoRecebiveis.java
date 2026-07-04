package br.unicamp.padroesestruturais.legacy.ajuste;

public class AjusteAntecipacaoRecebiveis implements AjusteValor {

    private static final double TAXA_ANTECIPACAO = 0.015;

    @Override
    public double aplicar(double valor) {
        return valor + (valor * TAXA_ANTECIPACAO);
    }
}
