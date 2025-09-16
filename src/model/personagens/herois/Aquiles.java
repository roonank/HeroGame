package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.efeitos.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import model.personagens.Personagem;

import static util.Cores.*;

import java.util.Arrays;

public class Aquiles extends Guerreiro {

    public Aquiles() {
        super("Aquiles", 100, 17, 9,
                Arrays.asList(
                        new Habilidade("Golpe do Talão", TipoHabilidade.FISICO,
                                34, 5, 0.95, TipoEfeito.STUN).comEfeito(1, 0.55, 0.0), // 17 * 2
                        new Habilidade("Investida Imortal", TipoHabilidade.FISICO,
                                42, 8, 1.25, TipoEfeito.DANO), // 17 * 2.5
                        new Habilidade("Fúria do Herói", TipoHabilidade.FISICO,
                                51, 15, 2.0, TipoEfeito.DANO) // 17 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Aquiles pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (!podeUsarHabilidade(habilidade)) {
            System.out.println(AMARELO + "\nAquiles não pode usar esta habilidade!" + RESET);
            return;
        }
        if (!(alvo instanceof Personagem)) {
            System.out.println(AMARELO + "\nAlvo inválido para " + habilidade.getNome() + "!" + RESET);
            return;
        }

        int custo = habilidade.getCustoMana();
        if (!temMana(custo)){
            System.out.printf(AMARELO + "\nMana insuficiente! (%d/%d) - %s custa %d." + RESET + getManaAtual(), getManaMaxima(), habilidade.getNome(), custo);
            return;
        }

        gastarMana(custo);

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
}