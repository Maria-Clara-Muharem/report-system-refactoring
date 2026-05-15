package br.unicamp.padroescriacionais.legacy.creator;

import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;

public abstract class RelatorioCreator {
    public abstract RelatorioGenerator createGenerator();
}