package model.personagens.inimigos.deuses.egipcios;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.GuerreiroInimigo;

import java.util.Arrays;

public class Horus extends GuerreiroInimigo {

    public Horus() {
        super("Horus", 100, 20, 8,
                Arrays.asList(
                        new Habilidade("Lança da Discórdia", TipoHabilidade.FISICO,
                                40, 2, 0.85, false), // 20 * 2
                        new Habilidade("Golpe da Guerra", TipoHabilidade.FISICO,
                                50, 3, 0.8, false), // 20 * 2.5
                        new Habilidade("Fúria Belicosa", TipoHabilidade.FISICO,
                                60, 4, 0.7, false) // 20 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Horus pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Horus tem 25% de chance de dano crítico
            int dano = habilidade.calcularDano(getForca());
            if (Math.random() < 0.25) {
                dano = (int) (dano * 1.8); // Multiplicador maior
                System.out.println("FÚRIA GUERREIRA! DANO CRÍTICO!");
            }

            alvo.receberDano(dano);
            System.out.println("Horus usa " + habilidade.getNome() +
                    " com sede de batalha e causa " + dano + " de dano!");
        } else {
            System.out.println("Horus não pode usar esta habilidade!");
        }
    }

    // Sobrescreve o metodo do guerreiro com versão mais agressiva
    @Override
    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
        int danoExtra = (int) (getForca() * 2.5);
        alvo.receberDano(danoExtra);
        System.out.println("Horus executa uma Investida Sangrenta causando " +
                danoExtra + " de dano devastador!");

        // Horus ganha um bônus de força após a investida
        setForca(getForca() + 2);
        System.out.println("Horus fica mais furioso! Força aumentada!");
    }

    // Metodo especial para Sedução da Guerra
    public void seducaoDaGuerra() {
        System.out.println("Horus seduz com o chamado da guerra!");

        // Aumenta drasticamente o dano mas reduz a defesa
        setForca(getForca() + 5);
        setDefesa(getDefesa() - 3);

        System.out.println("Força aumentada em 5, defesa reduzida em 3!");
    }

    // Habilidade de guerra - Estratégia Militar
    public void estrategiaMilitar(model.interfaces.ICombatente alvo) {
        System.out.println("Horus implementa sua Estratégia Militar!");

        // Dano baseado na defesa do inimigo (quanto mais defesa, mais dano)
        int danoEstrategico = (int) (getForca() * 1.5 + getDefesa() * 0.8);
        alvo.receberDano(danoEstrategico);

        System.out.println("Dano estratégico: " + danoEstrategico +
                " | Horus explora as fraquezas táticas do inimigo!");
    }
}