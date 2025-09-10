package model.habilidades;

import model.habilidades.TipoEfeito;

public class Habilidade {
    private String nome;
    private String descricao;
    private int custoMana;        // custo de energia/mana/estamina
    private int danoBase;         // dano inicial
    private double multiplicador; // multiplicador do ataque do personagem
    private TipoEfeito tipoEfeito; // tipo do efeito (dano, cura, buff, debuff)
    private int duracao;          // quantos turnos dura (para buffs/debuffs)
    private double chanceAcerto;  // chance de acertar (0.0 - 1.0)
    private double chanceCritico; // chance de crítico
    private boolean ultimate;     // se é uma habilidade única/ultimate

    public Habilidade(String nome, String descricao, int custoMana, int danoBase,
                      double multiplicador, TipoEfeito tipoEfeito, int duracao,
                      double chanceAcerto, double chanceCritico, boolean ultimate) {
        this.nome = nome;
        this.descricao = descricao;
        this.custoMana = custoMana;
        this.danoBase = danoBase;
        this.multiplicador = multiplicador;
        this.tipoEfeito = tipoEfeito;
        this.duracao = duracao;
        this.chanceAcerto = chanceAcerto;
        this.chanceCritico = chanceCritico;
        this.ultimate = ultimate;
    }

    // --- Métodos de uso ---
    public int calcularDano(int ataquePersonagem) {
        int dano = (int) (danoBase + ataquePersonagem * multiplicador);

        // Crítico
        if (Math.random() < chanceCritico) {
            dano *= 2;
        }
        return dano;
    }

    public boolean acertou() {
        return Math.random() < chanceAcerto;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getCustoMana() { return custoMana; }
    public TipoEfeito getTipoEfeito() { return tipoEfeito; }
    public int getDuracao() { return duracao; }
    public boolean isUltimate() { return ultimate; }
}
