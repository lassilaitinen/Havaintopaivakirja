package fxPaivakirja;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * 
 * 
 * @author lassi
 * @version 16.2.2022
 */
public class AloitusController implements ModalControllerInterface<String> {
    

    @FXML private TextField textNimi;
    private String vastaus = null;

    @FXML void handleEi() {
        sulje();
    }

    @FXML void handleOK() {
        avaa();
    }

    //----------------------
    
    /**
     * avataan pääikkuna
     */
    private void avaa() {
        ModalController.showModal(AloitusController.class.getResource("PaivakirjaGUIView.fxml"), "Avataan pääikkuna", null, "lajit");
    }
    
    
    /**
     * aliohjelma sulkemista varten
     */
    private void sulje() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }

    @Override
    public String getResult() {
        return vastaus;
    }

    
    @Override
    public void setDefault(String oletus) {
        textNimi.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textNimi.requestFocus();
    }
    
}
