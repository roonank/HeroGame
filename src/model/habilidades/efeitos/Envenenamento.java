package model.habilidades.efeitos;

import model.personagens.Personagem;

import java.util.List;

public class Envenenamento extends EfeitoStatus{
    public Envenenamento(int turnos) { super("Envenenado", turnos);}
    @Override public void aoAplicar(Personagem alvo, List<String> log) {
        log.add("🩻" + alvo.getNome() + " ficou ENVENENADO por " + turnos + "turno(s)!");
    }
}
