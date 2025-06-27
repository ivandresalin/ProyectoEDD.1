package src;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class ContactoEditable {

    private CircularDoubleLinkedList<Contacto> listaContactos;
    private Stage ventanaPrincipal;
    private Stage ventanaEditar;

    public ContactoEditable(CircularDoubleLinkedList<Contacto> listaContactos, Stage ventanaPrincipal) {
        this.listaContactos = listaContactos;
        this.ventanaPrincipal = ventanaPrincipal;
    }

    // Clase auxiliar para agrupar campo texto + descripción + botón +
    private static class CampoMultiple {
        TextField campo;
        TextField descripcion;
        Button btnAgregar;
        VBox contenedor;
        ArrayList<Atributo> atributos;

        CampoMultiple(String promptCampo, String promptDesc) {
            campo = new TextField();
            campo.setPromptText(promptCampo);

            descripcion = new TextField();
            descripcion.setPromptText(promptDesc);

            btnAgregar = new Button("+");

            atributos = new ArrayList<>();
            contenedor = new VBox(5);

            HBox fila = new HBox(5, campo, descripcion, btnAgregar);
            contenedor.getChildren().add(fila);

            btnAgregar.setOnAction(e -> {
                String valor = campo.getText().trim();
                String desc = descripcion.getText().trim();
                if (!valor.isEmpty()) {
                    atributos.add(new Atributo(valor, desc.isEmpty() ? "-" : desc));
                    Label etiqueta = new Label(valor + " (" + (desc.isEmpty() ? "-" : desc) + ")");
                    contenedor.getChildren().add(etiqueta);
                    campo.clear();
                    descripcion.clear();
                }
            });
        }

        public VBox getContenedor() {
            return contenedor;
        }

        public ArrayList<Atributo> getAtributos() {
            return atributos;
        }
    }

    public void mostrarFormularioNuevo() {
        ventanaEditar = new Stage();
        ventanaEditar.setTitle("Nuevo Contacto");

        VBox formulario = new VBox(10);
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");

        // Campos múltiples para varios atributos
        CampoMultiple telefonos = new CampoMultiple("Teléfono", "Descripción teléfono");
        CampoMultiple emails = new CampoMultiple("Email", "Descripción email");
        CampoMultiple direcciones = new CampoMultiple("Dirección", "Descripción dirección");
        CampoMultiple fechas = new CampoMultiple("Fecha de interés", "Descripción fecha");
        CampoMultiple redes = new CampoMultiple("Red social", "Descripción red");

        Label labelFotos = new Label("Fotos: 0");
        Button btnAgregarFoto = new Button("Agregar Foto");
        CircularDoubleLinkedList<Foto> fotosTemp = new CircularDoubleLinkedList<>();

        btnAgregarFoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar foto");
            File archivo = fileChooser.showOpenDialog(ventanaEditar);
            if (archivo != null) {
                fotosTemp.add(new Foto(archivo.getAbsolutePath(), "Foto añadida"));
                labelFotos.setText("Fotos: " + fotosTemp.getTamanio());
            }
        });

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");

        btnGuardar.setOnAction(event -> {
            Contacto nuevo = new Contacto(txtNombre.getText(), txtApellido.getText());

            for (Atributo tel : telefonos.getAtributos()) nuevo.addTelefono(tel.getNombre(), tel.getDescripcion());
            for (Atributo em : emails.getAtributos()) nuevo.addEmail(em.getNombre(), em.getDescripcion());
            for (Atributo dir : direcciones.getAtributos()) nuevo.addDireccion(dir.getNombre(), dir.getDescripcion());
            for (Atributo f : fechas.getAtributos()) nuevo.addFechaDeInteres(f.getNombre(), f.getDescripcion());
            for (Atributo red : redes.getAtributos()) nuevo.addRedSocial(red.getNombre(), red.getDescripcion());

            // Agregar fotos cargadas
            if (fotosTemp.getTamanio() > 0) {
                Nodo<Foto> nodo = fotosTemp.getCabeza();
                for (int i = 0; i < fotosTemp.getTamanio(); i++) {
                    nuevo.addFoto(nodo.getDato());
                    nodo = nodo.getSiguiente();
                }
            }

            listaContactos.add(nuevo);
            ventanaEditar.close();
        });

        btnCancelar.setOnAction(event -> ventanaEditar.close());

        formulario.getChildren().addAll(
                txtNombre, txtApellido,
                new Label("Teléfonos:"), telefonos.getContenedor(),
                new Label("Emails:"), emails.getContenedor(),
                new Label("Direcciones:"), direcciones.getContenedor(),
                new Label("Fechas de Interés:"), fechas.getContenedor(),
                new Label("Redes Sociales:"), redes.getContenedor(),
                btnAgregarFoto, labelFotos,
                btnGuardar, btnCancelar
        );

        Scene scene = new Scene(formulario, 400, 700);
        ventanaEditar.setScene(scene);
        ventanaEditar.show();
    }

    public void mostrarFormularioEdicion(Contacto contacto) {
        ventanaEditar = new Stage();
        ventanaEditar.setTitle("Editar Contacto");

        VBox formulario = new VBox(10);
        TextField txtNombre = new TextField(contacto.getNombre());
        TextField txtApellido = new TextField(contacto.getApellido());

        // Reutilizamos la clase CampoMultiple para edición
        CampoMultiple telefonos = new CampoMultiple("Nuevo teléfono", "Descripción teléfono");
        CampoMultiple emails = new CampoMultiple("Nuevo email", "Descripción email");
        CampoMultiple direcciones = new CampoMultiple("Nueva dirección", "Descripción dirección");
        CampoMultiple fechas = new CampoMultiple("Nueva fecha", "Descripción fecha");
        CampoMultiple redes = new CampoMultiple("Nueva red social", "Descripción red");

        // Mostrar los actuales (solo etiquetas, no editables)
        Label labelTel = new Label("Teléfonos actuales:");
        for (Atributo tel : contacto.getTelefonos()) {
            labelTel.setText(labelTel.getText() + "\n- " + tel.toString());
        }
        Label labelEmails = new Label("Emails actuales:");
        for (Atributo em : contacto.getEmails()) {
            labelEmails.setText(labelEmails.getText() + "\n- " + em.toString());
        }
        Label labelDirecciones = new Label("Direcciones actuales:");
        for (Atributo dir : contacto.getDirecciones()) {
            labelDirecciones.setText(labelDirecciones.getText() + "\n- " + dir.toString());
        }
        Label labelFechas = new Label("Fechas de interés actuales:");
        for (Atributo f : contacto.getFechasDeInteres()) {
            labelFechas.setText(labelFechas.getText() + "\n- " + f.toString());
        }
        Label labelRedes = new Label("Redes sociales actuales:");
        for (Atributo r : contacto.getRedesSociales()) {
            labelRedes.setText(labelRedes.getText() + "\n- " + r.toString());
        }

        Label labelFotos = new Label("Fotos actuales: " + contacto.getFotos().getTamanio());
        Button btnAgregarFoto = new Button("Agregar Foto");

        btnAgregarFoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar foto");
            File archivo = fileChooser.showOpenDialog(ventanaEditar);
            if (archivo != null) {
                contacto.addFoto(new Foto(archivo.getAbsolutePath(), "Foto añadida"));
                labelFotos.setText("Fotos actuales: " + contacto.getFotos().getTamanio());
            }
        });

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");

        btnGuardar.setOnAction(event -> {
            contacto.setNombre(txtNombre.getText());
            contacto.setApellido(txtApellido.getText());

            for (Atributo tel : telefonos.getAtributos()) contacto.addTelefono(tel.getNombre(), tel.getDescripcion());
            for (Atributo em : emails.getAtributos()) contacto.addEmail(em.getNombre(), em.getDescripcion());
            for (Atributo dir : direcciones.getAtributos()) contacto.addDireccion(dir.getNombre(), dir.getDescripcion());
            for (Atributo f : fechas.getAtributos()) contacto.addFechaDeInteres(f.getNombre(), f.getDescripcion());
            for (Atributo red : redes.getAtributos()) contacto.addRedSocial(red.getNombre(), red.getDescripcion());

            ventanaEditar.close();
        });

        btnCancelar.setOnAction(event -> ventanaEditar.close());

        formulario.getChildren().addAll(
                txtNombre, txtApellido,
                labelTel, telefonos.getContenedor(),
                labelEmails, emails.getContenedor(),
                labelDirecciones, direcciones.getContenedor(),
                labelFechas, fechas.getContenedor(),
                labelRedes, redes.getContenedor(),
                btnAgregarFoto, labelFotos,
                btnGuardar, btnCancelar
        );

        Scene scene = new Scene(formulario, 400, 900);
        ventanaEditar.setScene(scene);
        ventanaEditar.show();
    }
}
