package model.personagens.inimigos.deuses.egipcios;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.interfaces.ICombatente;
import model.personagens.MagoInimigo;
import static util.Cores.*;
import java.util.Arrays;

public class Ra extends MagoInimigo {

    public Ra() {
        super("Ra", 100, 10, 8, 100,
                Arrays.asList(
                        new Habilidade("Sabedoria Suprema", TipoHabilidade.MAGICO,
                                25, 3, 0.95, false),
                        new Habilidade("Chama Destruidora", TipoHabilidade.MAGICO,
                                40, 4, 0.99, false),
                        new Habilidade("Desolação Divina", TipoHabilidade.MAGICO,
                                35, 5, 0.85, false)
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Ra pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Ra tem 25% de chance de crítico mágico (mais que Ragnar)
            int dano = habilidade.calcularDano(getForca());

            if (Math.random() < 0.25) {
                dano = (int) (dano * 1.6);
                System.out.println(AMARELO + "\nCRÍTICO DIVINO!" + RESET);
            }

            setPontosMagia(getPontosMagia() - habilidade.getCustoMana());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nRa invoca " + habilidade.getNome() +
                    " com poder divino e causa " + dano + " de dano mágico!" + RESET);
        } else {
            System.out.println(AMARELO + "\nRa não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do mago com versão divina
    @Override
    public void explosaoArcana(ICombatente alvo) {
        if (getPontosMagia() >= 12) {
            setPontosMagia(getPontosMagia() - 12);
            int dano = (int) (getForca() * 2.3 + getPontosMagia() * 0.8);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nRa libera uma Explosão Arcana Divina causando " +
                    dano + " de dano celestial!" + RESET);
        } else {
            System.out.println(AMARELO + "\nRa não tem magia suficiente para esta habilidade!" + RESET);
        }
    }

    // Habilidade única de Ra - Sabedoria Estratégica
    public void saberSupremo(ICombatente alvo) {
        System.out.println(AMARELO + "\nRa utiliza seu saber supremo!" + RESET);

        // Analisa o alvo e aplica dano baseado na defesa do inimigo
        int danoBase = getForca() * 2;
        int danoBonus = (int) (getDefesa() * 0.6); // Dano extra baseado na defesa do alvo
        int danoTotal = danoBase + danoBonus;

        alvo.receberDano(danoTotal);
        System.out.println(AMARELO + "\nRa encontra a fraqueza do inimigo causando " +
                danoTotal + " de dano estratégico! (Bonus por defesa: " + danoBonus + ")" + RESET);

        // Recupera 10% da mana gasta no próximo turno
        setPontosMagia(getPontosMagia() + (int) (danoTotal * 0.2));
    }

    // Metodo especial
    public void ataqueCelestial() {
        int escudo = (int) (getVidaMaxima() * 0.3);
        int regeneracaoMana = (int) (getPontosMagia() * 0.25);

        System.out.println(AMARELO + "\nRa invoca o ataque celestial, criando um escudo de " +
                escudo + " pontos e recuperando " + regeneracaoMana + " de magia!" + RESET);

        // Implementação do escudo dependeria da lógica do jogo
        curar(escudo); // Simula o escudo como cura
        setPontosMagia(getPontosMagia() + regeneracaoMana);
    }
}