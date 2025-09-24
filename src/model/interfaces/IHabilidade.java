package model.interfaces;

import model.habilidades.Habilidade;

public interface IHabilidade {
    java.util.List<Habilidade> getHabilidades();

    void adicionarHabilidade(Habilidade habilidade);

    boolean podeUsarHabilidade(Habilidade habilidade);

    void usarHabilidade(Habilidade habilidade, ICombatente alvo);
}
