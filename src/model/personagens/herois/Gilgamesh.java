package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.Mago;

import java.util.Arrays;

import static util.Cores.*;

public class Gilgamesh extends Mago {

    public Gilgamesh() {
        super("Gilgamesh", 100, 14, 10, 90,
                Arrays.asList(
                        new Habilidade("Cetro de Ur", TipoHabilidade.MAGICO,
                                35, 5, 0.9, false), // 14 * 2.5
                        new Habilidade("Projéteis de Argila", TipoHabilidade.MAGICO,
                                42, 6, 0.85, false), // 14 * 3
                        new Habilidade("Fúria da Mesopotâmia", TipoHabilidade.MAGICO,
                                49, 7, 0.8, false) // 14 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Gilgamesh pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Gilgamesh tem 25% de chance de crítico real
            int dano = habilidade.calcularDano(getForca());

            if (Math.random() < 0.25) {
                dano = (int) (dano * 1.6);
                System.out.println(AMARELO + "\nPODER REAL DE GILGAMESH!" + RESET);
            }

            setPontosMagia(getPontosMagia() - habilidade.getCustoMana());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nGilgamesh conjura " + habilidade.getNome() +
                    " com autoridade real e causa " + dano + " de dano majestoso!" + RESET);
        } else {
            System.out.println(AMARELO + "\nGilgamesh não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do mago com versão real
    @Override
    public void explosaoArcana(model.interfaces.ICombatente alvo) {
        if (getPontosMagia() >= 20) {
            setPontosMagia(getPontosMagia() - 20);
            int dano = (int) (getForca() * 3.0 + getPontosMagia() * 0.4);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nGilgamesh libera uma Explosão da realeza causando " +
                    dano + " de dano imperial!" + RESET);
        } else {
            System.out.println(AMARELO + "\nGilgamesh não tem magia suficiente para esta habilidade!" + RESET);
        }
    }

    // Habilidade única de Gilgamesh - Exército de Argila
    public void exercitoDeArgila(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nGilgamesh convoca seu Exército de Argila!" + RESET);

        // Múltiplos ataques de criaturas de argila
        for (int i = 0; i < 4; i++) {
            int danoBase = getForca() + (int) (Math.random() * 8);
            int danoMagico = (int) (getPontosMagia() * 0.25);
            int danoTotal = danoBase + danoMagico;

            alvo.receberDano(danoTotal);
            System.out.println(AMARELO + "\nGolem de Argila " + (i + 1) + ": " +
                    danoTotal + " de dano (Base: " + danoBase +
                    ", Mágico: " + danoMagico + ")" + RESET);

            // 15% de chance de ataque extra por golem
            if (Math.random() < 0.15) {
                int danoExtra = (int) (danoTotal * 0.3);
                alvo.receberDano(danoExtra);
                System.out.println(AMARELO + "\n  → Ataque Extra do Golem: " + danoExtra + " de dano!" + RESET);
            }
        }

        // Consome 25% da mana atual (mais que Ragnar)
        setPontosMagia((int) (getPontosMagia() * 0.75));
    }

    // Habilidade defensiva única - Muralha da Babilônia
    public void muralhaDaBabilonia() {
        int defesaExtra = getDefesa() * 2 + (int) (getPontosMagia() * 0.3);
        System.out.println(AMARELO + "\nGilgamesh ergue a Muralha da Babilônia, aumentando sua defesa em " +
                defesaExtra + " pontos!" + RESET);

        // Implementação temporária - precisaria de um mecanismo para defesa temporária
        setDefesa(getDefesa() + defesaExtra);
    }
}