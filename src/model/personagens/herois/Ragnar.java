package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.Mago;
import java.util.Arrays;
import static util.Cores.*;

public class Ragnar extends Mago {

    public Ragnar() {
        super("Ragnar", 100, 12, 8, 80, // Mais vida, força e defesa que um mago comum
                Arrays.asList(
                        new Habilidade("Grito do Viking", TipoHabilidade.MAGICO,
                                30, 4, 0.9, false), // 12 * 2.5
                        new Habilidade("Machado de Gelo", TipoHabilidade.MAGICO,
                                36, 5, 0.85, false), // 12 * 3
                        new Habilidade("Fúria Nórdica", TipoHabilidade.MAGICO,
                                42, 6, 0.8, false) // 12 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Ragnar pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Ragnar tem 20% de chance de crítico mágico
            int dano = habilidade.calcularDano(getForca());

            if (Math.random() < 0.2) {
                dano = (int) (dano * 1.5);
                System.out.println(AMARELO + "\nCRÍTICO MÁGICO!" + RESET);
            }

            setPontosMagia(getPontosMagia() - habilidade.getCustoMana());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nRagnar invoca " + habilidade.getNome() +
                    " com poder nórdico e causa " + dano + " de dano mágico!" + RESET);
        } else {
            System.out.println(AMARELO + "\nRagnar não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do mago com versão mais poderosa
    @Override
    public void explosaoArcana(model.interfaces.ICombatente alvo) {
        if (getPontosMagia() >= 15) {
            setPontosMagia(getPontosMagia() - 15);
            int dano = (int) (getForca() * 2.5 + getPontosMagia() * 0.5);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nRagnar libera uma Explosão Arcana Nórdica causando " +
                    dano + " de dano devastador!" + RESET);
        } else {
            System.out.println(AMARELO + "\nRagnar não tem magia suficiente para esta habilidade!" + RESET);
        }
    }

    // Habilidade única de Ragnar - Berserker Místico
    public void berserkerMistico(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nRagnar entra em estado de Berserker Místico!" + RESET);

        // Combina dano físico e mágico em múltiplos ataques
        for (int i = 0; i < 3; i++) {
            int danoFisico = getForca() + (int) (Math.random() * getForca());
            int danoMagico = (int) (getPontosMagia() * 0.3);
            int danoTotal = danoFisico + danoMagico;

            alvo.receberDano(danoTotal);
            System.out.println(AMARELO + "\nAtaque Berserker " + (i + 1) + ": " +
                    danoTotal + " de dano (Físico: " + danoFisico +
                    ", Mágico: " + danoMagico + ")" + RESET);
        }

        // Consome 20% da mana atual
        setPontosMagia((int) (getPontosMagia() * 0.8));
    }

    // Metodo especial para regeneração nórdica
    public void regeneracaoNordica() {
        int cura = getVidaMaxima() / 4;
        int manaRecuperada = (int) (getPontosMagia() * 0.4);

        curar(cura);
        setPontosMagia(getPontosMagia() + manaRecuperada);

        System.out.println(AMARELO + "\nRagnar invoca a regeneração nórdica, recuperando " +
                cura + " de vida e " + manaRecuperada + " de magia!" + RESET);
    }
}