package model.habilidades.efeitos;

import model.personagens.Personagem;

import java.util.List;

public class Queimacao extends EfeitoStatus{
    public Queimacao (int turnos) { super("Queimação", turnos);}
    @Override public void aoAplicar(Personagem alvo, List<String> log) {
        log.add("🔥" + alvo.getNome() + " ficou QUEIMANDO por " + turnos + " turno(s)!");
    }
}
