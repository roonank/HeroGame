package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import static util.Cores.*;

import java.util.Arrays;

public class Hercules extends Guerreiro {

    public Hercules() {
        super("Hércules", 100, 18, 10,
                Arrays.asList(
                        new Habilidade("Golpe Poderoso", TipoHabilidade.FISICO,
                                36, 2, 0.9, false), // 18 * 2
                        new Habilidade("Força de Titã", TipoHabilidade.FISICO,
                                45, 3, 0.85, false), // 18 * 2.5
                        new Habilidade("Punhos dos Deuses", TipoHabilidade.FISICO,
                                54, 4, 0.75, false) // 18 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Hércules pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Hércules tem 20% de chance de dano crítico
            int dano = habilidade.calcularDano(getForca());
            if (Math.random() < 0.2) {
                dano = (int) (dano * 1.5);
                System.out.println("DANO CRÍTICO!");
            }

            alvo.receberDano(dano);
            System.out.println("Hércules usa " + habilidade.getNome() +
                    " com poder divino e causa " + dano + " de dano!");
        } else {
            System.out.println("Hércules não pode usar esta habilidade!");
        }
    }

    // Sobrescreve o método do guerreiro com versão mais poderosa
    @Override
    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
        int danoExtra = (int) (getForca() * 2.0); // Mais poderoso que um guerreiro comum
        alvo.receberDano(danoExtra);
        System.out.println("Hércules executa uma Investida Divina causando " +
                danoExtra + " de dano devastador!");
    }

    // Habilidade única de Hércules
    public void furiaDivina(model.interfaces.ICombatente alvo) {
        System.out.println("Hércules entra em Fúria Divina!");

        // Múltiplos ataques
        for (int i = 0; i < 3; i++) {
            int dano = getForca() + (int) (Math.random() * getForca());
            alvo.receberDano(dano);
            System.out.println("Ataque " + (i + 1) + ": " + dano + " de dano!");
        }
    }

    // Metodo especial para recuperar vida
    public void regeneracaoDivina() {
        int cura = getVidaMaxima() / 4;
        curar(cura);
        System.out.println("Hércules usa sua herança divina para recuperar " +
                cura + " pontos de vida!");
    }
}