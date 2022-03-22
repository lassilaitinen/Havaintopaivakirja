package fxPaivakirja;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import paivakirja.Havainto;
import paivakirja.Laji;
import paivakirja.Paivakirja;
import paivakirja.TilaException;


/**
 * @author lassi
 * @version 14.3.2022
 *
 */
public class PaivakirjaGUIController implements Initializable{

    @FXML private Button buttonPoistaH;
    @FXML private Button buttonPoistaLaji;
    @FXML private Button buttonUusiH;
    @FXML private Button buttonUusiLaji;
    @FXML private ComboBoxChooser<Havainto> choiceHav;
    @FXML private Menu menuApua;
    @FXML private Menu menuMuokkaa;
    @FXML private Menu menuTiedosto;
    @FXML private TextField textHae;
    @FXML private ScrollPane areaHavainnot;
    //@FXML private TextArea textHav;
    
    /*
    @Override
    public void initialize(URL url, ResourceBundle rbundle) {
        alusta();
    }
    */

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
        uusiHavainto();
        //avaa("HavaintoDialogView.fxml", "Havainnon lisäys");
    }

    @FXML void handleUusiLaji() {
        uusiLaji();
        //avaa("LajiDialogView.fxml", "Lajin lisäys");
    }
    
    

    
    //--------------------------------------
    
    private Paivakirja paivakirja;
    private Havainto hav;
    private TextArea havArea = new TextArea();
    
    
    /**
     * Tekee tarvittavat alustukset
     */
    protected void alusta() {
        areaHavainnot.setContent(havArea);
        
        areaHavainnot.setFitToHeight(true);
        areaHavainnot.setFitToWidth(true);

        choiceHav.clear();
        choiceHav.addSelectionListener(e -> naytaHavainto());
    }
    
    
    /**
     *  Luo uuden tyhjän lajin tietojen syöttämiseen
     */
    public void uusiLaji() {
        Havainto hava = choiceHav.getSelectedObject();
        if ( hava == null) return; 
        Laji otus = new Laji();
        otus.rekisterointi();
        otus.vastaa(hava.getID());
        paivakirja.lisaa(otus);
        hae(hava.getID());
    }
    
    
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
    /*
    private void avaa(String tiedosto, String mitaTehdaan) {
        ModalController.showModal(PaivakirjaGUIController.class.getResource(tiedosto), mitaTehdaan, null, "");
    }
    */
    
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
    /*
    private void virheilmoitus() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    */
    
    /**
     * Uuden havainnon lisääminen
     */
    protected void uusiHavainto() {
        hav = new Havainto();
        hav.vastaa();
        hav.rekisterointi();
        Laji valilaji = new Laji();
        valilaji.vastaa(0);
        valilaji.rekisterointi();
        
        try {
            paivakirja.lisaa(hav);
            paivakirja.lisaa(valilaji);
        } catch (TilaException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(hav.getID());
    }
    


    /**
     * @param id halutun havainnon id
     */
    protected void hae(int id) {
        choiceHav.clear();
        int index = 0;
        for (int i = 0; i < paivakirja.getLukumaara(); i++) {
            hav = paivakirja.annaHavainto(i);
            if (hav.getID() == id) index = i;
            choiceHav.add(""+ i + " " + hav.getLaji(), hav);
        }
        choiceHav.setSelectedIndex(index);
        
    }
    
    /**
     *  Tulostetaan havainnot näyttöön
     */
    
    protected void naytaHavainto() {
        hav = choiceHav.getSelectedObject();
        
        
        if (hav == null) return;
        havArea.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(havArea)) {
                hav.tulosta(os);
                Laji haluttu = paivakirja.annaLaji(hav);
                haluttu.tulosta(os);
            }
    }
    
    
    /**
     * Tulostetaan havainto
     * @param os Mihin tulostetaan
     * @param havainto Tulostettava havainto
     */
    public void tulosta(PrintStream os, final Havainto havainto) {
        os.println("---------");
        havainto.tulosta(os);
        
        Laji laji = this.paivakirja.annaLaji(havainto);
        laji.tulosta(os);
        os.println("---------");
    }

    
    
    /**
     * Asetetaan päiväkirja käyttöön
     * @param paivakirja Päiväkirja jota käytetään
     */
    public void setPaivakirja(Paivakirja paivakirja) {
        this.paivakirja = paivakirja;
        naytaHavainto();
    }
    
    
    /**
     * Luetaan tiedot tiedostosta / TODO: !!
     * @param nimi Tiedosto, josta tiedot luetaan
     */
    protected void lueTied(String nimi) {
        String tiedosto = nimi;
        setTitle("Havaintopäiväkirja - " + tiedosto);
        String virhe = "Ei osata vielä";
        Dialogs.showMessageDialog(virhe);
    }
    
    

    private void setTitle(String nimi) {
        ModalController.getStage(textHae).setTitle(nimi);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }


}