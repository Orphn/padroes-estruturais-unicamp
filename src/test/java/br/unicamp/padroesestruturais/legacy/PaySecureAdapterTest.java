package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.PaySecureAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaySecureAdapterTest {

    @Test
    void deveAprovarTransacaoPaySecure() {
        PaySecureAdapter adapter = new PaySecureAdapter();
        Pedido pedido = new Pedido("PED-001", "Joao Silva", "Notebook", 500.0);

        ResultadoCobranca resultado = adapter.processarCobranca(pedido, 500.0, FormaPagamento.CARTAO_CREDITO);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(500.0, resultado.getValorCobrado(), 0.001);
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("PSEC-"));
    }

    @Test
    void deveRecusarTransacaoPaySecureAcimaDoLimite() {
        PaySecureAdapter adapter = new PaySecureAdapter();
        Pedido pedido = new Pedido("PED-999", "Construtora ABC Ltda", "Servidor", 15000.0);

        ResultadoCobranca resultado = adapter.processarCobranca(pedido, 15000.0, FormaPagamento.CARTAO_CREDITO);

        assertEquals("RECUSADA", resultado.getStatus());
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("PSEC-"));
    }
}
