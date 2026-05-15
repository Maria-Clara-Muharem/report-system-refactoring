package br.unicamp.padroescriacionais.legacy.creator;

import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.HtmlRelatorioGenerator;

public class HtmlRelatorioCreator extends RelatorioCreator {
    @Override
    public RelatorioGenerator createGenerator() {
        return new HtmlRelatorioGenerator();
    }
}