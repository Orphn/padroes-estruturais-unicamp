package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.externo.GatewayIndisponivelException;
import br.unicamp.padroesestruturais.legacy.externo.PaySecureGateway;
import br.unicamp.padroesestruturais.legacy.externo.TransacaoExterna;

import java.util.HashMap;
import java.util.Map;

public class PaySecureAdapter implements GatewayPagamento {
    private final PaySecureGateway gateway;

    public PaySecureAdapter() {
        this.gateway = new PaySecureGateway();
    }

    @Override
    public ResultadoCobranca processarCobranca(Pedido pedido, double valorFinal, FormaPagamento formaPagamento) {
        Map<String, Object> dadosTransacao = new HashMap<>();
        dadosTransacao.put("orderId", pedido.getId());
        dadosTransacao.put("customerName", pedido.getCliente());
        dadosTransacao.put("amount", valorFinal);
        dadosTransacao.put("currency", "BRL");

        try {
            TransacaoExterna transacao = gateway.processarTransacao(dadosTransacao);
            String status = transacao.getCodigoStatus() == 200 ? "APROVADA" : "RECUSADA";
            return new ResultadoCobranca(pedido.getId(), valorFinal, status, transacao.getReferenciaExterna(), formaPagamento);
        } catch (GatewayIndisponivelException e) {
            return new ResultadoCobranca(pedido.getId(), valorFinal, "RECUSADA", null, formaPagamento);
        }
    }
}