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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import paivakirja.Laji;

/**
 * @author lassi
 * @version 20.4.2022
 *
 */
public class LajiDialogController implements ModalControllerInterface<Laji>, Initializable{
    
    @FXML private TextField editLaji;
    @FXML private TextField editLuokka;
    @FXML private TextField editUhanalaisuus;
    @FXML private TextField editKoko;
    @FXML private TextField editTuntomerkit;
    @FXML private TextField editRavinto;
    @FXML private TextField editMuut;
    
    @FXML private GridPane gridLajit;
    @FXML private Label labelVirhe;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //
    }
    
    
    @Override
    public Laji getResult() {
       return tamaLaji;
    }
    
    
    @Override
    public void handleShown() {
        alusta();
        naytaLaji(editsLaji, tamaLaji);
    }
    
    
    @Override
    public void setDefault(Laji oletus) {
        tamaLaji = oletus;
    }
    
    
    @FXML private void handleOK() {
        if (tamaLaji != null && tamaLaji.getLaji().trim().equals("")) {
            naytaVirhe("Ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    
    @FXML private void handleCancel() {
        tamaLaji = null;
        ModalController.closeStage(labelVirhe);
    }

    //-----------------------------------------------------------------------
    private TextField[] editsLaji;
    private Laji tamaLaji; 
    private static Laji apu = new Laji();
    
    
    private void alusta() {
        editsLaji = luoKentat(gridLajit);
        for (TextField edit : editsLaji) {
            if (edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutos((TextField)(e.getSource())));
        }
    }
    
    
    private void kasitteleMuutos(TextField edit) {
        if (tamaLaji == null) tamaLaji = new Laji();
        String teksti = edit.getText();
        int k = getIDkentta(edit, 1);
        String virhe = tamaLaji.aseta(k, teksti);
        
        if (virhe != null) {
            Dialogs.setToolTipText(edit, virhe);
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, "");
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
     * @param editsLaji tekstikentät taulukkona
     * @param tama näytettävä havainto
     */
    public static void naytaLaji(TextField[] editsLaji, Laji tama) {
        if (tama == null) return;
        for (int i = 0; i < 7; i++) {
            editsLaji[i].setText(tama.anna(i));
            }
        }
    
    
    /**
     * Asetetaan kentät GridPaneen
     * @param gridLajit GridPane johon kentät asetetaan
     * @return tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridLajit) {
        gridLajit.getChildren().clear();
        TextField[] edits = new TextField[8];
        
        for (int i = 0; i < 8; i++) {
            Label label = new Label(apu.getTeksti(i));
            gridLajit.add(label, 0, i);
            
            TextField edit = new TextField();
            edits[i] = edit;
            edit.setId("e"+i);
            if (i != 0) gridLajit.add(edit, 1, i);
            

        }
        //editsLaji = edits;
        return edits;
    }
    
    
    /**
     * @param modalityStage mille modaalisia (null -> modaalisia sovellukselle)
     * @param tama oletuslaji joka näytetään oletuksena
     * @param k mikä kenttä aktiiviseksi //TODO
     * @return täytetty dialogi, null jos painetaan "peruuta"
     */
    public static Laji kysyLajia(Stage modalityStage, Laji tama, @SuppressWarnings("unused") int k) {
        return ModalController.showModal(PaivakirjaGUIController.class.getResource("LajiDialogView.fxml"), "Laji", modalityStage, tama);
    }

}
