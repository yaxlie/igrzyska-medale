/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLMedalController implements Initializable {
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    private boolean zespoly;
    private ImageView ivTeam;
    private ImageView ivUser;
    
    @FXML
    private TextField cbDysc;
    @FXML
    private TextField cbZloto;
    @FXML
    private TextField cbSrebro;
    @FXML
    private TextField cbBraz;
    @FXML
    private TextField cbZlotoZ;
    @FXML
    private TextField cbSrebroZ;
    @FXML
    private TextField cbBrazZ;
    @FXML
    private Button changeZZButton;
            
    private int zlotoId = 0;
    private int srebroId = 0;
    private int brazId = 0;
    
    private static final String hintZaw = "(podaj zawodnika) ";
    private static final String hintZesp = "(podaj zespół) ";
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zespoly = false;
        ivTeam = assignIV(changeZZButton, "src/team.png");
        ivUser = assignIV(changeZZButton, "src/user.png");
        
        refresh();
        
        changeZZButton.setGraphic(ivTeam);
        
        changeZZButton.setOnAction((event) -> {
            zespoly = !zespoly;
            changeZZButton.setGraphic(!zespoly? ivTeam: ivUser);
            
            refresh();
        
        }); 

        TextFields.bindAutoCompletion(cbDysc, igrzyska.getDyscypliny());
        TextFields.bindAutoCompletion(cbZloto, igrzyska.getOsoby("zawodnik"));
        TextFields.bindAutoCompletion(cbSrebro, igrzyska.getOsoby("zawodnik"));
        TextFields.bindAutoCompletion(cbBraz, igrzyska.getOsoby("zawodnik"));
        
        TextFields.bindAutoCompletion(cbZlotoZ, igrzyska.getZespoly().getArray());
        TextFields.bindAutoCompletion(cbSrebroZ, igrzyska.getZespoly().getArray());
        TextFields.bindAutoCompletion(cbBrazZ, igrzyska.getZespoly().getArray());
    }   
    
    @FXML
    private void handleOkButton() {
        String[] parts;
        try{
            if(cbZloto.getText() != ""){
                parts = zespoly?cbZlotoZ.getText().split("\\,"): cbZloto.getText().split("\\,");
                zlotoId =  Integer.parseInt(parts[0]);
            }
        }catch(Exception e){}

        try{
            if(cbSrebro.getText() != ""){
                parts = zespoly?cbSrebroZ.getText().split("\\,"): cbSrebro.getText().split("\\,");
                srebroId = Integer.parseInt(parts[0]);
            }
        }
            catch(Exception e){}
            
        try{
            if(cbBraz.getText() != ""){
                parts = zespoly?cbBrazZ.getText().split("\\,"): cbBraz.getText().split("\\,");
                brazId = Integer.parseInt(parts[0]);
            }
        }
        catch(Exception e){}
        
        
        if(!zespoly){
            if(!igrzyska.existDyscyplina(cbDysc.getText()))
                igrzyska.dodajDyscypline(cbDysc.getText(), null, null);
            if(igrzyska.getZawodnik(zlotoId)!=null)
                igrzyska.dodajMedal("ZŁOTO", 0, zlotoId, cbDysc.getText(), null);
            if(igrzyska.getZawodnik(srebroId)!=null)
                igrzyska.dodajMedal("SREBRO", 0, srebroId, cbDysc.getText(), null);
            if(igrzyska.getZawodnik(brazId)!=null)
                igrzyska.dodajMedal("BRĄZ", 0, brazId, cbDysc.getText(), null);
        }
        else{
            if(!igrzyska.existDyscyplina(cbDysc.getText()))
                igrzyska.dodajDyscypline(cbDysc.getText(), null, null);
            if(igrzyska.getZespol(zlotoId)!=null)
                igrzyska.dodajMedal("ZŁOTO", zlotoId, 0, cbDysc.getText(), null);
            if(igrzyska.getZespol(srebroId)!=null)
                igrzyska.dodajMedal("SREBRO", srebroId, 0, cbDysc.getText(), null);
            if(igrzyska.getZespol(brazId)!=null)
                igrzyska.dodajMedal("BRĄZ", brazId, 0, cbDysc.getText(), null);
        }
        
        igrzyska.getMainWindow().refreshView();
        Stage stage = (Stage) cbSrebro.getScene().getWindow();
        stage.close();
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
    
    private ImageView assignIV(Button button, String imagePath){
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(button.getMinHeight()-5);
        iv.setFitWidth(button.getMinWidth()-5);
        return iv;
    }
    
    private void refresh(){
            cbZloto.setVisible(!zespoly);
            cbSrebro.setVisible(!zespoly);
            cbBraz.setVisible(!zespoly);
            
            cbZlotoZ.setVisible(zespoly);
            cbSrebroZ.setVisible(zespoly);
            cbBrazZ.setVisible(zespoly);
    }
}
