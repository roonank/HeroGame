package model.habilidades.efeitos;

import model.personagens.Personagem;

import java.util.List;

public class Atordoado extends EfeitoStatus{
    public Atordoado(int turnos) { super("Atordoado", turnos);}
    @Override public  boolean impedeAgir() { return  true; }
    @Override public void aoAplicar(Personagem alvo, List<String> log) {
        log.add("💤" + alvo.getNome() + " ficou ATORDOADO por " + turnos + " turno(s)!");
    }
}
