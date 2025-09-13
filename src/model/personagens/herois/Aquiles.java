package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import static util.Cores.*;

import java.security.interfaces.RSAKey;
import java.util.Arrays;

public class Aquiles extends Guerreiro {

    public Aquiles() {
        super("Aquiles", 100, 17, 9,
                Arrays.asList(
                        new Habilidade("Golpe do Talão", TipoHabilidade.FISICO,
                                34, 2, 0.95, false), // 17 * 2
                        new Habilidade("Investida Imortal", TipoHabilidade.FISICO,
                                42, 3, 0.9, false), // 17 * 2.5
                        new Habilidade("Fúria do Herói", TipoHabilidade.FISICO,
                                51, 4, 0.85, false) // 17 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Aquiles pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Aquiles tem 30% de chance de acerto crítico
            int dano = habilidade.calcularDano(getForca());
            if (Math.random() < 0.3) {
                dano = (int) (dano * 1.6); // Dano crítico mais forte
                System.out.println(AMARELO + "\nGOLPE PERFEITO DE AQUILES!" + RESET);
            }

            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAquiles executa " + habilidade.getNome() +
                    " com precisão lendária e causa " + dano + " de dano!" + RESET);
        } else {
            System.out.println(AMARELO + "\nAquiles não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o metodo do guerreiro com versão mais ágil
    @Override
    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
        int danoExtra = getForca() + getDefesa() + 5; // Mais ágil que um guerreiro comum
        alvo.receberDano(danoExtra);
        System.out.println(AMARELO + "\nAquiles executa uma Investida Imortal causando " +
                danoExtra + " de dano devastador!" + RESET);
    }

    // Habilidade única de Aquiles - Velocidade Sobrenatural
    public void velocidadeSobrenatural(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nAquiles ativa sua Velocidade Sobrenatural!" + RESET);

        // Ataques extremamente rápidos
        for (int i = 0; i < 5; i++) {
            int dano = (getForca() / 3) + (int) (Math.random() * 8);
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAtaque Relâmpago " + (i + 1) + ": " + dano + " de dano!" + RESET);

            // 20% de chance de ataque extra por golpe
            if (Math.random() < 0.2) {
                int danoExtra = (int) (dano * 0.5);
                alvo.receberDano(danoExtra);
                System.out.println(AMARELO + "\n  → Ataque Extra: " + danoExtra + " de dano!" + RESET);
            }
        }
    }

    // Metodo especial - Fúria de Aquiles
    public void furiaDeAquiles(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nAquiles entra em Fúria Imortal!" + RESET);

        // Aumento temporário de atributos
        int forcaOriginal = getForca();
        int defesaOriginal = getDefesa();

        setForca((int) (forcaOriginal * 1.4));
        setDefesa((int) (defesaOriginal * 0.7)); // Menos defesa, mais ofensiva

        System.out.println(AMARELO + "\nForça aumentada para " + getForca() +
                ", Defesa reduzida para " + getDefesa() + "!" + RESET);
        // Em uma implementação completa, precisaria reverter esses valores
        // após alguns turnos ou ao final do combate
    }
}