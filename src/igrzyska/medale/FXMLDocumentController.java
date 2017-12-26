/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import igrzyska.medale.structures.Zawodnik;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author Marcin
 */
public class FXMLDocumentController implements Initializable {
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    private TableArray osobyTable;
    
    @FXML
    private ChoiceBox dyscyplina;
    @FXML
    private ListView osoby;
    @FXML 
    private ListView countries;
    @FXML 
    private Label imie;
    @FXML 
    private Label nazwisko;
    @FXML 
    private Label data;
    @FXML 
    private Label rating;
    @FXML 
    private Label kraj;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> items = FXCollections.observableArrayList(igrzyska.getCountries());
        countries.setItems(items);
        
        countries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                String dysc = dyscyplina.getValue().toString();
                osobyTable = igrzyska.getZawodnicy(newValue);
                ObservableList<String> i = FXCollections.observableArrayList(osobyTable.getArray());
                osoby.setItems(i);
            }
        });
        
        osoby.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    int i = osoby.getSelectionModel().getSelectedIndex();
                    Zawodnik z = igrzyska.getZawodnik(osobyTable.getId().get(i));
                    imie.setText(z.getImie());
                    nazwisko.setText(z.getNazwisko());
                    data.setText(z.getDataUr());
                    rating.setText(Float.toString(z.getRating()));
                    kraj.setText(z.getKraj());
                }
            }
        });
        
    }    
    
}
