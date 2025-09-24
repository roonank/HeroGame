package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.efeitos.TipoEfeito;
import model.habilidades.Emun.TipoHabilidade;
import model.personagens.Mago;
import model.personagens.Personagem;

import java.util.Arrays;
import static util.Cores.*;

public class Ragnar extends Mago {

    public Ragnar() {
        super("Ragnar", 100, 12, 8, 80, // Mais vida, força e defesa que um mago comum
                Arrays.asList(
                        new Habilidade("Grito do Viking", TipoHabilidade.MAGICO,
                                30, 5, 0.96, TipoEfeito.STUN).comEfeito(1, 0.5, 0.0), // 12 * 2.5
                        new Habilidade("Machado de Gelo", TipoHabilidade.MAGICO,
                                36, 9, 1.25, TipoEfeito.CONGELAMENTO).comEfeito(2, 0.3, 0.5), // 12 * 3
                        new Habilidade("Fúria Nórdica", TipoHabilidade.MAGICO,
                                42, 12, 2.0, TipoEfeito.DANO) // 12 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Ragnar pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (!podeUsarHabilidade(habilidade)) {
            System.out.println(AMARELO + "\nRagnar não pode usar esta habilidade!" + RESET);
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

        if (!r.acertou()) {
            System.out.printf(AMARELO + "%s usa %s mas ERRA (chance %.0f%%)!" + RESET + "%n",
                    getNome(), habilidade.getNome(), r.chanceAcertoUsada() * 100);
            return;
        }

        if (r.critico()) {
            System.out.printf(VERMELHO + "CRÍTICO x%.2f! " + RESET, r.multiplicadorCritico());
        }

        int danoAplicado = defensor.receberDano(r.dano());
        System.out.printf(AMARELO + "%n%s executa %s e causa %d de dano! " + RESET, getNome(), habilidade.getNome(), danoAplicado);
    }
}