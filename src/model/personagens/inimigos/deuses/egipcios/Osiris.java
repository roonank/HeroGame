package model.personagens.inimigos.deuses.egipcios;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.MagoInimigo;
import model.personagens.Personagem;

import static util.Cores.*;
import java.util.Arrays;

public class Osiris extends MagoInimigo {

    public Osiris() {
        super("Osiris", 100, 12, 8, 90, // Mais vida e força que Atena, menos mana
                Arrays.asList(
                        new Habilidade("Tridente dos Mares", TipoHabilidade.MAGICO,
                                30, 4, 0.9, TipoEfeito.DANO), // 12 * 2.5
                        new Habilidade("Tsunami Devastador", TipoHabilidade.MAGICO,
                                36, 5, 0.85, TipoEfeito.DANO), // 12 * 3
                        new Habilidade("Fúria dos Oceanos", TipoHabilidade.MAGICO,
                                42, 6, 0.8, TipoEfeito.DANO) // 12 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Osiris pode usar habilidades mágicas se tiver mana suficiente
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

    // Sobrescreve o metodo do mago com versão marinha
//    @Override
//    public void explosaoArcana(model.interfaces.ICombatente alvo) {
//        if (getPontosMagia() >= 15) {
//            setPontosMagia(getPontosMagia() - 15);
//            int dano = (int) (getForca() * 2.8 + getPontosMagia() * 0.4);
//            alvo.receberDano(dano);
//            System.out.println(AMARELO + "\nOsiris libera uma Explosão Arcana Marinha causando " +
//                    dano + " de dano devastador das profundezas!" + RESET);
//        } else {
//            System.out.println(AMARELO + "\nOsiris não tem magia suficiente para esta habilidade!" + RESET);
//        }
//    }

}