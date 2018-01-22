/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    
    @FXML
    protected ComboBox cbDzien;
    @FXML
    protected ComboBox cbMiesiac;
    @FXML
    protected ComboBox cbRok;
    
    
    protected IgrzyskaSingleton igrzyska;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setItems();
        igrzyska = IgrzyskaSingleton.getInstance();
        tKraj.setText(igrzyska.getSelectedStuff().getKraj());
        tDyscyplina.setText(igrzyska.getSelectedStuff().getDyscyplina());
        bDodaj.setOnAction((event) -> {
            if(!igrzyska.existDyscyplina(tDyscyplina.getText()))
                igrzyska.dodajDyscypline(tDyscyplina.getText(), null, null);
            
            String data;
            
            if(cbDzien.getSelectionModel().isEmpty() ||
                    cbMiesiac.getSelectionModel().isEmpty() ||
                    cbRok.getSelectionModel().isEmpty())
                data = "";
            else
                data = cbDzien.getSelectionModel().getSelectedItem().toString() + "-"
                    +cbMiesiac.getSelectionModel().getSelectedItem().toString() + "-"
                    +cbRok.getSelectionModel().getSelectedItem().toString();
            
            boolean b = igrzyska.dodajZawodnikaProcedure(tImie.getText(), tNazwisko.getText(), data, 0, tKraj.getText(),
                    tDyscyplina.getText(), zespol.isSelected()?tKraj.getText():null, 0);
            
            if(b){
                igrzyska.getMainWindow().refreshView();
                Stage stage = (Stage) bDodaj.getScene().getWindow();
                stage.close();
            }
        }); 
        
        TextFields.bindAutoCompletion(tDyscyplina, igrzyska.getDyscypliny());
        TextFields.bindAutoCompletion(tKraj, igrzyska.getKrajList());
        
    }  
    
    protected void setItems(){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=2005; i>1950; i--){
            list.add(i);
        }
        cbRok.setItems(FXCollections.observableArrayList(list));
        list = new ArrayList<>();
        for(int i=1; i<13; i++){
            list.add(i);
        }
        cbMiesiac.setItems(FXCollections.observableArrayList(list));
        list = new ArrayList<>();
        for(int i=1; i<32; i++){
            list.add(i);
        }
        cbDzien.setItems(FXCollections.observableArrayList(list));
    }
    
}
