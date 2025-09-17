package model.personagens.inimigos.deuses.gregos;

import model.habilidades.Habilidade;
import model.habilidades.efeitos.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.personagens.MagoInimigo;
import model.personagens.Personagem;

import static util.Cores.*;
import java.util.Arrays;

public class Zeus extends MagoInimigo {

    public Zeus() {
        super("Zeus", 100, 10, 8, 100,
                Arrays.asList(
                        new Habilidade("Sabedoria Suprema", TipoHabilidade.MAGICO,
                                25, 3, 0.95, TipoEfeito.DANO),
                        new Habilidade("Choque Destruidor", TipoHabilidade.MAGICO,
                                40, 4, 0.99, TipoEfeito.STUN).comEfeito(2, 0.6, 1.5),
                        new Habilidade("Desolação Divina", TipoHabilidade.MAGICO,
                                35, 5, 0.85, TipoEfeito.DANO)
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Zeus pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (!podeUsarHabilidade(habilidade)) {
            System.out.println(AMARELO + "\nZeus não pode usar esta habilidade!" + RESET);
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