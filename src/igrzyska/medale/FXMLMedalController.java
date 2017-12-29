/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLMedalController implements Initializable {
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    @FXML
    private TextField cbDysc;
    @FXML
    private TextField cbZloto;
    @FXML
    private TextField cbSrebro;
    @FXML
    private TextField cbBraz;
            
    private int zlotoId = 0;
    private int srebroId = 0;
    private int brazId = 0;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TextFields.bindAutoCompletion(cbDysc, igrzyska.getDyscypliny());
        TextFields.bindAutoCompletion(cbZloto, igrzyska.getOsoby("zawodnik"));
        TextFields.bindAutoCompletion(cbSrebro, igrzyska.getOsoby("zawodnik"));
        TextFields.bindAutoCompletion(cbBraz, igrzyska.getOsoby("zawodnik"));
    }   
    
    @FXML
    private void handleOkButton() {
        String[] parts = cbZloto.getText().split("\\,");
        zlotoId = Integer.parseInt(parts[0]);
        
        parts = cbSrebro.getText().split("\\,");
        srebroId = Integer.parseInt(parts[0]);
                
        parts = cbBraz.getText().split("\\,");
        brazId = Integer.parseInt(parts[0]);
        
        if(zlotoId != 0 && srebroId != 0 && brazId !=0){
            igrzyska.dodajMedal("ZŁOTO", 0, zlotoId, cbDysc.getText(), null);
            igrzyska.dodajMedal("SREBRO", 0, srebroId, cbDysc.getText(), null);
            igrzyska.dodajMedal("BRĄZ", 0, brazId, cbDysc.getText(), null);
            igrzyska.getMainWindow().refreshView();
            Stage stage = (Stage) cbSrebro.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void resetZloto() {
        zlotoId = 0;
        System.out.println("Reset gold");
    }
    @FXML
    private void resetSrebro() {
        srebroId = 0;
    }
    @FXML
    private void resetBraz() {
        brazId = 0;
    }
    
    
}
