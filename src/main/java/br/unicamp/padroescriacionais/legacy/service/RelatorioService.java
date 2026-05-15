package br.unicamp.padroescriacionais.legacy.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import br.unicamp.padroescriacionais.legacy.creator.CsvRelatorioCreator;
import br.unicamp.padroescriacionais.legacy.creator.HtmlRelatorioCreator;
import br.unicamp.padroescriacionais.legacy.creator.JsonRelatorioCreator;
import br.unicamp.padroescriacionais.legacy.creator.PdfRelatorioCreator;
import br.unicamp.padroescriacionais.legacy.creator.RelatorioCreator;
import br.unicamp.padroescriacionais.legacy.creator.XmlRelatorioCreator;
import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;

public class RelatorioService {
    private final Map<FormatoRelatorio, RelatorioCreator> creators = new HashMap<>();
    private ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstancia();
    
    public RelatorioService() {
        creators.put(FormatoRelatorio.PDF, new PdfRelatorioCreator());
        creators.put(FormatoRelatorio.CSV, new CsvRelatorioCreator());
        creators.put(FormatoRelatorio.JSON, new JsonRelatorioCreator());
        creators.put(FormatoRelatorio.XML, new XmlRelatorioCreator());
        creators.put(FormatoRelatorio.HTML, new HtmlRelatorioCreator());
    }

    public Relatorio criarRelatorio(TipoRelatorio tipo) {
        String titulo;
        String conteudo;

        switch (tipo) {
            case VENDAS:
                titulo = "Relatorio de Vendas";
                conteudo = gerarConteudoVendas();
                break;
            case ESTOQUE:
                titulo = "Relatorio de Estoque";
                conteudo = gerarConteudoEstoque();
                break;
            case CLIENTES:
                titulo = "Relatorio de Clientes";
                conteudo = gerarConteudoClientes();
                break;
            default:
                throw new IllegalArgumentException("Tipo de relatorio desconhecido: " + tipo);
        }

        return new Relatorio(titulo, conteudo, tipo, LocalDateTime.now());
    }

    public String gerarRelatorio(TipoRelatorio tipo, FormatoRelatorio formato) {
        Relatorio relatorio = criarRelatorio(tipo);

        if (configuracao.isDebugAtivo()) {
            System.out.println("[DEBUG-RelatorioService] Gerando: " + tipo + " -> " + formato);
        }
        
        RelatorioCreator creator = creators.get(formato);
        if (creator == null) {
            throw new IllegalArgumentException("Formato nao suportado: " + formato);
        }
        
        RelatorioGenerator generator = creator.createGenerator();
        return generator.gerar(relatorio);
    }

    private String gerarConteudoVendas() {
        return "Produto A: 150 unidades vendidas - R$ 12.000,00\n"
             + "Produto B: 230 unidades vendidas - R$ 23.000,00\n"
             + "Produto C:  80 unidades vendidas - R$ 10.000,00\n"
             + "Total geral: R$ 45.000,00";
    }

    private String gerarConteudoEstoque() {
        return "Item X: 500 unidades disponiveis\n"
             + "Item Y: 120 unidades disponiveis\n"
             + "Item Z:  80 unidades disponiveis (estoque critico)";
    }

    private String gerarConteudoClientes() {
        return "Cliente 001: Joao Silva       - ativo\n"
             + "Cliente 002: Maria Santos     - ativo\n"
             + "Cliente 003: Pedro Oliveira   - inativo\n"
             + "Total: 3 clientes cadastrados";
    }
}
