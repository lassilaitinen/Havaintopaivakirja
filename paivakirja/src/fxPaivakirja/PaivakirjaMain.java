package fxPaivakirja;

import javafx.application.Application;
import javafx.stage.Stage;
import paivakirja.Paivakirja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author lassi
 * @version 20.1.2022
 *
 */
public class PaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaivakirjaGUIView.fxml"));
            final Pane root = ldr.load();
            final PaivakirjaGUIController paivakirjaCtrl = (PaivakirjaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("paivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Paivakirja");
            Paivakirja paivakirja = new Paivakirja();
            paivakirjaCtrl.setPaivakirja(paivakirja);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}