package model.habilidades;

public class Habilidade {
    private String nome;
    private String descricao;
    private int custoPontos;
    private int danoBase;
    private double multiplicador;
    private TipoEfeito tipoEfeito;
    private Tipo tipo;
    private int duracao;
    private double chanceAcerto;
    private double chanceCritico;
    private boolean ultimate;

    public Habilidade(String nome, String descricao, int custoPontos, int danoBase, double multiplicador, TipoEfeito tipoEfeito,
                      Tipo tipo, int duracao, double chanceAcerto, double chanceCritico, boolean ultimate) {
        this.nome = nome;
        this.descricao = descricao;
        this.custoPontos = custoPontos;
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
    public int getCustoPontos() { return custoPontos; }
    public TipoEfeito getTipoEfeito() { return tipoEfeito; }
    public int getDuracao() { return duracao; }
    public boolean isUltimate() { return ultimate; }
}
