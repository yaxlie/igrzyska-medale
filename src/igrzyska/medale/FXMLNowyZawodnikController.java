/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLNowyZawodnikController implements Initializable {
    
    @FXML
    private Button bDodaj;
    @FXML
    private TextField tImie;
    @FXML
    private TextField tNazwisko;
    private IgrzyskaSingleton igrzyskaSingleton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        igrzyskaSingleton = IgrzyskaSingleton.getInstance();
        bDodaj.setOnAction((event) -> {
            igrzyskaSingleton.dodajZawodnika(tImie.getText(), tNazwisko.getText(), null, null, null, 
                    igrzyskaSingleton.getSelectedStuff().getDyscyplina(), 
                    igrzyskaSingleton.getSelectedStuff().getKraj(), null);
            igrzyskaSingleton.getMainWindow().refreshView();
            Stage stage = (Stage) bDodaj.getScene().getWindow();
            stage.close();
        }); 
    }    
    
}
