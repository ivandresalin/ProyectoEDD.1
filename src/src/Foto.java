package src;

public class Foto {
    private String ruta;
    private String descripcion;

    public Foto(String ruta, String descripcion) {
        this.ruta = ruta;
        this.descripcion = descripcion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Foto: " + descripcion + " [" + ruta + "]";
    }
}
