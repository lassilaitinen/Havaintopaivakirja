package fxPaivakirja;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;


/**
 * @author lassi
 * @version 20.1.2022
 *
 */
public class PaivakirjaGUIController {

    @FXML
    private Button buttonPoistaH;

    @FXML
    private Button buttonPoistaLaji;

    @FXML
    private Button buttonUusiH;

    @FXML
    private Button buttonUusiLaji;

    @FXML
    private ComboBox<?> cboxVEhdot;

    @FXML
    private Menu menuApua;

    @FXML
    private Menu menuMuokkaa;

    @FXML
    private Menu menuTiedosto;

    @FXML
    private TextField textHae;

    @FXML
    void mouseClicked() {
        virheilmoitus();
    }
    
    @FXML
    void keyReleased() {
        virheilmoitus();
    }
    
    
    @FXML
    void handeApua() {
        avustus();
    }
    
    @FXML void handleUusiH() {
        avaa("HavaintoDialogView.fxml", "Havainnon lisäys");
    }

    @FXML
    void handleUusiLaji() {
        avaa("LajiDialogView.fxml", "Lajin lisäys");
    }

    
    //--------------------------------------
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2022k/ht/loklaiti");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        } 
    }
    
    private void avaa(String tiedosto, String mitaTehdaan) {
        ModalController.showModal(PaivakirjaGUIController.class.getResource(tiedosto), mitaTehdaan, null, "");
    }
    
    
    
    
    private void virheilmoitus() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }

}