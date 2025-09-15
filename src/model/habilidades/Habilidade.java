package model.habilidades;

public class Habilidade {
    private String nome;
    private String descricao;
    private int custoMana;
    private int danoBase;
    private double multiplicador;
    private TipoEfeito tipoEfeito;
    private TipoHabilidade tipo;
    private int duracao;
    private double chanceAcerto;
    private double chanceCritico;
    private boolean ultimate;

    public Habilidade(String nome, String descricao, int custoMana, int danoBase, double multiplicador, TipoEfeito tipoEfeito,
                      TipoHabilidade tipo, int duracao, double chanceAcerto, double chanceCritico, boolean ultimate) {
        this.nome = nome;
        this.descricao = descricao;
        this.custoMana = custoMana;
        this.danoBase = danoBase;
        this.multiplicador = multiplicador;
        this.tipoEfeito = tipoEfeito;
        this.tipo = tipo;
        this.duracao = duracao;
        this.chanceAcerto = chanceAcerto;
        this.chanceCritico = chanceCritico;
        this.ultimate = ultimate;
    }

    public boolean acertou() {
        return Math.random() < chanceAcerto;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getCustoMana() { return custoMana; }
    public TipoHabilidade getTipo() {
        return tipo;
    }
    public TipoEfeito getTipoEfeito() { return tipoEfeito; }
    public int getDuracao() { return duracao; }
    public boolean isUltimate() { return ultimate; }
}
