package model.personagens.inimigos.deuses.gregos;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.GuerreiroInimigo;
import model.personagens.Personagem;

import static util.Cores.*;
import java.util.Arrays;

public class Ares extends GuerreiroInimigo {

    public Ares() {
        super("Ares", 100, 20, 8,
                Arrays.asList(
                        new Habilidade("Lança da Discórdia", TipoHabilidade.FISICO,
                                40, 2, 0.85, TipoEfeito.DANO), // 20 * 2
                        new Habilidade("Golpe da Guerra", TipoHabilidade.FISICO,
                                50, 3, 0.8, TipoEfeito.DANO), // 20 * 2.5
                        new Habilidade("Fúria Belicosa", TipoHabilidade.FISICO,
                                60, 4, 0.7, TipoEfeito.DANO) // 20 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Ares pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (!podeUsarHabilidade(habilidade)) {
            System.out.println(AMARELO + "\n" + getNome() + "não pode usar esta habilidade!" + RESET);
            return;
        }
        if (!(alvo instanceof Personagem)) {
            System.out.println(AMARELO + "\nAlvo inválido para " + habilidade.getNome() + "!" + RESET);
            return;
        }

        Personagem defensor = (Personagem) alvo;

        calculadora.ResultadoAtaque r = calculadora.CalculoDano.calcularDetalhado(this, defensor, habilidade);

        if (!r.acertou) {
            System.out.printf(AMARELO + "%s usa %s mas ERRA (chance %.0f%%)!" + RESET + "%n",
                    getNome(), habilidade.getNome(), r.chanceAcertoUsada * 100);
            return;
        }

        if (r.critico) {
            System.out.printf(VERMELHO + "CRÍTICO x%.2f! " + RESET, r.multiplicadorCritico);
        }

        int danoAplicado = defensor.receberDano(r.dano);
        System.out.printf(AMARELO + "%n%s executa %s e causa %d de dano! " + RESET, getNome(), habilidade.getNome(), danoAplicado);
    }

    // Sobrescreve o metodo do guerreiro com versão mais agressiva
//    @Override
//    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
//        int danoExtra = (int) (getForca() * 2.5);
//        alvo.receberDano(danoExtra);
//        System.out.println(AMARELO + "\nAres executa uma Investida Sangrenta causando " +
//                danoExtra + " de dano devastador!" + RESET);
//
//        // Ares ganha um bônus de força após a investida
//        setForca(getForca() + 2);
//        System.out.println(AMARELO + "\nAres fica mais furioso! Força aumentada!" + RESET);
//    }
}