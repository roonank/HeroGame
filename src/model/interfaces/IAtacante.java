package model.interfaces;

import model.habilidades.Habilidade;
import model.personagens.Personagem;

public interface IAtacante {

    void atacar(Personagem alvo);
    void usarHabilidade(Habilidade habilidade, Personagem alvo);
}
