public class FilaAdapter<T> implements Estrutura<T> {

    private MinhaFila<T> fila = new MinhaFila<>();

    public void adicionar(T valor) {
        fila.enqueue(valor);
    }

    public T remover() {
        return fila.dequeue();
    }

    public boolean estaVazia() {
        return fila.estaVazia();
    }
}