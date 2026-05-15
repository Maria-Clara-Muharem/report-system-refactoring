package br.unicamp.padroescriacionais.legacy.service;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.generator.CsvRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.JsonRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.PdfRelatorioGenerator;

public class ExportacaoService {

    private ConfiguracaoSistema configuracao = new ConfiguracaoSistema(
            "Empresa XPTO Ltda.",
            "PROD",
            "/var/exports/relatorios",
            false
    );

    public void exportar(Relatorio relatorio, FormatoRelatorio formato) {
        String conteudoFormatado;

        switch (formato) {
            case PDF:
                PdfRelatorioGenerator pdfGenerator = new PdfRelatorioGenerator();
                conteudoFormatado = pdfGenerator.gerar(relatorio);
                break;
            case CSV:
                CsvRelatorioGenerator csvGenerator = new CsvRelatorioGenerator();
                conteudoFormatado = csvGenerator.gerar(relatorio);
                break;
            case JSON:
                JsonRelatorioGenerator jsonGenerator = new JsonRelatorioGenerator();
                conteudoFormatado = jsonGenerator.gerar(relatorio);
                break;
            default:
                throw new IllegalArgumentException("Formato nao suportado para exportacao: " + formato);
        }

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
