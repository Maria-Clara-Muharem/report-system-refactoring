package br.unicamp.padroescriacionais.legacy.creator;

import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.XmlRelatorioGenerator;

public class XmlRelatorioCreator extends RelatorioCreator {
    @Override
    public RelatorioGenerator createGenerator() {
        return new XmlRelatorioGenerator();
    }
}