package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.service.ConfiguracaoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoSingletonTest {

    private String nomeEmpresaOriginal;
    private String ambienteOriginal;
    private String diretorioOriginal;
    private boolean debugOriginal;

    @BeforeEach
    void salvarEstadoDoSingleton() {
        ConfiguracaoSistema instancia = ConfiguracaoSistema.getInstancia();
        nomeEmpresaOriginal = instancia.getNomeEmpresa();
        ambienteOriginal = instancia.getAmbiente();
        diretorioOriginal = instancia.getDiretorioExportacao();
        debugOriginal = instancia.isDebugAtivo();
    }

    @AfterEach
    void restaurarEstadoDoSingleton() {
        ConfiguracaoSistema instancia = ConfiguracaoSistema.getInstancia();
        instancia.setNomeEmpresa(nomeEmpresaOriginal);
        instancia.setAmbiente(ambienteOriginal);
        instancia.setDiretorioExportacao(diretorioOriginal);
        instancia.setDebugAtivo(debugOriginal);
    }

    @Test
    void getInstanciaDeveRetornarInstanciaNaoNula() {
        assertNotNull(ConfiguracaoSistema.getInstancia());
    }

    @Test
    void multiplasChamadasAoGetInstanciaDevemRetornarMesmaReferencia() {
        ConfiguracaoSistema instancia1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema instancia2 = ConfiguracaoSistema.getInstancia();
        assertSame(instancia1, instancia2,
                "getInstancia() deve retornar sempre o mesmo objeto (Singleton)");
    }

    @Test
    void singletonDeveTePropriedadesNaoNulas() {
        ConfiguracaoSistema instancia = ConfiguracaoSistema.getInstancia();
        assertAll(
                () -> assertNotNull(instancia.getNomeEmpresa()),
                () -> assertNotNull(instancia.getAmbiente()),
                () -> assertNotNull(instancia.getDiretorioExportacao())
        );
    }

    @Test
    void singletonDeveTeNomeEmpresaNaoVazio() {
        assertFalse(ConfiguracaoSistema.getInstancia().getNomeEmpresa().isBlank());
    }

    @Test
    void singletonDeveTeAmbienteNaoVazio() {
        assertFalse(ConfiguracaoSistema.getInstancia().getAmbiente().isBlank());
    }

    @Test
    void singletonDeveTesDiretorioDeExportacaoNaoVazio() {
        assertFalse(ConfiguracaoSistema.getInstancia().getDiretorioExportacao().isBlank());
    }

    @Test
    void alteracaoViaUmaReferenciaDeveSerVisivelEmOutraReferencia() {
        ConfiguracaoSistema ref1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema ref2 = ConfiguracaoSistema.getInstancia();

        ref1.setAmbiente("PRODUCAO");

        assertEquals("PRODUCAO", ref2.getAmbiente(),
                "Mudanca feita via ref1 deve ser visivel em ref2 (mesma instancia Singleton)");
    }

    @Test
    void alteracaoDeDebugViaUmaReferenciaDeveSerVisivelEmOutra() {
        ConfiguracaoSistema ref1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema ref2 = ConfiguracaoSistema.getInstancia();

        ref1.setDebugAtivo(true);

        assertTrue(ref2.isDebugAtivo());
    }

    @Test
    void alteracaoDeDiretorioViaUmaReferenciaDeveSerVisivelEmOutra() {
        ConfiguracaoSistema ref1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema ref2 = ConfiguracaoSistema.getInstancia();

        ref1.setDiretorioExportacao("/novo/diretorio/exportacao");

        assertEquals("/novo/diretorio/exportacao", ref2.getDiretorioExportacao());
    }

    @Test
    void configuracaoServiceDeveExposeOMesmoSingleton() {
        ConfiguracaoSistema singleton = ConfiguracaoSistema.getInstancia();
        ConfiguracaoService service = new ConfiguracaoService();

        assertSame(singleton, service.getConfiguracao(),
                "ConfiguracaoService deve retornar a mesma instancia do Singleton");
    }

    @Test
    void mudancaNoSingletonDeveSerRefletidaNoConfiguracaoService() {
        ConfiguracaoSistema singleton = ConfiguracaoSistema.getInstancia();
        singleton.setNomeEmpresa("Empresa Centralizada");

        ConfiguracaoService service = new ConfiguracaoService();

        assertEquals("Empresa Centralizada", service.getConfiguracao().getNomeEmpresa(),
                "Mudanca no Singleton deve ser visivel via ConfiguracaoService");
    }

    @Test
    void doisServicosConfiguracaoDeveCompartilharMesmaInstancia() {
        ConfiguracaoService service1 = new ConfiguracaoService();
        ConfiguracaoService service2 = new ConfiguracaoService();

        assertSame(service1.getConfiguracao(), service2.getConfiguracao(),
                "Dois ConfiguracaoService devem apontar para o mesmo Singleton");
    }
}
