package model.habilidades.efeitos;

import model.personagens.Personagem;

import java.util.List;

public class Congelamento extends EfeitoStatus{
    public Congelamento (int turnos) { super("Congelamento", turnos);}
    @Override public boolean impedeAgir() {return true;}
    @Override public void aoAplicar(Personagem alvo, List<String> log) {
        log.add("❄️" + alvo.getNome() + " ficou CONGELADO por " + turnos + " turno(s)!");
    }
}
