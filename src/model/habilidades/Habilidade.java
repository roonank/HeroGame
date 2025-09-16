package model.habilidades;
import model.habilidades.efeitos.TipoEfeito;

public class Habilidade {
    private final String nome;
    private final int custoMana;
    private final int danoBase;
    private final double multiplicador;
    private final TipoEfeito tipoEfeito;
    private final TipoHabilidade tipo;
    private final double chanceAcerto;
    private final double chanceCritico;
    private int duracaoEfeito = 0;
    private double chanceEfeito = 0.0;
    private double poderEfeito = 0.0;

    public Habilidade(String nome, int custoMana, int danoBase, double multiplicador, TipoEfeito tipoEfeito,
                      TipoHabilidade tipo, double chanceAcerto, double chanceCritico) {
        this.nome = nome;
        this.custoMana = custoMana;
        this.danoBase = danoBase;
        this.multiplicador = multiplicador;
        this.tipoEfeito = tipoEfeito;
        this.tipo = tipo;
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


    public Habilidade comEfeito(int duracao, double chance, double poder) {
        this.duracaoEfeito = Math.max(0, duracao);
        this.chanceEfeito = Math.max(0.0, Math.min(1.0, chance));
        this.poderEfeito = Math.max(0.0, poder);
        return this;
    }

    public boolean acertou() {
        return Math.random() < chanceAcerto;
    }

    public int getDuracaoEfeito() { return duracaoEfeito; }
    public double getChanceEfeito() { return chanceEfeito; }
    public double getPoderEfeito() { return poderEfeito; }
    public TipoEfeito getTipoEfeito() { return tipoEfeito; }
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
