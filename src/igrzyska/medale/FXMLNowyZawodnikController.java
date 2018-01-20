/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLNowyZawodnikController implements Initializable {
    
    @FXML
    protected Button bDodaj;
    @FXML
    protected TextField tImie;
    @FXML
    protected TextField tNazwisko;
    @FXML
    protected TextField tKraj;
    @FXML
    protected TextField tDyscyplina;
    @FXML
    protected TextField tData;
    @FXML
    protected CheckBox zespol;
    
    protected IgrzyskaSingleton igrzyska;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        igrzyska = IgrzyskaSingleton.getInstance();
        tKraj.setText(igrzyska.getSelectedStuff().getKraj());
        tDyscyplina.setText(igrzyska.getSelectedStuff().getDyscyplina());
        bDodaj.setOnAction((event) -> {
            igrzyska.dodajZawodnikaProcedure(tImie.getText(), tNazwisko.getText(), tData.getText(), 0, tKraj.getText(),
                    tDyscyplina.getText(), zespol.isSelected()?tKraj.getText():null, 0);
            igrzyska.getMainWindow().refreshView();
            Stage stage = (Stage) bDodaj.getScene().getWindow();
            stage.close();
        }); 
        
        TextFields.bindAutoCompletion(tDyscyplina, igrzyska.getDyscypliny());
        TextFields.bindAutoCompletion(tKraj, igrzyska.getKrajList());
        
    }    
    
}
