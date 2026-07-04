package br.unicamp.padroesestruturais.legacy.service;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayFactory;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteAntecipacaoRecebiveis;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteDescontoFidelidade;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteEmissaoNotaFiscal;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteJurosParcelamento;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteSeguro;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteTaxaInternacional;
import br.unicamp.padroesestruturais.legacy.ajuste.AjusteValor;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayPagamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CobrancaService {

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma, AjusteValor... ajustes) {
        double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);

        GatewayPagamento gateway = GatewayFactory.obterGateway(forma);
        return gateway.processarCobranca(pedido, valorFinal, forma);
    }

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma,
                                   boolean aplicarDescontoFidelidade,
                                   boolean aplicarJurosParcelamento,
                                   boolean aplicarTaxaInternacional,
                                   boolean aplicarSeguro) {
        return cobrar(pedido, forma, criarAjustes(aplicarDescontoFidelidade,
                aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro));
    }

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos, FormaPagamento forma, AjusteValor... ajustes) {
        List<ResultadoCobranca> resultados = new ArrayList<>();
        GatewayPagamento gateway = GatewayFactory.obterGateway(forma);

        for (Pedido pedido : pedidos) {
            double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);
            resultados.add(gateway.processarCobranca(pedido, valorFinal, forma));
        }

        return resultados;
    }

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos, FormaPagamento forma,
                                                 boolean aplicarDescontoFidelidade,
                                                 boolean aplicarJurosParcelamento,
                                                 boolean aplicarTaxaInternacional,
                                                 boolean aplicarSeguro) {
        return cobrarEmLote(pedidos, forma, criarAjustes(aplicarDescontoFidelidade,
                aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro));
    }

    public double calcularValorFinal(double valorBase, AjusteValor... ajustes) {
        double valor = valorBase;

        if (ajustes == null || ajustes.length == 0) {
            return valor;
        }

        for (AjusteValor ajuste : ajustes) {
            Objects.requireNonNull(ajuste, "AjusteValor nao pode ser null");
            valor = ajuste.aplicar(valor);
        }

        return valor;
    }

    public double calcularValorFinal(double valorBase,
                                      boolean aplicarDescontoFidelidade,
                                      boolean aplicarJurosParcelamento,
                                      boolean aplicarTaxaInternacional,
                                      boolean aplicarSeguro) {
        return calcularValorFinal(valorBase, criarAjustes(aplicarDescontoFidelidade,
                aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro));
    }

    private AjusteValor[] criarAjustes(boolean aplicarDescontoFidelidade,
                                      boolean aplicarJurosParcelamento,
                                      boolean aplicarTaxaInternacional,
                                      boolean aplicarSeguro) {
        List<AjusteValor> ajustes = new ArrayList<>();

        if (aplicarDescontoFidelidade) {
            ajustes.add(new AjusteDescontoFidelidade());
        }
        if (aplicarJurosParcelamento) {
            ajustes.add(new AjusteJurosParcelamento());
        }
        if (aplicarTaxaInternacional) {
            ajustes.add(new AjusteTaxaInternacional());
        }
        if (aplicarSeguro) {
            ajustes.add(new AjusteSeguro());
        }

        return ajustes.toArray(new AjusteValor[0]);
    }
}