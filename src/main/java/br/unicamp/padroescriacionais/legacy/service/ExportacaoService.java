package br.unicamp.padroescriacionais.legacy.service;

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
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;

public class ExportacaoService {

    private final Map<FormatoRelatorio, RelatorioCreator> creators = new HashMap<>();
    private ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstancia();
    public ExportacaoService() {
        creators.put(FormatoRelatorio.PDF, new PdfRelatorioCreator());
        creators.put(FormatoRelatorio.CSV, new CsvRelatorioCreator());
        creators.put(FormatoRelatorio.JSON, new JsonRelatorioCreator());
        creators.put(FormatoRelatorio.XML, new XmlRelatorioCreator());
        creators.put(FormatoRelatorio.HTML, new HtmlRelatorioCreator());
    }

    public void exportar(Relatorio relatorio, FormatoRelatorio formato) {
        RelatorioCreator creator = creators.get(formato);
        if (creator == null) {
            throw new IllegalArgumentException("Formato nao suportado para exportacao: " + formato);
        }
        
        RelatorioGenerator generator = creator.createGenerator();
        String conteudoFormatado = generator.gerar(relatorio);

        String nomeArquivo = relatorio.getTitulo()
                .replace(" ", "_")
                .toLowerCase()
                + "." + formato.name().toLowerCase();

        String caminhoCompleto = configuracao.getDiretorioExportacao() + "/" + nomeArquivo;

        System.out.println("[EXPORTACAO] Empresa  : " + configuracao.getNomeEmpresa());
        System.out.println("[EXPORTACAO] Ambiente : " + configuracao.getAmbiente());
        System.out.println("[EXPORTACAO] Arquivo  : " + caminhoCompleto);
        System.out.println("[EXPORTACAO] Conteudo :");
        System.out.println(conteudoFormatado);
    }
}