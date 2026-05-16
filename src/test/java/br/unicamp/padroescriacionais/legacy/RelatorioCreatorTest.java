package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.creator.*;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioCreatorTest {

    private Relatorio criarRelatorioTeste() {
        return new Relatorio("Titulo Teste", "Conteudo Teste", TipoRelatorio.VENDAS, LocalDateTime.now());
    }

    @Test
    void pdfCreatorDeveCriarPdfGenerator() {
        RelatorioCreator creator = new PdfRelatorioCreator();
        RelatorioGenerator generator = creator.createGenerator();
        assertInstanceOf(PdfRelatorioGenerator.class, generator);
    }

    @Test
    void csvCreatorDeveCriarCsvGenerator() {
        RelatorioCreator creator = new CsvRelatorioCreator();
        RelatorioGenerator generator = creator.createGenerator();
        assertInstanceOf(CsvRelatorioGenerator.class, generator);
    }

    @Test
    void jsonCreatorDeveCriarJsonGenerator() {
        RelatorioCreator creator = new JsonRelatorioCreator();
        RelatorioGenerator generator = creator.createGenerator();
        assertInstanceOf(JsonRelatorioGenerator.class, generator);
    }

    @Test
    void xmlCreatorDeveCriarXmlGenerator() {
        RelatorioCreator creator = new XmlRelatorioCreator();
        RelatorioGenerator generator = creator.createGenerator();
        assertInstanceOf(XmlRelatorioGenerator.class, generator);
    }

    @Test
    void htmlCreatorDeveCriarHtmlGenerator() {
        RelatorioCreator creator = new HtmlRelatorioCreator();
        RelatorioGenerator generator = creator.createGenerator();
        assertInstanceOf(HtmlRelatorioGenerator.class, generator);
    }

    @Test
    void cadaChamadaAoCreatorDeveCriarNovaInstanciaDeGenerator() {
        RelatorioCreator creator = new PdfRelatorioCreator();
        RelatorioGenerator gen1 = creator.createGenerator();
        RelatorioGenerator gen2 = creator.createGenerator();
        assertNotSame(gen1, gen2);
    }

    @Test
    void cadaChamadaAoXmlCreatorDeveCriarNovaInstancia() {
        RelatorioCreator creator = new XmlRelatorioCreator();
        assertNotSame(creator.createGenerator(), creator.createGenerator());
    }

    @Test
    void cadaChamadaAoHtmlCreatorDeveCriarNovaInstancia() {
        RelatorioCreator creator = new HtmlRelatorioCreator();
        assertNotSame(creator.createGenerator(), creator.createGenerator());
    }

    @Test
    void todosOsGeneratorsCriadosPelosFactoriesDevemProduizirConteudoNaoVazio() {
        Relatorio relatorio = criarRelatorioTeste();

        RelatorioCreator[] creators = {
            new PdfRelatorioCreator(),
            new CsvRelatorioCreator(),
            new JsonRelatorioCreator(),
            new XmlRelatorioCreator(),
            new HtmlRelatorioCreator()
        };

        for (RelatorioCreator creator : creators) {
            String nomeCreator = creator.getClass().getSimpleName();
            RelatorioGenerator generator = creator.createGenerator();
            String resultado = generator.gerar(relatorio);
            assertNotNull(resultado, "Resultado nulo para: " + nomeCreator);
            assertFalse(resultado.isBlank(), "Resultado vazio para: " + nomeCreator);
        }
    }

    @Test
    void generatorCriadoPeloXmlFactoryDeveConterTituloDoRelatorio() {
        Relatorio relatorio = criarRelatorioTeste();
        RelatorioGenerator generator = new XmlRelatorioCreator().createGenerator();
        assertTrue(generator.gerar(relatorio).contains("Titulo Teste"));
    }

    @Test
    void generatorCriadoPeloHtmlFactoryDeveConterTituloDoRelatorio() {
        Relatorio relatorio = criarRelatorioTeste();
        RelatorioGenerator generator = new HtmlRelatorioCreator().createGenerator();
        assertTrue(generator.gerar(relatorio).contains("Titulo Teste"));
    }

    @Test
    void generatorCriadoPeloPdfFactoryDeveConterTituloDoRelatorio() {
        Relatorio relatorio = criarRelatorioTeste();
        RelatorioGenerator generator = new PdfRelatorioCreator().createGenerator();
        assertTrue(generator.gerar(relatorio).contains("Titulo Teste"));
    }
}
