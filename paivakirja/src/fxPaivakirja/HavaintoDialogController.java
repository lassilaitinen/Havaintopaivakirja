/**
 * 
 */
package fxPaivakirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import paivakirja.Havainto;


/**
 * @author lassi
 * @version 16.4.2022
 *
 */
public class HavaintoDialogController implements ModalControllerInterface<Havainto>, Initializable{
    
    @FXML private TextField editLaji;
    @FXML private TextField editLisat;
    @FXML private TextField editMaara;
    @FXML private TextField editPaikka;
    @FXML private TextField editPvm;
    @FXML private TextField editPyydettiinko;
    
    @FXML private Label labelVirhe;
    @FXML private GridPane gridHavainnot;
    @FXML private GridPane gridLajit;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //alusta();
    }

    
    @Override
    public Havainto getResult() {
        return tamaHav;
    }
 
    
    @Override
    public void handleShown() {
        alusta();
        naytaHav(edits, tamaHav);
    }

    
    @Override
    public void setDefault(Havainto oletus) {
        this.tamaHav = oletus; 
    }
    
    
    @FXML private void handleOK() {
        if (tamaHav != null && tamaHav.getLaji().trim().equals("")) {
            naytaVirhe("Ei saa olla tyhjä");
            return;
        }
        tamaHav.setElain(tamaHav.getLaji());       
        ModalController.closeStage(labelVirhe); 
    }
    
    
    @FXML private void handleCancel() {
        tamaHav = null;
        ModalController.closeStage(labelVirhe);
    }
    
    //--------------------------------------------------------------------------------
    private Havainto tamaHav; 
    private static Havainto apu = new Havainto();
    private TextField[] edits;
    private static int havkentat = 7;
    
    
    private void alusta() {
        edits = luoKentat(gridHavainnot, havkentat);

        for (TextField edit : edits) {
            if (edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutos((TextField)(e.getSource())));                 
        }  
    }
    

    /*
     * Käsitellään muutos havaintoon
     */
    private void kasitteleMuutos(TextField edit) {
        if (tamaHav == null) return;
        
        String teksti = edit.getText();
        int k = getIDkentta(edit, 1);
        String virhe = null; 
        virhe = tamaHav.aseta(k, teksti);
        
        if (virhe != null) {
            Dialogs.setToolTipText(edit, virhe);
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, "Täytä");
            naytaVirhe(virhe);
        } 
    }
    
    
    /**
     * Haetaan kentän id-numero
     * @param obj tutkittava kenttä
     * @param oletus mikä arvo, jos id ei käy
     * @return kentän id
     */
    public static int getIDkentta(Object obj, int oletus) {
        if (!(obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
    
    
    /*
     * Näytetään virhe jos sellainen tulee
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            return;
        }
        labelVirhe.setText(virhe);
    }
    
    /**
     * @param edits tekstikentät taulukkona
     * @param hav näytettävä havainto
     */
    public static void naytaHav(TextField[] edits, Havainto hav) {
        if (hav == null) return;
        for (int i = 0; i < havkentat; i++) {
            edits[i].setText(hav.anna(i));
        }
    }
    
    
    /**
     * Asetetaan kentät GridPaneen
     * @param grid GridPane johon kentät asetetaan
     * @param kentat montako kenttää luodaan
     * @return tekstikentät
     */
    public static TextField[] luoKentat(GridPane grid, int kentat) {
        grid.getChildren().clear();
        TextField[] e = new TextField[kentat];
        
        for (int i = 0; i < kentat; i++) {
            Label label = new Label(apu.getTeksti(i));
            grid.add(label, 0, i);
            
            TextField edit = new TextField();
            e[i] = edit;
            edit.setId("e"+i);
            if (i != 0) grid.add(edit, 1, i);
        }
        return e;
    }

    
    /**
     * @param modalityStage mille modaalisia (null -> modaalisia sovellukselle)
     * @param tama oletushavainto joka näytetään oletuksena
     * @param k mikä kenttä avautuu aktiivisena
     * @return täytetty dialogi, null jos painetaan "peruuta"
     */
    public static Havainto kysyHav(Stage modalityStage, Havainto tama, @SuppressWarnings("unused") int k) {
        //TODO oletuskenttä aktiiviseksi
        return ModalController.showModal(PaivakirjaGUIController.class.getResource("HavaintoDialogView.fxml"), "Havainto", modalityStage, tama);
    }
    
}
