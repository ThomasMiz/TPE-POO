package frontend;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.Optional;

public class AppMenuBar extends MenuBar {
    private final PaintPane paintPane;

    public AppMenuBar(PaintPane paintPane) {
        this.paintPane = paintPane;

        Menu file = new Menu("Archivo");
        MenuItem clearMenuItem = new MenuItem("Borrar todo");
        clearMenuItem.setOnAction(this::onClearClicked);
        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(this::onExitClicked);
        file.getItems().addAll(clearMenuItem, exitMenuItem);

        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(this::onAboutClicked);
        help.getItems().add(aboutMenuItem);

        getMenus().addAll(file, help);
    }

    private void onClearClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Esta seguro que quiere borrar todo su dibujo?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Borrar todo");
        alert.setHeaderText("Eliminar todas las figuras");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Por favor no lo borres, Franco! Tu dibujo es hermoso :'(", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Borrar todo??????");
            alert.setHeaderText("No lo hagas!!!");
            result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES)
                paintPane.reset();

        }
    }

    private void onExitClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("Salir de la aplicación");
        alert.setContentText("¿Está seguro que desea salir de la aplicación?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0);
    }

    private void onAboutClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca De");
        alert.setHeaderText("Paint");
        alert.setContentText("TPE Final POO Julio 2021\nRealizado por Alejo Caeiro, Tomas Marengo y Thomas Mizrahi");
        alert.showAndWait();
    }
}
