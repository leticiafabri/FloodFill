public interface Estrutura<T> {
    void adicionar(T valor);   // push / enqueue
    T remover();               // pop / dequeue
    boolean estaVazia();
}