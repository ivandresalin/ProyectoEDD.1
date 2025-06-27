package src;

import java.util.ArrayList;

public class Contacto {
    private String nombre;
    private String apellido;

    private ArrayList<Atributo> telefonos;
    private ArrayList<Atributo> emails;
    private ArrayList<Atributo> direcciones;
    private ArrayList<Atributo> fechasDeInteres;
    private ArrayList<Atributo> redesSociales;
    private ArrayList<Contacto> contactosRelacionados;

    private CircularDoubleLinkedList<Foto> fotos;

    public Contacto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;

        this.telefonos = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.direcciones = new ArrayList<>();
        this.fechasDeInteres = new ArrayList<>();
        this.redesSociales = new ArrayList<>();
        this.contactosRelacionados = new ArrayList<>();
        this.fotos = new CircularDoubleLinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Teléfonos
    public ArrayList<Atributo> getTelefonos() {
        return telefonos;
    }

    public void addTelefono(String numero, String descripcion) {
        this.telefonos.add(new Atributo(numero, descripcion));
    }

    // Emails
    public ArrayList<Atributo> getEmails() {
        return emails;
    }

    public void addEmail(String email, String descripcion) {
        this.emails.add(new Atributo(email, descripcion));
    }

    // Direcciones
    public ArrayList<Atributo> getDirecciones() {
        return direcciones;
    }

    public void addDireccion(String direccion, String descripcion) {
        this.direcciones.add(new Atributo(direccion, descripcion));
    }

    // Fechas de interés
    public ArrayList<Atributo> getFechasDeInteres() {
        return fechasDeInteres;
    }

    public void addFechaDeInteres(String fecha, String descripcion) {
        this.fechasDeInteres.add(new Atributo(fecha, descripcion));
    }

    // Redes sociales
    public ArrayList<Atributo> getRedesSociales() {
        return redesSociales;
    }

    public void addRedSocial(String redSocial, String descripcion) {
        this.redesSociales.add(new Atributo(redSocial, descripcion));
    }

    // Contactos relacionados
    public ArrayList<Contacto> getContactosRelacionados() {
        return contactosRelacionados;
    }

    public void addContactoRelacionado(Contacto contacto) {
        this.contactosRelacionados.add(contacto);
    }

    // Fotos
    public CircularDoubleLinkedList<Foto> getFotos() {
        return fotos;
    }

    public void addFoto(Foto foto) {
        fotos.add(foto);
    }

    // Mostrar detalles
    public String mostrarDetalles() {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Nombre: ").append(nombre).append(" ").append(apellido).append("\n");

        detalles.append("Teléfonos: ");
        for (Atributo tel : telefonos) {
            detalles.append(tel.toString()).append("; ");
        }
        detalles.append("\n");

        detalles.append("Emails: ");
        for (Atributo em : emails) {
            detalles.append(em.toString()).append("; ");
        }
        detalles.append("\n");

        detalles.append("Direcciones: ");
        for (Atributo dir : direcciones) {
            detalles.append(dir.toString()).append("; ");
        }
        detalles.append("\n");

        detalles.append("Fechas de Interés: ");
        for (Atributo fecha : fechasDeInteres) {
            detalles.append(fecha.toString()).append("; ");
        }
        detalles.append("\n");

        detalles.append("Redes Sociales: ");
        for (Atributo red : redesSociales) {
            detalles.append(red.toString()).append("; ");
        }
        detalles.append("\n");

        detalles.append("Contactos Relacionados: ");
        for (Contacto c : contactosRelacionados) {
            detalles.append(c.getNombre()).append(" ").append(c.getApellido()).append("; ");
        }

        return detalles.toString();
    }
}
