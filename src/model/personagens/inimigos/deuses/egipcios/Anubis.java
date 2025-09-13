package model.personagens.inimigos.deuses.egipcios;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import model.interfaces.ICombatente;
import model.personagens.GuerreiroInimigo;

import java.util.Arrays;

public class Anubis extends GuerreiroInimigo {

    public Anubis() {
        super("Anubis", 100, 16, 12,
                Arrays.asList(
                        new Habilidade("Lâmina das Sombras", TipoHabilidade.FISICO,
                                32, 2, 0.9, false), // 16 * 2
                        new Habilidade("Toque da Morte", TipoHabilidade.FISICO,
                                40, 3, 0.85, false), // 16 * 2.5
                        new Habilidade("Punho do Submundo", TipoHabilidade.FISICO,
                                48, 4, 0.75, false) // 16 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Anubis pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            //tem 20% de chance de dano crítico
            int dano = habilidade.calcularDano(getForca());
            if (Math.random() < 0.2) {
                dano = (int) (dano * 1.5);
                System.out.println("DANO CRÍTICO!");
            }

            alvo.receberDano(dano);
            System.out.println("Anubis usa " + habilidade.getNome() +
                    " com poder divino e causa " + dano + " de dano!");
        } else {
            System.out.println("Anubis não pode usar esta habilidade!");
        }
    }

    // Sobrescreve o metodo do guerreiro com versão sombria
    @Override
    public void investidaFuriosa(ICombatente alvo) {
        int danoExtra = (int) (getForca() * 2.2); // Mais poderoso que um guerreiro comum
        alvo.receberDano(danoExtra);
        System.out.println("Anubis executa uma Investida das Trevas causando " +
                danoExtra + " de dano e enfraquecendo o alvo!");

        // Lógica para aplicar enfraquecimento seria implementada aqui
    }

    // Habilidade única de Hades - Invoca espíritos do submundo
    public void invocacaoEspiritual(ICombatente alvo) {
        System.out.println("Anubis invoca espíritos do Submundo!");

        // Ataques múltiplos com chance de ignorar defesa
        for (int i = 0; i < 2; i++) {
            int danoBase = getForca() + (int) (Math.random() * getForca() / 2);

            // 40% de chance de ignorar defesa
            if (Math.random() < 0.4) {
                danoBase = (int) (danoBase * 1.2); // Dano aumentado quando ignora defesa
                System.out.println("O espírito atravessa as defesas!");
            }

            alvo.receberDano(danoBase);
            System.out.println("Espírito " + (i + 1) + ": " + danoBase + " de dano!");
        }
    }

    // Habilidade única para manipular as sombras
    public void mantoDasSombras(model.interfaces.ICombatente alvo) {
        System.out.println("Anubis se envolve em um manto de sombras!");

        // Múltiplos ataques
        for (int i = 0; i < 3; i++) {
            int dano = getForca() + (int) (Math.random() * getForca());
            alvo.receberDano(dano);
            System.out.println("Ataque " + (i + 1) + ": " + dano + " de dano!");
        }
    }

    // Metodo especial para recuperar vida
    public void olharAmedrontador() {
        int cura = getVidaMaxima() / 4;
        curar(cura);
        System.out.println("Anubis usa sua herança divina para recuperar " +
                cura + " pontos de vida!");
    }
}