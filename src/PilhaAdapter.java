public class PilhaAdapter<T> implements Estrutura<T> {

    private MinhaPilha<T> pilha = new MinhaPilha<>();

    public void adicionar(T valor) {
        pilha.push(valor);
    }

    public T remover() {
        return pilha.pop();
    }

    public boolean estaVazia() {
        return pilha.estaVazia();
    }
}