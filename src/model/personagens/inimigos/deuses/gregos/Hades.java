package model.personagens.inimigos.deuses.gregos;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.interfaces.ICombatente;
import model.personagens.GuerreiroInimigo;
import model.personagens.Personagem;

import static util.Cores.*;
import java.util.Arrays;

public class Hades extends GuerreiroInimigo {

    public Hades() {
        super("Hades", 100, 16, 12,
                Arrays.asList(
                        new Habilidade("Lâmina das Sombras", TipoHabilidade.FISICO,
                                32, 2, 0.9, TipoEfeito.DANO), // 16 * 2
                        new Habilidade("Toque da Morte", TipoHabilidade.FISICO,
                                40, 3, 0.85, TipoEfeito.DANO), // 16 * 2.5
                        new Habilidade("Punho do Submundo", TipoHabilidade.FISICO,
                                48, 4, 0.75, TipoEfeito.DANO) // 16 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Hades pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            Personagem defensor = (Personagem) alvo;
            //tem 20% de chance de dano crítico
            int dano = CalculoDano.calcularDano(this, defensor, habilidade);
            if (Math.random() < 0.2) {
                dano = (int) (dano * 1.5);
                System.out.println(AMARELO + "\nDANO CRÍTICO!" + RESET);
            }

            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nHades usa " + habilidade.getNome() +
                    " com poder divino e causa " + dano + " de dano!" + RESET);
        } else {
            System.out.println(AMARELO + "\nHades não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do guerreiro com versão sombria
    @Override
    public void investidaFuriosa(ICombatente alvo) {
        int danoExtra = (int) (getForca() * 2.2); // Mais poderoso que um guerreiro comum
        alvo.receberDano(danoExtra);
        System.out.println(AMARELO + "\nHades executa uma Investida das Trevas causando " +
                danoExtra + " de dano e enfraquecendo o alvo!" + RESET);

        // Lógica para aplicar enfraquecimento seria implementada aqui
    }

    // Habilidade única de Hades - Invoca espíritos do submundo
    public void invocacaoEspiritual(ICombatente alvo) {
        System.out.println(AMARELO + "\nHades invoca espíritos do Submundo!" + RESET);

        // Ataques múltiplos com chance de ignorar defesa
        for (int i = 0; i < 2; i++) {
            int danoBase = getForca() + (int) (Math.random() * getForca() / 2);

            // 40% de chance de ignorar defesa
            if (Math.random() < 0.4) {
                danoBase = (int) (danoBase * 1.2); // Dano aumentado quando ignora defesa
                System.out.println(AMARELO + "\nO espírito atravessa as defesas!" + RESET);
            }

            alvo.receberDano(danoBase);
            System.out.println(AMARELO + "\nEspírito " + (i + 1) + ": " + danoBase + " de dano!" + RESET);
        }
    }

    // Habilidade única para manipular as sombras
    public void mantoDasSombras(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nHades se envolve em um manto de sombras!" + RESET);

        // Múltiplos ataques
        for (int i = 0; i < 3; i++) {
            int dano = getForca() + (int) (Math.random() * getForca());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAtaque " + (i + 1) + ": " + dano + " de dano!" + RESET);
        }
    }

    // Metodo especial para recuperar vida
    public void olharAmedrontador() {
        int cura = getVidaMaxima() / 4;
        curar(cura);
        System.out.println(AMARELO + "\nHades usa sua herança divina para recuperar " +
                cura + " pontos de vida!" + RESET);
    }
}