/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import igrzyska.medale.structures.Zawodnik;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Marcin
 */
public class FXMLDocumentController implements Initializable {
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    private TableArray osobyTable;
    
    @FXML
    private Button dMedalButton;
    @FXML
    private ChoiceBox cbDyscyplina;
    @FXML
    private Button buttonNZawodnik;
    @FXML
    private Button buttonNKraj;
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
    private ImageView iGold;
    @FXML
    private ImageView iSilver;
    @FXML
    private ImageView iBronze;
    @FXML
    private ListView goldList;
    @FXML
    private ListView silverList;
    @FXML
    private ListView bronzeList;
    
    
    
    private ObservableList<String> dyscyplinyList;
    private ObservableList<String> krajeList;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        igrzyska.setMainWindow(this);
        krajeList = FXCollections.observableArrayList(igrzyska.getCountries());
        countries.setItems(krajeList);
        
        File file = new File("src/gold.png");
        Image image = new Image(file.toURI().toString());
        iGold.setImage(image);
        
        file = new File("src/silver.png");
        image = new Image(file.toURI().toString());
        iSilver.setImage(image);
        
        file = new File("src/bronze.png");
        image = new Image(file.toURI().toString());
        iBronze.setImage(image);
        
        countries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                String dysc = dyscyplina.getValue().toString();
                igrzyska.getSelectedStuff().setKraj(newValue);
                osobyTable = igrzyska.getZawodnicy(newValue, igrzyska.getSelectedStuff().getDyscyplina());
                ObservableList<String> i = FXCollections.observableArrayList(osobyTable.getArray());
                osoby.setItems(i);
            }
        });
        
        dyscyplinyList = FXCollections.observableArrayList(igrzyska.getDyscypliny());
        cbDyscyplina.setItems(dyscyplinyList);
        cbDyscyplina.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                igrzyska.getSelectedStuff().setDyscyplina(dyscyplinyList.get(newValue.intValue()));
                refreshView();
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
      
        dMedalButton.setOnAction((event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("FXMLMedal.fxml"));
                /* 
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Nowy medal");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });   
        
        buttonNZawodnik.setOnAction((event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("FXMLNowyZawodnik.fxml"));
                /* 
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Nowy Zawodnik");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        }); 
        setMedaleList();
        refreshView();
    }  
    
    public void refreshView(){
        krajeList = FXCollections.observableArrayList(igrzyska.getCountries());
        countries.setItems(krajeList);
        
        osobyTable = igrzyska.getZawodnicy(igrzyska.getSelectedStuff().getKraj(), igrzyska.getSelectedStuff().getDyscyplina());
        ObservableList<String> i = FXCollections.observableArrayList(osobyTable.getArray());
        osoby.setItems(i);
        setMedaleList();
    }
    
    public void setMedaleList(){
        goldList.setItems(getMedaleArray(igrzyska.getSelectedStuff().getDyscyplina(), "ZŁOTO"));
        silverList.setItems(getMedaleArray(igrzyska.getSelectedStuff().getDyscyplina(), "SREBRO"));
        bronzeList.setItems(getMedaleArray(igrzyska.getSelectedStuff().getDyscyplina(), "BRĄZ"));
    }
    
    private ObservableList<Integer> getMedaleArray(String dysc, String kolor){
        ArrayList<Integer> list = new ArrayList<>();
        for (String kraj : igrzyska.getKrajList()){
            list.add(igrzyska.countMedale(kraj, dysc, kolor));
        }
        return FXCollections.observableArrayList(list);
    }
    
}
