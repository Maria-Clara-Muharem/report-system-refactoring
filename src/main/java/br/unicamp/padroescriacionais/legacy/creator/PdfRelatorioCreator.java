package br.unicamp.padroescriacionais.legacy.creator;

import br.unicamp.padroescriacionais.legacy.generator.PdfRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;

public class PdfRelatorioCreator extends RelatorioCreator{
    @Override
    public RelatorioGenerator createGenerator() {
        return new PdfRelatorioGenerator();
    }
}