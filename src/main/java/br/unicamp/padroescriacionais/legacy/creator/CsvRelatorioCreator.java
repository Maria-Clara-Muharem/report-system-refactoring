package br.unicamp.padroescriacionais.legacy.creator;

import br.unicamp.padroescriacionais.legacy.generator.CsvRelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;

public class CsvRelatorioCreator extends RelatorioCreator {
    @Override
    public RelatorioGenerator createGenerator() {
        return new CsvRelatorioGenerator();
    }
}