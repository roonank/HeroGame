package model.personagens.inimigos.deuses.gregos;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.MagoInimigo;
import static util.Cores.*;
import java.util.Arrays;

public class Atena extends MagoInimigo {

    public Atena() {
        super("Atena", 100, 10, 7, 100, // Menos vida e defesa, mas mais mana que Ragnar
                Arrays.asList(
                        new Habilidade("Sabedoria Divina", TipoHabilidade.MAGICO,
                                25, 3, 0.95, false), // 10 * 2.5
                        new Habilidade("Lança da Vitória", TipoHabilidade.MAGICO,
                                30, 4, 0.9, false), // 10 * 3
                        new Habilidade("Escudo de Égide", TipoHabilidade.MAGICO,
                                35, 5, 0.85, false) // 10 * 3.5
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Atena pode usar habilidades mágicas se tiver mana suficiente
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                getPontosMagia() >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Atena tem 25% de chance de crítico mágico (mais que Ragnar)
            int dano = habilidade.calcularDano(getForca());

            if (Math.random() < 0.25) {
                dano = (int) (dano * 1.6);
                System.out.println(AMARELO + "\nCRÍTICO DIVINO!" + RESET);
            }

            setPontosMagia(getPontosMagia() - habilidade.getCustoMana());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAtena invoca " + habilidade.getNome() +
                    " com poder divino e causa " + dano + " de dano mágico!" + RESET);
        } else {
            System.out.println(AMARELO + "\nAtena não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do mago com versão divina
    @Override
    public void explosaoArcana(model.interfaces.ICombatente alvo) {
        if (getPontosMagia() >= 12) {
            setPontosMagia(getPontosMagia() - 12);
            int dano = (int) (getForca() * 2.2 + getPontosMagia() * 0.6);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAtena libera uma Explosão Arcana Divina causando " +
                    dano + " de dano celestial!" + RESET);
        } else {
            System.out.println(AMARELO + "\nAtena não tem magia suficiente para esta habilidade!" + RESET);
        }
    }

    // Habilidade única de Atena - Sabedoria Estratégica
    public void sabedoriaEstrategica(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nAtena utiliza sua Sabedoria Estratégica!" + RESET);

        // Analisa o alvo e aplica dano baseado na defesa do inimigo
        int danoBase = getForca() * 2;
        int danoBonus = (int) (getDefesa() * 0.5); // Dano extra baseado na defesa do alvo
        int danoTotal = danoBase + danoBonus;

        alvo.receberDano(danoTotal);
        System.out.println(AMARELO + "\nAtena encontra a fraqueza do inimigo causando " +
                danoTotal + " de dano estratégico! (Bonus por defesa: " + danoBonus + ")" + RESET);

        // Recupera 10% da mana gasta no próximo turno
        setPontosMagia(getPontosMagia() + (int) (danoTotal * 0.1));
    }

    // Metodo especial para Proteção Divina
    public void protecaoDivina() {
        int escudo = (int) (getVidaMaxima() * 0.3);
        int regeneracaoMana = (int) (getPontosMagia() * 0.25);

        // Cria um escudo protetor
        System.out.println(AMARELO + "\nAtena invoca uma Proteção Divina, criando um escudo de " +
                escudo + " pontos e recuperando " + regeneracaoMana + " de magia!" + RESET);

        // Implementação do escudo dependeria da lógica do jogo
        curar(escudo); // Simula o escudo como cura temporária
        setPontosMagia(getPontosMagia() + regeneracaoMana);
    }
}