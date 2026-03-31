public interface Estrutura<T> {
    void adicionar(T valor);
    T remover();
    boolean estaVazia();
}