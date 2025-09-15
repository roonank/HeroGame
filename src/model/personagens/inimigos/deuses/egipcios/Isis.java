package model.personagens.inimigos.deuses.egipcios;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.MagoInimigo;
import model.personagens.Personagem;

import static util.Cores.*;
import java.util.Arrays;

public class Isis extends MagoInimigo {

    public Isis() {
        super("Isis", 100, 10, 7, 100, // Menos vida e defesa, mas mais mana que Ragnar
                Arrays.asList(
                        new Habilidade("Sabedoria Divina", TipoHabilidade.MAGICO,
                                25, 3, 0.95, TipoEfeito.DANO), // 10 * 2.5
                        new Habilidade("Lança da Vitória", TipoHabilidade.MAGICO,
                                30, 4, 0.9, TipoEfeito.DANO), // 10 * 3
                        new Habilidade("Escudo de Égide", TipoHabilidade.MAGICO,
                                35, 5, 0.85, TipoEfeito.DANO) // 10 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Isis pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
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

    // Sobrescreve o metodo do mago com versão divina
//    @Override
//    public void explosaoArcana(model.interfaces.ICombatente alvo) {
//        if (getPontosMagia() >= 12) {
//            setPontosMagia(getPontosMagia() - 12);
//            int dano = (int) (getForca() * 2.2 + getPontosMagia() * 0.6);
//            alvo.receberDano(dano);
//            System.out.println(AMARELO + "\nIsis libera uma Explosão Arcana Divina causando " +
//                    dano + " de dano celestial!" + RESET);
//        } else {
//            System.out.println(AMARELO + "\nIsis não tem magia suficiente para esta habilidade!" + RESET);
//        }
//    }
}