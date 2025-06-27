package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContactoApp extends Application {

    private CircularDoubleLinkedList<Contacto> listaContactos;
    private Label labelNombre;
    private VBox detallesBox;
    private ImageView fotoContacto;
    private Stage ventanaPrincipal;
    private Button btnFotoAnterior;
    private Button btnFotoSiguiente;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ventanaPrincipal = primaryStage;
        listaContactos = new CircularDoubleLinkedList<>();
        
        // Contacto 1
        Contacto contacto1 = new Contacto("Ana", "Martínez");
        contacto1.addTelefono("0991122334", "Móvil");
        contacto1.addTelefono("022443355", "Casa");
        contacto1.addEmail("ana.martinez@gmail.com", "Personal");
        contacto1.addEmail("ana.m@empresa.com", "Trabajo");
        contacto1.addDireccion("Av. Colón y 10 de Agosto", "Oficina");
        contacto1.addDireccion("Calle 5 y Libertad", "Casa");
        contacto1.addRedSocial("@ana_martinez", "Instagram");
        contacto1.addFechaDeInteres("01/01/1990", "Cumpleaños");

        // Contacto 2
        Contacto contacto2 = new Contacto("Luis", "Gómez");
        contacto2.addTelefono("0988776655", "Celular");
        contacto2.addEmail("luis.gomez@trabajo.com", "Trabajo");
        contacto2.addDireccion("Av. de los Shyris y Portugal", "Oficina");
        contacto2.addRedSocial("@luisgomez", "Twitter");
        contacto2.addRedSocial("luis.gomez", "LinkedIn");
        contacto2.addFechaDeInteres("14/02/2010", "Aniversario");

        // Contacto 3
        Contacto contacto3 = new Contacto("Elena", "Ríos");
        contacto3.addTelefono("0976655443", "Móvil");
        contacto3.addEmail("elena.rios@mail.com", "Principal");
        contacto3.addDireccion("Callejón San Luis 34", "Casa");
        contacto3.addFechaDeInteres("08/03/1995", "Cumpleaños");

        // Relacionar algunos contactos
        contacto1.addContactoRelacionado(contacto2);
        contacto2.addContactoRelacionado(contacto3);

        // Agregarlos a la lista
        listaContactos.add(contacto1);
        listaContactos.add(contacto2);
        listaContactos.add(contacto3);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f5f5f5;");

        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);

        fotoContacto = new ImageView();
        fotoContacto.setFitWidth(100);
        fotoContacto.setFitHeight(100);
        fotoContacto.setPreserveRatio(true);

        labelNombre = new Label("Nombre del contacto");
        labelNombre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox fotoNav = new HBox(5);
        fotoNav.setAlignment(Pos.CENTER);
        btnFotoAnterior = new Button("←");
        btnFotoSiguiente = new Button("→");
        fotoNav.getChildren().addAll(btnFotoAnterior, fotoContacto, btnFotoSiguiente);

        topBox.getChildren().addAll(fotoNav, labelNombre);
        root.setTop(topBox);

        detallesBox = new VBox(10);
        detallesBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(detallesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: white; -fx-padding: 10;");
        root.setCenter(scrollPane);

        HBox botonesNav = new HBox(10);
        botonesNav.setAlignment(Pos.CENTER);
        botonesNav.setPadding(new Insets(10));

        Button btnAnterior = new Button("←");
        Button btnSiguiente = new Button("→");
        Button btnAgregar = new Button("＋");
        Button btnEditar = new Button("✎");
        Button btnEliminar = new Button("🗑");

        botonesNav.getChildren().addAll(btnAnterior, btnSiguiente, btnAgregar, btnEditar, btnEliminar);
        root.setBottom(botonesNav);

        btnAnterior.setOnAction(e -> mostrarContacto(listaContactos.anterior()));
        btnSiguiente.setOnAction(e -> mostrarContacto(listaContactos.siguiente()));
        btnAgregar.setOnAction(e -> new ContactoEditable(listaContactos, ventanaPrincipal).mostrarFormularioNuevo());
        btnEditar.setOnAction(e -> new ContactoEditable(listaContactos, ventanaPrincipal).mostrarFormularioEdicion(listaContactos.getActual()));
        btnEliminar.setOnAction(e -> eliminarContacto());

        btnFotoAnterior.setOnAction(e -> mostrarFotoAnterior());
        btnFotoSiguiente.setOnAction(e -> mostrarFotoSiguiente());

        Scene scene = new Scene(root, 360, 640);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Agenda Móvil");
        primaryStage.show();

        mostrarContacto(listaContactos.getActual());
    }

    private void mostrarContacto(Contacto contacto) {
        if (contacto != null) {
            labelNombre.setText(contacto.getNombre() + " " + contacto.getApellido());
            mostrarFoto(contacto);
            detallesBox.getChildren().clear();

            detallesBox.getChildren().addAll(
                crearSeccion("📞 Teléfonos:", contacto.getTelefonos()),
                crearSeccion("✉️ Emails:", contacto.getEmails()),
                crearSeccion("📍 Direcciones:", contacto.getDirecciones()),
                crearSeccion("🎉 Fechas de interés:", contacto.getFechasDeInteres()),
                crearSeccion("🌐 Redes Sociales:", contacto.getRedesSociales()),
                crearSeccionContactosRelacionados("🤝 Contactos Relacionados:", contacto)
            );
        } else {
            labelNombre.setText("Sin contactos");
            detallesBox.getChildren().clear();
            fotoContacto.setImage(null);
        }
    }

    private VBox crearSeccion(String titulo, java.util.List<Atributo> lista) {
        VBox box = new VBox(3);
        Label labelTitulo = new Label(titulo);
        labelTitulo.setStyle("-fx-font-weight: bold;");
        box.getChildren().add(labelTitulo);
        for (Atributo a : lista) {
            box.getChildren().add(new Label("- " + a.getNombre() + " (" + a.getDescripcion() + ")"));
        }
        return box;
    }

    private VBox crearSeccionContactosRelacionados(String titulo, Contacto contacto) {
        VBox box = new VBox(3);
        Label labelTitulo = new Label(titulo);
        labelTitulo.setStyle("-fx-font-weight: bold;");
        box.getChildren().add(labelTitulo);
        for (Contacto rel : contacto.getContactosRelacionados()) {
            box.getChildren().add(new Label("- " + rel.getNombre() + " " + rel.getApellido()));
        }
        return box;
    }

    private void mostrarFoto(Contacto contacto) {
        if (contacto.getFotos().getTamanio() > 0) {
            Foto foto = contacto.getFotos().getActual();
            fotoContacto.setImage(new Image("file:" + foto.getRuta()));
        } else {
            fotoContacto.setImage(null);
        }
    }

    private void mostrarFotoAnterior() {
        Contacto contacto = listaContactos.getActual();
        if (contacto != null && contacto.getFotos().getTamanio() > 0) {
            contacto.getFotos().anterior();
            mostrarFoto(contacto);
        }
    }

    private void mostrarFotoSiguiente() {
        Contacto contacto = listaContactos.getActual();
        if (contacto != null && contacto.getFotos().getTamanio() > 0) {
            contacto.getFotos().siguiente();
            mostrarFoto(contacto);
        }
    }

    private void eliminarContacto() {
        Contacto contacto = listaContactos.getActual();
        if (contacto != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar a " + contacto.getNombre() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.YES) {
                    listaContactos.remove(contacto);
                    mostrarContacto(listaContactos.getActual());
                }
            });
        }
    }
}
