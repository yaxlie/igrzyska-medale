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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLMedalController implements Initializable {
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    @FXML
    private ComboBox cbDysc;
    @FXML
    private ComboBox cbZloto;
    @FXML
    private ComboBox cbSrebro;
    @FXML
    private ComboBox cbBraz;
            
    private int zlotoId = 0;
    private int srebroId = 0;
    private int brazId = 0;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbDysc.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                                            String oldValue, String newValue) {
                
                cbDysc.setItems(FXCollections.observableArrayList(igrzyska.getDyscypliny(newValue)));
                cbDysc.show();
            }
        });
        
        cbZloto.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                                            String oldValue, String newValue) {
                
                if(!newValue.contains(",")){
                    cbZloto.setItems(FXCollections.observableArrayList(igrzyska.getZawodnik(newValue, "nazwisko")));
                    cbZloto.show();
                    zlotoId = 0;
                }
            }
        });
        
        cbSrebro.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                                            String oldValue, String newValue) {
                if(!newValue.contains(",")){
                    cbSrebro.setItems(FXCollections.observableArrayList(igrzyska.getZawodnik(newValue, "nazwisko")));
                    cbSrebro.show();
                    srebroId = 0;
                }
            }
        });
        
        cbBraz.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                                            String oldValue, String newValue) {
                if(!newValue.contains(",")){
                    cbBraz.setItems(FXCollections.observableArrayList(igrzyska.getZawodnik(newValue, "nazwisko")));
                    cbBraz.show();
                    brazId = 0;
                }
            }
        });
        
        cbZloto.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                String[] parts = newValue.split("\\,");
                zlotoId = Integer.parseInt(parts[0]);
            }    
        });
        cbSrebro.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                String[] parts = newValue.split("\\,");
                srebroId = Integer.parseInt(parts[0]);
            }    
        });
        cbBraz.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                String[] parts = newValue.split("\\,");
                brazId = Integer.parseInt(parts[0]);
            }    
        });
    }   
    
    @FXML
    private void handleOkButton() {
        if(zlotoId != 0 && srebroId != 0 && brazId !=0){
            igrzyska.dodajMedal("ZŁOTO", 0, zlotoId, cbDysc.getSelectionModel().getSelectedItem().toString(), null);
            igrzyska.dodajMedal("SREBRO", 0, srebroId, cbDysc.getSelectionModel().getSelectedItem().toString(), null);
            igrzyska.dodajMedal("BRĄZ", 0, brazId, cbDysc.getSelectionModel().getSelectedItem().toString(), null);
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
