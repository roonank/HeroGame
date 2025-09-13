package model.habilidades;

public class Habilidade {
    private String nome;
    private TipoHabilidade tipo;
    private int danoBase;
    private int custoMana;
    private double chanceAcerto;
    private boolean critica;

    public Habilidade(String nome, TipoHabilidade tipo, int danoBase, int custoMana,
                      double chanceAcerto, boolean critica) {
        this.nome = nome;
        this.tipo = tipo;
        this.danoBase = danoBase;
        this.custoMana = custoMana;
        this.chanceAcerto = chanceAcerto;
        this.critica = critica;
    }

    public int calcularDano(int atributoPersonagem) {
        if (Math.random() > chanceAcerto) return 0; // Errou

        int dano = danoBase + (atributoPersonagem / 2);

        // Sistema de crítico (20% chance base)
        if (critica && Math.random() < 0.2) {
            dano = (int) (dano * 1.5);
        }

        return dano;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public TipoHabilidade getTipo() {
        return tipo;
    }

    public int getCustoMana() {
        return custoMana;
    }

    public boolean isCritica() {
        return critica;
    }
}