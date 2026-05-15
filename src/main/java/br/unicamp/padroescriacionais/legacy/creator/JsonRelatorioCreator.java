package br.unicamp.padroescriacionais.legacy.creator;

import br.unicamp.padroescriacionais.legacy.generator.JsonRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;

public class JsonRelatorioCreator extends RelatorioCreator {
    @Override
    public RelatorioGenerator createGenerator() {
        return new JsonRelatorioGenerator();
    }
}