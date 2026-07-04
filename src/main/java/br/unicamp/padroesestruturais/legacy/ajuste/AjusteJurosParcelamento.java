package br.unicamp.padroesestruturais.legacy.ajuste;

public class AjusteJurosParcelamento implements AjusteValor {

    private static final double TAXA_JUROS_PARCELAMENTO = 0.0299;

    @Override
    public double aplicar(double valor) {
        return valor + (valor * TAXA_JUROS_PARCELAMENTO);
    }
}
