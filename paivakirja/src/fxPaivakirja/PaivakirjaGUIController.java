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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    @FXML private Button buttonUusiH;
    @FXML private ComboBoxChooser<Havainto> choiceHav;
    @FXML private Menu menuApua;
    @FXML private Menu menuMuokkaa;
    @FXML private MenuItem menuMuokkaaH;
    @FXML private MenuItem menuMuokkaaL;
    @FXML private Menu menuTiedosto;
    @FXML private MenuItem menuTallenna;
    @FXML private MenuItem menuTulosta;
    @FXML private TextField textHae;
    @FXML private ScrollPane areaHavainnot;
    @FXML private GridPane gridHavainnot;
    @FXML private GridPane gridLajit;

    // Havainnon tietokentät
    @FXML private TextField editLaji;
    @FXML private TextField editLisat;
    @FXML private TextField editMaara;
    @FXML private TextField editPaikka;
    @FXML private TextField editPvm;
    @FXML private TextField editPyydettiinko;
    
    // Lajin tietokentät
    @FXML private TextField editNimi;
    @FXML private TextField editLuokka;
    @FXML private TextField editUhanalaisuus;
    @FXML private TextField editKoko;
    @FXML private TextField editTuntomerkit;
    @FXML private TextField editRavinto;
    @FXML private TextField editMuut;

    @FXML void handleTallenna() {
        tallenna();
    }
    
    @FXML void handleTulosta() {
        //TODO tulostus tiedostolle
        virheilmoitus();
    }
    
    @FXML void handlePoistaH() {
        poistaHavainto();
        tallenna();
    }

    
    @FXML void keyReleased() {
        //haku();
        hae(0);
    }
    
    
    @FXML void handeApua() {
        avustus();
    }
    
    @FXML private void handleMuokkaaL() {
        muokkaaLaji(1);
        tallenna();
    }
    
    
    @FXML private void handleMuokkaaH() {
        muokkaaHavainto(1);
        tallenna();
    }
    
    
    
    @FXML void handleUusiH() {
        uusiHavainto();
        tallenna();
        //avaa("HavaintoDialogView.fxml", "Havainnon lisäys");
    }

    @FXML void handleUusiLaji() {
        uusiLaji();
        tallenna();
        //avaa("LajiDialogView.fxml", "Lajin lisäys");
    }
    
    @FXML void handleHaku() {
        hae(0);
    }
    
    

    
    //--------------------------------------
    
    private Paivakirja paivakirja;
    private Havainto hav;
    private TextField[] edits = {editLaji, editPaikka, editMaara, editPvm, editLisat, editPyydettiinko};
    private TextField[] editsLajit = {editNimi, editLuokka, editUhanalaisuus, editKoko, editTuntomerkit, editRavinto, editMuut};
    private int kentta = 0;
    //private TextArea havArea = new TextArea();
    
    
    /**
     * Tekee tarvittavat alustukset
     */
    protected void alusta() {
 
        choiceHav.clear();
        
        if (hav == null) hav = new Havainto();
        choiceHav.addSelectionListener(e -> naytaHavainto());
        choiceHav.addSelectionListener(e -> naytaHavainto());
        TextField[] editit = HavaintoDialogController.luoKentat(gridHavainnot, 7);
        edits = editit;
        TextField[] edititLaji = LajiDialogController.luoKentat(gridLajit);
        editsLajit = edititLaji;
        for (TextField edit : edits)
            if (edit != null) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> { if (e.getClickCount() > 1) muokkaaHavainto(getIDkentta(e.getSource(),0));});
                edit.focusedProperty().addListener((a, o, n) -> kentta = getIDkentta(edit,kentta));
            }
        for (TextField editti : editsLajit)
            if (editti != null) {
                editti.setEditable(false);
                editti.setOnMouseClicked(e -> { if (e.getClickCount() > 1) muokkaaLaji(getIDkenttaL(e.getSource(),0));});
                editti.focusedProperty().addListener((a, o, n) -> kentta = getIDkenttaL(editti,kentta));
            }
        
    }
    
    
    /**
     * Etsitään halutun kentän indexi
     * @param edit Kenttä, jonka indeksi etsitään
     * @param oletus oletus mikä aktiivisena
     * @return Haluttu kenttä
     */
    public int getIDkentta(Object edit, int oletus) {
        return HavaintoDialogController.getIDkentta(edit, oletus);
    }
    
    
    /**
     * Etsitään halutun kentän indexi
     * @param edit Kenttä, jonka indeksi etsitään
     * @param oletus oletus mikä aktiivisena
     * @return Haluttu kenttä
     */
    public int getIDkenttaL(Object edit, int oletus) {
        return LajiDialogController.getIDkentta(edit, oletus);
    }
    
    
    /**
     *  Luo uuden tyhjän lajin tietojen syöttämiseen
     */
    public void uusiLaji() {
        if ( hav == null) return; 
        Laji otus = new Laji();
        otus.rekisterointi();
        otus.vastaa(hav.getID());
        try {
            paivakirja.lisaa(otus);
        } catch (Exception e) {
           Dialogs.showMessageDialog("Ongelmia lisäämisessä" + e.getMessage());
        }
        hae(hav.getID());
        tallenna();
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
     * @return true jos onnistui
     */
    public boolean avaa() {
        lueTied("paivakirja");
        return true;
    }
    
    
    private void tallenna() {
        try {
            paivakirja.tallenna();
        } catch (TilaException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }

    
    /**
     * Aliohjelma havainnon poistamista varten
     */
    private void poistaHavainto() {
        Havainto h = hav;
        if (h == null) return;
        if ( !Dialogs.showQuestionDialog("Poistaminen", "Poistetaanko havainto: " + h.getLaji(), "Kyllä", "Ei") )return;
            paivakirja.poista(h);
            int index = choiceHav.getSelectedIndex();
            hae(0);
            choiceHav.setSelectedIndex(index);
        tallenna();
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
        try {
            Havainto uusi = new Havainto();
            uusi = HavaintoDialogController.kysyHav(null, uusi,1);
            if (uusi == null) return;
            uusi.rekisterointi();
            paivakirja.lisaa(uusi);
            hae(uusi.getID());
            tallenna();
        } catch (TilaException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        
    }
    
    
    private void muokkaaHavainto(int k) {
        if (hav == null) return;
        try {
            Havainto h;
            h = HavaintoDialogController.kysyHav(null, hav.clone(), k);
            if (h == null) return;
            paivakirja.korjaaOrLisaa(h);
            hae(h.getID());
            tallenna();
        } catch (CloneNotSupportedException | TilaException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
        
    }
    
    
    private void muokkaaLaji(int k) {
        Havainto tamaHavainto = choiceHav.getSelectedObject();
        Laji tama = paivakirja.annaLaji(tamaHavainto);
        try {
            Laji l = LajiDialogController.kysyLajia(null, tama.clone(), k);
            if (l == null) return;
            
            paivakirja.korjaaOrLisaa(l);
            hae(l.getID());
            tallenna();
        } catch (CloneNotSupportedException | TilaException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
        
    }


    /**
     * @param id halutun havainnon id
     */
    protected void hae(int id) {
        int idnro = id;
        if (idnro == 0) {
            Havainto tama = choiceHav.getSelectedObject();
            if (tama != null) idnro = tama.getID();
        }
        choiceHav.clear();
        String hakuteksti = textHae.getText();
        int index = 0;
        int apu = 0;
        for (int i = 0; i < paivakirja.getLukumaara(); i++) {
            Havainto h = paivakirja.annaHavainto(i);
            if (!h.getLaji().contains(hakuteksti)) continue;
            //if (!h.getPvm().contains(hakuteksti)) continue; TODO: Haku päivämäärän perusteella
            if (h.getID() == id) index = apu;
            choiceHav.add(""+ h.getPvm() + " " + h.getLaji(h), h);
        }
        choiceHav.setSelectedIndex(index);
        
    }
    
    
    /**
     *  Tulostetaan havainnot näyttöön
     */
    protected void naytaHavainto() {
        hav = choiceHav.getSelectedObject();

        if (hav == null) return;
        
        HavaintoDialogController.naytaHav(edits, hav);
        Laji haluttu = paivakirja.annaLaji(hav);
        naytaLaji(haluttu);
        
    }
    
    
    /**
     *  Tulostetaan laji näyttöön
     * @param haluttu Laji joka halutaan näyttää
     */
    protected void naytaLaji(Laji haluttu) {
        LajiDialogController.naytaLaji(editsLajit, haluttu);
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
     * Luetaan tiedot tiedostosta 
     * @param nimi Tiedosto, josta tiedot luetaan
     * @return virhe jos ei onnistu, muutoin null
     */
    protected String lueTied(String nimi) {
        String tiedosto = nimi;
        setTitle("Havaintopäiväkirja - " + tiedosto);
        try {
            paivakirja.lueTiedosto(nimi);
            hae(0);
            return null;
        } catch (TilaException e) {
            String virhe = "Ei osata vielä";
            Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    

    private void setTitle(String nimi) {
        ModalController.getStage(textHae).setTitle(nimi);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }


}