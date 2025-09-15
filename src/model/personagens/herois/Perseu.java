package model.personagens.herois;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import model.personagens.Personagem;

import static util.Cores.*;
import java.util.Arrays;

public class Perseu extends Guerreiro {

    public Perseu() {
        super("Perseu", 100, 16, 12,
                Arrays.asList(
                        new Habilidade("Corte da Harpe", TipoHabilidade.FISICO,
                                32, 2, 0.95, TipoEfeito.DANO), // 16 * 2
                        new Habilidade("Golpe da Medusa", TipoHabilidade.FISICO,
                                40, 3, 0.9, TipoEfeito.DANO), // 16 * 2.5
                        new Habilidade("Fúria do Herói", TipoHabilidade.FISICO,
                                48, 4, 0.8, TipoEfeito.DANO) // 16 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Perseu pode usar qualquer habilidade física
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