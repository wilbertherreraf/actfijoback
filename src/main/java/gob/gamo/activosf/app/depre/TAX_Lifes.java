package gob.gamo.activosf.app.depre;

public enum TAX_Lifes {
    NONE(0),
    THREE(3),
    FIVE(5),
    SEVEN(7),
    TEN(10),
    FIFTEEN(15),
    TWENTY(20);

    private final int valor;

    private TAX_Lifes(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }
}
