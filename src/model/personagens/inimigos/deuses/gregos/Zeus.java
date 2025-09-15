package model.personagens.inimigos.deuses.gregos;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.interfaces.ICombatente;
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
                        new Habilidade("Chama Destruidora", TipoHabilidade.MAGICO,
                                40, 4, 0.99, TipoEfeito.DANO),
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
        if (podeUsarHabilidade(habilidade)) {
            Personagem defensor = (Personagem) alvo;
            // Zeus tem 25% de chance de crítico mágico (mais que Ragnar)
            int dano = CalculoDano.calcularDano(this, defensor, habilidade);

            if (Math.random() < 0.25) {
                dano = (int) (dano * 1.6);
                System.out.println(AMARELO + "\nCRÍTICO DIVINO!" + RESET);
            }

            setPontosMagia(getPontosMagia() - habilidade.getCustoMana());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nZeus invoca " + habilidade.getNome() +
                    " com poder divino e causa " + dano + " de dano mágico!" + RESET);
        } else {
            System.out.println(AMARELO + "\nZeus não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do mago com versão divina
    @Override
    public void explosaoArcana(ICombatente alvo) {
        if (getPontosMagia() >= 12) {
            setPontosMagia(getPontosMagia() - 12);
            int dano = (int) (getForca() * 2.3 + getPontosMagia() * 0.8);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nZeus libera uma Explosão Arcana Divina causando " +
                    dano + " de dano celestial!" + RESET);
        } else {
            System.out.println(AMARELO + "\nZeus não tem magia suficiente para esta habilidade!" + RESET);
        }
    }

    // Habilidade única de Zeus - Sabedoria Estratégica
    public void saberSupremo(ICombatente alvo) {
        System.out.println(AMARELO + "\nZeus utiliza seu saber supremo!" + RESET);

        // Analisa o alvo e aplica dano baseado na defesa do inimigo
        int danoBase = getForca() * 2;
        int danoBonus = (int) (getDefesa() * 0.6); // Dano extra baseado na defesa do alvo
        int danoTotal = danoBase + danoBonus;

        alvo.receberDano(danoTotal);
        System.out.println(AMARELO + "\nZeus encontra a fraqueza do inimigo causando " +
                danoTotal + " de dano estratégico! (Bonus por defesa: " + danoBonus + ")" + RESET);

        // Recupera 10% da mana gasta no próximo turno
        setPontosMagia(getPontosMagia() + (int) (danoTotal * 0.2));
    }

    // Metodo especial
    public void ataqueCelestial() {
        int escudo = (int) (getVidaMaxima() * 0.3);
        int regeneracaoMana = (int) (getPontosMagia() * 0.25);

        System.out.println(AMARELO + "\nZeus invoca o ataque celestial, criando um escudo de " +
                escudo + " pontos e recuperando " + regeneracaoMana + " de magia!" + RESET);

        // Implementação do escudo dependeria da lógica do jogo
        curar(escudo); // Simula o escudo como cura
        setPontosMagia(getPontosMagia() + regeneracaoMana);
    }
}