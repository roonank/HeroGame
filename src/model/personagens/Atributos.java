package model.personagens;

public class Atributos {
    private int forca;
    private int agilidade;
    private int inteligentia;
    private int constituicao;

    public Atributos(int forca, int agilidade, int inteligentia, int constituicao) {
        this.forca = forca;
        this.agilidade = agilidade;
        this.inteligentia = inteligentia;
        this.constituicao = constituicao;
    }

    public int getForca() { return forca; }
    public int getAgilidade() { return agilidade; }
    public int getInteligentia() { return inteligentia; }
    public int getConstituicao() { return constituicao; }

    public void aumentarForca(int pontos) { this.forca += pontos; }
    public void aumentarAgilidade(int pontos) { this.agilidade += pontos; }
    public void aumentarInteligencia(int pontos) { this.inteligentia += pontos; }
    public void aumentarConstituicao(int pontos) { this.constituicao += pontos; }

    @Override
    public String toString(){
        return "Força: " + this.forca + " | Agilidade: "+ this.agilidade + " | Inteligência: " + this.inteligentia + " | Constituição: " + this.constituicao;
    }
}
