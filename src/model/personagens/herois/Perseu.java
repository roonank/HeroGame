package model.personagens.herois;

import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;
import model.personagens.Guerreiro;
import static util.Cores.*;
import java.util.Arrays;

public class Perseu extends Guerreiro {

    public Perseu() {
        super("Perseu", 100, 16, 12,
                Arrays.asList(
                        new Habilidade("Corte da Harpe", TipoHabilidade.FISICO,
                                32, 2, 0.95, false), // 16 * 2
                        new Habilidade("Golpe da Medusa", TipoHabilidade.FISICO,
                                40, 3, 0.9, false), // 16 * 2.5
                        new Habilidade("Fúria do Herói", TipoHabilidade.FISICO,
                                48, 4, 0.8, false) // 16 * 3
                ));
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        // Perseu pode usar qualquer habilidade física
        return habilidade.getTipo() == TipoHabilidade.FISICO;
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (podeUsarHabilidade(habilidade)) {
            // Perseu tem 25% de chance de esquiva e contra-ataque
            int dano = habilidade.calcularDano(getForca());

            // Chance de esquiva ágil
            if (Math.random() < 0.25) {
                System.out.println(AMARELO + "\nPerseu esquiva com agilidade e contra-ataca!" + RESET);
                dano = (int) (dano * 1.3);
            }

            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nPerseu usa " + habilidade.getNome() +
                    " com precisão heroica e causa " + dano + " de dano!" + RESET);
        } else {
            System.out.println(AMARELO + "\nPerseu não pode usar esta habilidade!" + RESET);
        }
    }

    // Sobrescreve o método do guerreiro com versão mais ágil
    @Override
    public void investidaFuriosa(model.interfaces.ICombatente alvo) {
        int danoBase = getForca() + getDefesa(); // Usa força + defesa para representar agilidade
        alvo.receberDano(danoBase);
        System.out.println(AMARELO + "\nPerseu executa uma Investida Ágil causando " +
                danoBase + " de dano preciso!" + RESET);
    }

    // Habilidade única de Perseu - Reflexos Sobrenaturais
    public void reflexosSobrenaturais(model.interfaces.ICombatente alvo) {
        System.out.println(AMARELO + "\nPerseu ativa seus Reflexos Sobrenaturais!" + RESET);

        // Ataques rápidos e precisos baseados na força e defesa
        for (int i = 0; i < 4; i++) {
            int dano = (getForca() / 2) + (int) (Math.random() * getDefesa());
            alvo.receberDano(dano);
            System.out.println(AMARELO + "\nAtaque Rápido " + (i + 1) + ": " + dano + " de dano preciso!" + RESET);
        }
    }

    // Metodo especial para usar o Escudo de Atena
    public void escudoDeAtena() {
        int defesaExtra = getDefesa() * 2;
        // Implementação temporária - aumenta a defesa temporariamente
        int defesaOriginal = getDefesa();
        setDefesa(defesaOriginal + defesaExtra);
        System.out.println(AMARELO + "\nPerseu usa o Escudo de Atena, aumentando sua defesa em " +
                defesaExtra + " pontos! Defesa atual: " + RESET + getDefesa());

        // Em uma implementação completa, precisaria de um mecanismo para
        // reverter este aumento após alguns turnos
    }
}