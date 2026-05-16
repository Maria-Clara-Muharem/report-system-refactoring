package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.HtmlRelatorioGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HtmlRelatorioGeneratorTest {

    private HtmlRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new HtmlRelatorioGenerator();
        relatorio = new Relatorio(
                "Relatorio de Clientes",
                "Cliente 001: Joao Silva",
                TipoRelatorio.CLIENTES,
                LocalDateTime.now()
        );
    }

    @Test
    void deveIniciarComDoctype() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.startsWith("<!DOCTYPE html>"));
    }

    @Test
    void deveConterTagsHtmlDeAberturaEFechamento() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<html>"));
        assertTrue(resultado.contains("</html>"));
    }

    @Test
    void deveConterSecaoHead() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<head>"));
        assertTrue(resultado.contains("</head>"));
    }

    @Test
    void deveConterTagTitleComTituloDoRelatorio() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<title>"));
        assertTrue(resultado.contains("</title>"));
        assertTrue(resultado.contains("Relatorio de Clientes"));
    }

    @Test
    void deveConterSecaoBody() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<body>"));
        assertTrue(resultado.contains("</body>"));
    }

    @Test
    void deveConterTituloComoH1() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<h1>"));
        assertTrue(resultado.contains("</h1>"));
        assertTrue(resultado.contains("Relatorio de Clientes"));
    }

    @Test
    void deveConterTipoDoRelatorio() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("CLIENTES"));
    }

    @Test
    void deveConterConteudoEmTagPre() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<pre>"));
        assertTrue(resultado.contains("</pre>"));
        assertTrue(resultado.contains("Cliente 001"));
    }

    @Test
    void deveConterDataDeGeracao() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<strong>Data:</strong>"));
    }

    @Test
    void deveConterSeparadorHr() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<hr/>"));
    }

    @Test
    void deveEscaparAmpersandNoConteudo() {
        relatorio.setConteudo("A & B");
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("&amp;"), "Ampersand deve ser escapado como &amp;");
    }

    @Test
    void deveEscaparChaveretesNoConteudo() {
        relatorio.setConteudo("<b>negrito</b>");
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("&lt;"), "< deve ser escapado como &lt;");
        assertTrue(resultado.contains("&gt;"), "> deve ser escapado como &gt;");
    }

    @Test
    void saideDeveTerConteudoNaoVazio() {
        String resultado = generator.gerar(relatorio);
        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
    }

    @Test
    void deveGerarHtmlParaTodosOsTiposDeRelatorio() {
        for (TipoRelatorio tipo : TipoRelatorio.values()) {
            Relatorio rel = new Relatorio("Titulo", "Conteudo", tipo, LocalDateTime.now());
            String resultado = generator.gerar(rel);
            assertNotNull(resultado, "Resultado nulo para tipo: " + tipo);
            assertTrue(resultado.contains("<html>"), "HTML invalido para tipo: " + tipo);
            assertTrue(resultado.contains(tipo.name()), "Tipo ausente no HTML: " + tipo);
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
