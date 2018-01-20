/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layouts;

import igrzyska.medale.IgrzyskaSingleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import igrzyska.medale.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLLoginController implements Initializable {
    
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    
    @FXML
    private TextField loginField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button button;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        igrzyska = IgrzyskaSingleton.getInstance(); 
        button.setOnAction((event) -> {
             login();
        }); 
    }   
    
    
    @FXML
    private void login(){
            try {
                igrzyska.connect(loginField.getText(), passwordField.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                
                try {
                        Parent root = FXMLLoader.load(getClass().getResource("/igrzyska/medale/FXMLDocument.fxml"));  
                        Scene scene = new Scene(root);
                        
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Tokio 2020");
                        stage.show();  
                        stage.setOnHiding( event -> {igrzyska.disconnect();} );
                        stage = (Stage) button.getScene().getWindow();
                        stage.close();
                        
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
}
