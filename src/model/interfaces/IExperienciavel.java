package model.interfaces;

public interface IExperienciavel {
    int getNivel();
    int getExperiencia();
    void ganharExperiencia(int quantidade);
    boolean podeSubirNivel();
    void subirNivel();
}