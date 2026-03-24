public class MinhaPilha<T> {
    private No<T> topo;

    public void push(T valor) {
        No<T> novo = new No<>(valor);
        novo.proximo = topo;
        topo = novo;
    }

    public T pop() {
        if (topo == null) return null;
        T valor = topo.valor;
        topo = topo.proximo;
        return valor;
    }

    public boolean estaVazia() {
        return topo == null;
    }
}