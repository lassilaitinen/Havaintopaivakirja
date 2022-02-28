package fxPaivakirja;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import paivakirja.Havainto;
import paivakirja.Paivakirja;
import paivakirja.TilaException;


/**
 * @author lassi
 * @version 16.2.2022
 *
 */
public class PaivakirjaGUIController {

    @FXML private Button buttonPoistaH;
    @FXML private Button buttonPoistaLaji;
    @FXML private Button buttonUusiH;
    @FXML private Button buttonUusiLaji;
    @FXML private ComboBox<?> cboxVEhdot;
    @FXML private Menu menuApua;
    @FXML private Menu menuMuokkaa;
    @FXML private Menu menuTiedosto;
    @FXML private TextField textHae;

    @FXML void handlePoistaH() {
        poista();
    }

    @FXML void handlePoistaLaji() {
        poista();
    }
    
    @FXML void keyReleased() {
        haku();
    }
    
    
    @FXML void handeApua() {
        avustus();
    }
    
    
    @FXML void handleUusiH() {
        virheilmoitus();
        avaa("HavaintoDialogView.fxml", "Havainnon lisäys");
    }

    @FXML void handleUusiLaji() {
        virheilmoitus();
        avaa("LajiDialogView.fxml", "Lajin lisäys");
    }

    
    //--------------------------------------
    
    private Paivakirja paivakirja;
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://gitlab.jyu.fi/loklaiti/ohj2");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        } 
    }
    
    
    /**
     *  Avataan haluttu tiedosto/ikkuna
     * @param tiedosto avattava tiedosto
     * @param mitaTehdaan mitä avattavalla tiedostolla tehdään
     */
    private void avaa(String tiedosto, String mitaTehdaan) {
        ModalController.showModal(PaivakirjaGUIController.class.getResource(tiedosto), mitaTehdaan, null, "");
    }
    
    
    /**
     * Aliohjelma hakutoimintoa varten
     */
    private void haku() {
        Dialogs.showMessageDialog("Ei osata hakea vielä!");
    }
    
    /**
     * Aliohjelma poistamista varten
     */
    private void poista() {
        Dialogs.showMessageDialog("Ei osata poistaa vielä!");
    }
    
    
    /**
     * Tulostetaan näyttöön virheilmoitus
     */
    private void virheilmoitus() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    
    
    /**
     * Uuden havainnon lisääminen
     */
    protected void uusiHavainto() {
        Havainto uusi = new Havainto();
        uusi.rekisterointi();
        uusi.vastaa();
        try {
            paivakirja.lisaa(uusi);
        } catch (TilaException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        //TODO: uuden hakemiseen aliohjelma hae(uusi.getID());
    }
    
    
    /**
     * Tulostetaan havainto
     * @param os Mihin tulostetaan
     * @param havainto Tulostettava havainto
     */
    public void tulosta(PrintStream os, final Havainto havainto) {
        os.println("---------");
        havainto.tulosta(os);
        os.println("---------");
    }

    
    
    /**
     * @param paivakirja Päiväkirja jota käytetään
     */
    public void setPaivakirja(Paivakirja paivakirja) {
        this.paivakirja = paivakirja;
        // TODO: naytaHavainnot();
    }


}