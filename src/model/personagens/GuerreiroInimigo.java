package model.personagens;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;

import java.util.List;

public class GuerreiroInimigo extends Inimigo {

    // Construtor com habilidades
    public GuerreiroInimigo(String nome, int vida, int forca, int defesa, List<Habilidade> habilidades) {
        super(nome, vida, forca, defesa);
        // Apenas adiciona as habilidades passadas como parâmetro
        if (habilidades != null) {
            for (Habilidade habilidade : habilidades) {
                adicionarHabilidade(habilidade);
            }
        }
    }

    // Construtor sem habilidades
    public GuerreiroInimigo(String nome, int vida, int forca, int defesa) {
        super(nome, vida, forca, defesa);
        // Não adiciona nenhuma habilidade - deixa para as classes filhas
    }

    // Construtor padrão para compatibilidade (sem habilidades)
    public GuerreiroInimigo() {
        super("Guerreiro", 100, 12, 6);
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            int dano = habilidade.calcularDano(getForca());
            alvo.receberDano(dano);
            System.out.println(getNome() + " usa " + habilidade.getNome() +
                    " e causa " + dano + " de dano!");
        } else {
            System.out.println(getNome() + " não pode usar esta habilidade!");
        }
    }

    //Habilidade única do guerreiro
    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
        int danoExtra = getForca() + (int) (getForca() * 0.5);
        alvo.receberDano(danoExtra);
        System.out.println(getNome() + " executa uma Investida Furiosa causando " +
                danoExtra + " de dano!");
    }
}