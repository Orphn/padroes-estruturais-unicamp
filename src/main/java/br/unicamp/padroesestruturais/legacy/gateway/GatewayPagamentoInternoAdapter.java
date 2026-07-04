package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;

public class GatewayPagamentoInternoAdapter implements GatewayPagamento {
    private final GatewayPagamentoInterno gatewayInterno;

    public GatewayPagamentoInternoAdapter() {
        this.gatewayInterno = new GatewayPagamentoInterno();
    }

    @Override
    public ResultadoCobranca processarCobranca(Pedido pedido, double valorFinal, FormaPagamento formaPagamento) {
        return gatewayInterno.cobrar(pedido.getId(), pedido.getCliente(), valorFinal, formaPagamento);
    }
}