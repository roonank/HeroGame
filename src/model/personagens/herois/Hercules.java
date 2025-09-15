package model.personagens.herois;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import model.personagens.Personagem;

import static util.Cores.*;

import java.util.Arrays;

public class Hercules extends Guerreiro {

    public Hercules() {
        super("Hércules", 100, 18, 10,
                Arrays.asList(
                        new Habilidade("Golpe Poderoso", TipoHabilidade.FISICO,
                                36, 2, 0.9, TipoEfeito.DANO), // 18 * 2
                        new Habilidade("Força de Titã", TipoHabilidade.FISICO,
                                45, 3, 0.85, TipoEfeito.DANO), // 18 * 2.5
                        new Habilidade("Punhos dos Deuses", TipoHabilidade.FISICO,
                                54, 4, 0.75, TipoEfeito.DANO) // 18 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Hércules pode usar qualquer habilidade física
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