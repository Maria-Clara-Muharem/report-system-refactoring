package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.XmlRelatorioGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class XmlRelatorioGeneratorTest {

    private XmlRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new XmlRelatorioGenerator();
        relatorio = new Relatorio(
                "Relatorio de Vendas",
                "Produto A: 100 unidades",
                TipoRelatorio.VENDAS,
                LocalDateTime.now()
        );
    }

    @Test
    void deveConterDeclaracaoXml() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
    }

    @Test
    void deveConterElementoRaizRelatorio() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<relatorio>"));
        assertTrue(resultado.contains("</relatorio>"));
    }

    @Test
    void deveConterElementoTituloComConteudo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<titulo>"));
        assertTrue(resultado.contains("</titulo>"));
        assertTrue(resultado.contains("Relatorio de Vendas"));
    }

    @Test
    void deveConterElementoTipo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<tipo>"));
        assertTrue(resultado.contains("</tipo>"));
        assertTrue(resultado.contains("VENDAS"));
    }

    @Test
    void deveConterElementoDataGeracao() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<dataGeracao>"));
        assertTrue(resultado.contains("</dataGeracao>"));
    }

    @Test
    void deveConterElementoConteudoComDados() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<conteudo>"));
        assertTrue(resultado.contains("</conteudo>"));
        assertTrue(resultado.contains("Produto A"));
    }

    @Test
    void deveEscaparAmpersandNoConteudo() {
        relatorio.setConteudo("A & B");
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("&amp;"), "Ampersand deve ser escapado como &amp;");
        assertFalse(resultado.replaceAll("<[^>]+>", "").contains(" & "),
                "Ampersand literal nao deve aparecer fora de tags");
    }

    @Test
    void deveEscaparChaveretesNoConteudo() {
        relatorio.setConteudo("<script>alert(1)</script>");
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("&lt;"), "< deve ser escapado como &lt;");
        assertTrue(resultado.contains("&gt;"), "> deve ser escapado como &gt;");
    }

    @Test
    void deveEscaparAspasNoTitulo() {
        relatorio.setTitulo("Titulo \"Especial\"");
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("&quot;"), "Aspas devem ser escapadas como &quot;");
    }

    @Test
    void deveEscaparApostrofeNoConteudo() {
        relatorio.setConteudo("O'Brien");
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("&apos;"), "Apostrofe deve ser escapada como &apos;");
    }

    @Test
    void saideDeveTerConteudoNaoVazio() {
        String resultado = generator.gerar(relatorio);
        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
    }

    @Test
    void deveGerarXmlParaTodosOsTiposDeRelatorio() {
        for (TipoRelatorio tipo : TipoRelatorio.values()) {
            Relatorio rel = new Relatorio("Titulo", "Conteudo", tipo, LocalDateTime.now());
            String resultado = generator.gerar(rel);
            assertNotNull(resultado, "Resultado nulo para tipo: " + tipo);
            assertTrue(resultado.contains("<relatorio>"), "XML invalido para tipo: " + tipo);
            assertTrue(resultado.contains(tipo.name()), "Tipo ausente no XML: " + tipo);
        }
    }

    @Test
    void deveTratarConteudoNuloSemLancarExcecao() {
        relatorio.setConteudo(null);
        assertDoesNotThrow(() -> generator.gerar(relatorio));
    }

    @Test
    void deveTratarTituloNuloSemLancarExcecao() {
        relatorio.setTitulo(null);
        assertDoesNotThrow(() -> generator.gerar(relatorio));
    }
}
