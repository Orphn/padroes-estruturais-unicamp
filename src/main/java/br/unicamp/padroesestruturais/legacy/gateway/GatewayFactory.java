package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;

public class GatewayFactory {
    
    public static GatewayPagamento obterGateway(FormaPagamento forma) {
        if (forma == null) {
            throw new IllegalArgumentException("Forma de pagamento nao suportada: " + forma);
        }

        switch (forma) {
            case BOLETO:
            case PIX:
                return new GatewayPagamentoInternoAdapter();
            case CARTAO_CREDITO:
                return new PaySecureAdapter();
            case CARTEIRA_DIGITAL:
                return new WalletPayAdapter();
            default:
                throw new IllegalArgumentException("Forma de pagamento nao suportada: " + forma);
        }
    }
}