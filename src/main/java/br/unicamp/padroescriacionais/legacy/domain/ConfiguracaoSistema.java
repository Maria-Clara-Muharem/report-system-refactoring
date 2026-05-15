package br.unicamp.padroescriacionais.legacy.domain;

public class ConfiguracaoSistema {

    private String nomeEmpresa;
    private String ambiente;
    private String diretorioExportacao;
    private boolean debugAtivo;
    private static ConfiguracaoSistema instancia = new ConfiguracaoSistema();

    private ConfiguracaoSistema() {
        this.nomeEmpresa = "Empresa XPTO Ltda.";
        this.ambiente = "DEV";
        this.diretorioExportacao = "/tmp/relatorios";
        this.debugAtivo = false;
    }

    public ConfiguracaoSistema(String nomeEmpresa, String ambiente,
                               String diretorioExportacao, boolean debugAtivo) {
        this.nomeEmpresa = nomeEmpresa;
        this.ambiente = ambiente;
        this.diretorioExportacao = diretorioExportacao;
        this.debugAtivo = debugAtivo;
    }

    public static ConfiguracaoSistema getInstancia() {
        return instancia;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getDiretorioExportacao() {
        return diretorioExportacao;
    }

    public void setDiretorioExportacao(String diretorioExportacao) {
        this.diretorioExportacao = diretorioExportacao;
    }

    public boolean isDebugAtivo() {
        return debugAtivo;
    }

    public void setDebugAtivo(boolean debugAtivo) {
        this.debugAtivo = debugAtivo;
    }
}
