package model.personagens.inimigos.deuses.gregos;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.GuerreiroInimigo;
import static util.Cores.*;
import java.util.Arrays;

public class Ares extends GuerreiroInimigo {

    public Ares() {
        super("Ares", 100, 20, 8,
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
        // Ares pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Ares tem 25% de chance de dano crítico
            int dano = habilidade.calcularDano(getForca());
            if (Math.random() < 0.25) {
                dano = (int) (dano * 1.8); // Multiplicador maior
                System.out.println(AMARELO + "\nFÚRIA GUERREIRA! DANO CRÍTICO!" + RESET);
            }

            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAres usa " + habilidade.getNome() +
                    " com sede de batalha e causa " + dano + " de dano!" + RESET);
        } else {
            System.out.println(AMARELO + "\nAres não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do guerreiro com versão mais agressiva
    @Override
    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
        int danoExtra = (int) (getForca() * 2.5);
        alvo.receberDano(danoExtra);
        System.out.println(AMARELO + "\nAres executa uma Investida Sangrenta causando " +
                danoExtra + " de dano devastador!" + RESET);

        // Ares ganha um bônus de força após a investida
        setForca(getForca() + 2);
        System.out.println(AMARELO + "\nAres fica mais furioso! Força aumentada!" + RESET);
    }

    // Metodo especial para Sedução da Guerra
    public void seducaoDaGuerra() {
        System.out.println(AMARELO + "\nAres seduz com o chamado da guerra!" + RESET);

        // Aumenta drasticamente o dano mas reduz a defesa
        setForca(getForca() + 5);
        setDefesa(getDefesa() - 3);

        System.out.println(AMARELO + "\nForça aumentada em 5, defesa reduzida em 3!" + RESET);
    }

    // Habilidade de guerra - Estratégia Militar
    public void estrategiaMilitar(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nAres implementa sua Estratégia Militar!" + RESET);

        // Dano baseado na defesa do inimigo (quanto mais defesa, mais dano)
        int danoEstrategico = (int) (getForca() * 1.5 + getDefesa() * 0.8);
        alvo.receberDano(danoEstrategico);

        System.out.println(AMARELO + "\nDano estratégico: " + danoEstrategico +
                " | Ares explora as fraquezas táticas do inimigo!" + RESET);
    }
}