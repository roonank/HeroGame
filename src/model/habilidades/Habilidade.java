package model.habilidades;
import model.personagens.Personagem;

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

    public Habilidade(String nome, String descricao, int custoMana, int danoBase, double multiplicador, TipoEfeito tipoEfeito,
                      TipoHabilidade tipo, int duracao, double chanceAcerto, double chanceCritico) {
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
    }

    public Habilidade(String nome, TipoHabilidade tipo, int custoMana, int danoBase, double multiplicador, TipoEfeito tipoEfeito) {
        this.nome = nome;
        this.tipo = tipo;
        this.custoMana = custoMana;
        this.danoBase = danoBase;
        this.multiplicador = multiplicador;
        this.tipoEfeito = tipoEfeito;
        this.chanceAcerto = 1.0;
        this.chanceCritico = 0.0;
    }

    public boolean acertou() {
        return Math.random() < chanceAcerto;
    }

    // Getters
    public int getDanoBase() { return danoBase; }
    public double getMultiplicador() { return multiplicador; }
    public double getChanceAcerto() { return chanceAcerto; }
    public double getChanceCritico() { return chanceCritico; }
    public String getNome() { return nome; }
    public int getCustoMana() { return custoMana; }
    public TipoHabilidade getTipo() {
        return tipo;
    }
}
