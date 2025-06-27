package src;

public class CircularDoubleLinkedList<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;
    private Nodo<T> actual;  // para mantener el nodo actual

    public CircularDoubleLinkedList() {
        cabeza = null;
        cola = null;
        actual = null;
        tamanio = 0;
    }

    public void add(T dato) {
        Nodo<T> newNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = newNodo;
            cola = newNodo;
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cola);
            actual = cabeza;
        } else {
            cola.setSiguiente(newNodo);
            newNodo.setAnterior(cola);
            newNodo.setSiguiente(cabeza);
            cabeza.setAnterior(newNodo);
            cola = newNodo;
        }
        tamanio++;
    }

    public void remove(T dato) {
        if (cabeza == null) return;

        Nodo<T> current = cabeza;
        do {
            if (current.getDato().equals(dato)) {
                if (current == cabeza && current == cola) {
                    cabeza = null;
                    cola = null;
                    actual = null;
                } else if (current == cabeza) {
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(cola);
                    cola.setSiguiente(cabeza);
                    if (actual == current) actual = cabeza;
                } else if (current == cola) {
                    cola = cola.getAnterior();
                    cola.setSiguiente(cabeza);
                    cabeza.setAnterior(cola);
                    if (actual == current) actual = cabeza;
                } else {
                    current.getAnterior().setSiguiente(current.getSiguiente());
                    current.getSiguiente().setAnterior(current.getAnterior());
                    if (actual == current) actual = current.getSiguiente();
                }
                tamanio--;
                break;
            }
            current = current.getSiguiente();
        } while (current != cabeza);
    }

    // Devuelve el dato actual y avanza el puntero actual
    public T siguiente() {
        if (actual != null) {
            actual = actual.getSiguiente();
            return actual.getDato();
        }
        return null;
    }

    // Devuelve el dato actual y retrocede el puntero actual
    public T anterior() {
        if (actual != null) {
            actual = actual.getAnterior();
            return actual.getDato();
        }
        return null;
    }

    // Devuelve el dato actual sin mover el puntero
    public T getActual() {
        if (actual != null) {
            return actual.getDato();
        }
        return null;
    }

    public Nodo<T> getCabeza() {
        return cabeza;
    }

    public int getTamanio() {
        return tamanio;
    }
}
