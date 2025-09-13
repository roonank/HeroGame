package model.personagens.inimigos.deuses.gregos;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.MagoInimigo;
import static util.Cores.*;
import java.util.Arrays;

public class Poseidon extends MagoInimigo {

    public Poseidon() {
        super("Poseidon", 100, 12, 8, 90, // Mais vida e força que Atena, menos mana
                Arrays.asList(
                        new Habilidade("Tridente dos Mares", TipoHabilidade.MAGICO,
                                30, 4, 0.9, false), // 12 * 2.5
                        new Habilidade("Tsunami Devastador", TipoHabilidade.MAGICO,
                                36, 5, 0.85, false), // 12 * 3
                        new Habilidade("Fúria dos Oceanos", TipoHabilidade.MAGICO,
                                42, 6, 0.8, false) // 12 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Poseidon pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Poseidon tem 20% de chance de crítico mágico com stun
            int dano = habilidade.calcularDano(getForca());

            if (Math.random() < 0.2) {
                dano = (int) (dano * 1.7);
                System.out.println(AMARELO + "\nCRÍTICO MARINHO! O inimigo foi atordoado pelas ondas!" + RESET);
                // Aqui seria implementado o efeito de stun se o sistema permitisse
            }

            setPontosMagia(getPontosMagia() - habilidade.getCustoMana());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nPoseidon invoca " + habilidade.getNome() +
                    " com poder dos mares e causa " + dano + " de dano aquático!" + RESET);
        } else {
            System.out.println(AMARELO + "\nPoseidon não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do mago com versão marinha
    @Override
    public void explosaoArcana(model.interfaces.ICombatente alvo) {
        if (getPontosMagia() >= 15) {
            setPontosMagia(getPontosMagia() - 15);
            int dano = (int) (getForca() * 2.8 + getPontosMagia() * 0.4);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nPoseidon libera uma Explosão Arcana Marinha causando " +
                    dano + " de dano devastador das profundezas!" + RESET);
        } else {
            System.out.println(AMARELO + "\nPoseidon não tem magia suficiente para esta habilidade!" + RESET);
        }
    }

    // Habilidade única de Poseidon - Fúria dos Mares
    public void furiaDosMares(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nPoseidon desencadeia a Fúria dos Mares!" + RESET);

        // Ataque em área que causa dano múltiplo baseado na força e mana
        for (int i = 0; i < 2; i++) {
            int danoBase = getForca() * 2;
            int danoMarinho = (int) (getPontosMagia() * 0.4);
            int danoTotal = danoBase + danoMarinho;

            alvo.receberDano(danoTotal);
            System.out.println(AMARELO + "\nOnda " + (i + 1) + ": " + danoTotal +
                    " de dano aquático! (Base: " + danoBase + ", Marinho: " + danoMarinho + ")" + RESET);
        }

        // Reduz a defesa do alvo após o ataque
        System.out.println(AMARELO + "\nA fúria dos mares enfraquece a defesa do inimigo!" + RESET);
    }

    // Metodo especial para Maré Alta
    public void mareAlta() {
        int curaMarinha = (int) (getVidaMaxima() * 0.25);
        int manaDasProfundezas = (int) (getPontosMagia() * 0.3);

        System.out.println(AMARELO + "\nPoseidon invoca a Maré Alta, recuperando " +
                curaMarinha + " de vida e " + manaDasProfundezas + " de magia das profundezas!" + RESET);

        curar(curaMarinha);
        setPontosMagia(getPontosMagia() + manaDasProfundezas);
    }

}