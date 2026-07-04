package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.WalletPayAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletPayAdapterTest {

    @Test
    void deveAprovarTransacaoWalletPay() {
        WalletPayAdapter adapter = new WalletPayAdapter();
        Pedido pedido = new Pedido("PED-002", "Maria Santos", "Cadeira", 500.0);

        ResultadoCobranca resultado = adapter.processarCobranca(pedido, 500.0, FormaPagamento.CARTEIRA_DIGITAL);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(500.0, resultado.getValorCobrado(), 0.001);
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("WPAY-"));
    }

    @Test
    void deveRecusarTransacaoWalletPayAcimaDoLimite() {
        WalletPayAdapter adapter = new WalletPayAdapter();
        Pedido pedido = new Pedido("PED-010", "Cliente X", "Servico", 15000.0);

        ResultadoCobranca resultado = adapter.processarCobranca(pedido, 15000.0, FormaPagamento.CARTEIRA_DIGITAL);

        assertEquals("RECUSADA", resultado.getStatus());
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("WPAY-"));
    }
}
