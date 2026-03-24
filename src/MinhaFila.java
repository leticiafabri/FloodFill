public class MinhaFila<T> {
    private No<T> inicio, fim;

    public void enqueue(T valor) {
        No<T> novo = new No<>(valor);
        if (fim != null) fim.proximo = novo;
        fim = novo;
        if (inicio == null) inicio = novo;
    }

    public T dequeue() {
        if (inicio == null) return null;
        T valor = inicio.valor;
        inicio = inicio.proximo;
        if (inicio == null) fim = null;
        return valor;
    }

    public boolean estaVazia() {
        return inicio == null;
    }
}