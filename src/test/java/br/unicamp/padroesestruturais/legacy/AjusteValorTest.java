package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.ajuste.AjusteAntecipacaoRecebiveis;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteDescontoFidelidade;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteEmissaoNotaFiscal;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteJurosParcelamento;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteSeguro;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteTaxaInternacional;
import br.unicamp.padroesestruturais.legacy.service.CobrancaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AjusteValorTest {

    private final CobrancaService service = new CobrancaService();

    @Test
    void deveAplicarAjusteAntecipacaoRecebiveis() {
        double valor = service.calcularValorFinal(1000.0, new AjusteAntecipacaoRecebiveis());
        assertEquals(1015.0, valor, 0.001);
    }

    @Test
    void deveAplicarAjusteEmissaoNotaFiscal() {
        double valor = service.calcularValorFinal(1000.0, new AjusteEmissaoNotaFiscal());
        assertEquals(1002.50, valor, 0.001);
    }

    @Test
    void deveAplicarMultiplesAjustesNaOrdemDefinida() {
        double valor = service.calcularValorFinal(1000.0,
                new AjusteDescontoFidelidade(),
                new AjusteTaxaInternacional(),
                new AjusteAntecipacaoRecebiveis(),
                new AjusteEmissaoNotaFiscal());

        double esperado = 1000.0;
        esperado = esperado - (esperado * 0.05);
        esperado = esperado + (esperado * 0.05);
        esperado = esperado + (esperado * 0.015);
        esperado = esperado + 2.50;

        assertEquals(esperado, valor, 0.001);
    }

    @Test
    void deveAplicarAjustesEmOrdemDiferente() {
        double valor = service.calcularValorFinal(1000.0,
                new AjusteAntecipacaoRecebiveis(),
                new AjusteDescontoFidelidade(),
                new AjusteEmissaoNotaFiscal());

        double esperado = 1000.0;
        esperado = esperado + (esperado * 0.015);
        esperado = esperado - (esperado * 0.05);
        esperado = esperado + 2.50;

        assertEquals(esperado, valor, 0.001);
        assertNotEquals(1000.0, valor);
    }
}
