package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.externo.ChargeRequest;
import br.unicamp.padroesestruturais.legacy.externo.ChargeResponse;
import br.unicamp.padroesestruturais.legacy.externo.ChargeStatus;
import br.unicamp.padroesestruturais.legacy.externo.WalletPaySDK;

public class WalletPayAdapter implements GatewayPagamento {
    private final WalletPaySDK sdk;

    public WalletPayAdapter() {
        this.sdk = new WalletPaySDK();
    }

    @Override
    public ResultadoCobranca processarCobranca(Pedido pedido, double valorFinal, FormaPagamento formaPagamento) {
        long amountInCents = Math.round(valorFinal * 100);
        ChargeRequest request = new ChargeRequest(pedido.getId(), pedido.getCliente(), amountInCents);

        ChargeResponse response = sdk.charge(request);

        String status = response.getStatus() == ChargeStatus.CONFIRMED ? "APROVADA" : "RECUSADA";
        return new ResultadoCobranca(pedido.getId(), valorFinal, status, response.getWalletTransactionId(), formaPagamento);
    }
}