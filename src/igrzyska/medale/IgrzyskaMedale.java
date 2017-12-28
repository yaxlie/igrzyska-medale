/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Marcin
 */
public class IgrzyskaMedale extends Application {
    IgrzyskaSingleton igrzyska;
    
    @Override
    public void start(Stage stage) throws Exception {   
        
        igrzyska = IgrzyskaSingleton.getInstance();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));  
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Tokio 2020");
        stage.show();   
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
     
}
