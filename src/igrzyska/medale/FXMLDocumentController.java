/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igrzyska.medale;

import igrzyska.medale.structures.Zawodnik;
import igrzyska.medale.structures.Zespol;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layouts.FXMLLoginController;
import layouts.FXMLMedaleController;
import layouts.FXMLModZawodnikController;

/**
 *
 * @author Marcin
 */
public class FXMLDocumentController implements Initializable {
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    private TableArray osobyTable;
    private TableArray zespolyTable;
    private boolean showZespoly;
    private ImageView ivTeam;
    private ImageView ivUser;
    private ImageView ivDelete;

    
    @FXML
    private Label labelError;
    @FXML
    private Label labelInfo;
    
    @FXML
    private Button removeKraj;
    @FXML
    private Button saveButton;
    @FXML
    private Button rollbackButton;
    @FXML
    private Button removeDyscyplina;
    @FXML
    private Button removeZawodnik;
    @FXML
    private Button modButton;
    @FXML
    private Button dMedalButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button changeZZButton;
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
    private Button medaleButton;
    @FXML 
    private ListView countries;
    @FXML 
    private Label imie;
    @FXML 
    private Label nazwisko;
    @FXML 
    private Label data;
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
        setKrajeList();
        countries.setItems(krajeList);
        showZespoly = false;
        
        File file = new File("res\\gold.png");
        Image image = new Image(file.toURI().toString());
        iGold.setImage(image);
        
        file = new File("res\\silver.png");
        image = new Image(file.toURI().toString());
        iSilver.setImage(image);
        
        file = new File("res\\bronze.png");
        image = new Image(file.toURI().toString());
        iBronze.setImage(image);
        
        ImageView iv = assignIV(refreshButton, "res\\refresh.png");
        refreshButton.setGraphic(iv);
        
        ivTeam = assignIV(changeZZButton, "res\\team.png");
        ivUser = assignIV(changeZZButton, "res\\user.png");
        
        iv = assignIV(removeKraj, "res\\delete.png");
        removeKraj.setGraphic(iv);
        removeKraj.setOnAction((event) -> {
            igrzyska.usunKraj(igrzyska.getSelectedStuff().getKraj());
            refreshView();
        }); 
        
        iv = assignIV(removeDyscyplina, "res\\delete.png");
        removeDyscyplina.setGraphic(iv);
        
        iv = assignIV(removeDyscyplina, "res\\edit.png");
        modButton.setGraphic(iv);
        
        modButton.setOnAction((event) -> {
            try {     
                    Zawodnik z = igrzyska.getSelectedStuff().getZawodnik();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLNowyZawodnik.fxml"));
                    fxmlLoader.setController(new FXMLModZawodnikController(z));
                    Parent root = (Parent)fxmlLoader.load(); 
                    
                    //FXMLModZawodnikController controller = fxmlLoader.<FXMLModZawodnikController>getController();
                    String zawodnik = igrzyska.getSelectedStuff().getZawodnik().getImie() + " " 
                            +igrzyska.getSelectedStuff().getZawodnik().getNazwisko();
                    
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(zawodnik);
                    stage.show();  
                    stage.show();  
                        
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        
        iv = assignIV(saveButton, "res\\save.png");
        saveButton.setGraphic(iv);
        saveButton.setOnAction((event) -> {
           igrzyska.save();
        });
        
        iv = assignIV(rollbackButton, "res\\rollback.png");
        rollbackButton.setGraphic(iv);
        rollbackButton.setOnAction((event) -> {
           igrzyska.rollback();
           refreshView();
        });
        
        iv = assignIV(removeDyscyplina, "res\\sport.png");
        medaleButton.setGraphic(iv);
        
        iv = assignIV(removeZawodnik, "res\\delete.png");
        removeZawodnik.setGraphic(iv);
        removeZawodnik.setOnAction((event) -> {
            if(!showZespoly)
                igrzyska.usunZawodnika(osobyTable.getId().get(osoby.getSelectionModel().selectedIndexProperty().getValue()));
            else
                igrzyska.usunZespol(zespolyTable.getId().get(osoby.getSelectionModel().selectedIndexProperty().getValue()));
            refreshView();
        }); 
        
        removeDyscyplina.setOnAction((event) -> {
            igrzyska.usunDyscypline(cbDyscyplina.getSelectionModel().getSelectedItem().toString());
            reset();
            refreshView();
        }); 
        
    osoby.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent click) {
            if (click.getClickCount() == 2) {
               //Use ListView's getSelected Item
               int currentItemSelected = osoby.getSelectionModel().getSelectedIndex();
               showmedal();
            }
        }
    });

        
        medaleButton.setOnAction((event) -> {
            showmedal();
        }); 
        
        changeZZButton.setGraphic(ivTeam);
        
        changeZZButton.setOnAction((event) -> {
            showZespoly = !showZespoly;
            refreshView();
        }); 
        
        refreshButton.setOnAction((event) -> {
            reset();
        }); 
        
        countries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                String dysc = dyscyplina.getValue().toString();
                igrzyska.getSelectedStuff().setKraj(newValue);
                setOsobyListView(newValue, igrzyska.getSelectedStuff().getDyscyplina(), showZespoly, false);
            }
        });
        
        dyscyplinyList = FXCollections.observableArrayList(igrzyska.getDyscypliny());
        cbDyscyplina.setItems(dyscyplinyList);
        cbDyscyplina.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try{
                igrzyska.getSelectedStuff().setDyscyplina(dyscyplinyList.get(newValue.intValue()));
                //refreshView();
                }catch(Exception e){}
            }

        });
        
        osoby.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !showZespoly){
                    int i = osoby.getSelectionModel().getSelectedIndex();
                    Zawodnik z = igrzyska.getZawodnik(osobyTable.getId().get(i));
                    imie.setText(z.getImie());
                    nazwisko.setText(z.getNazwisko());
                    data.setText(z.getDataUr()==null?"brak danych": z.getDataUr());
                    kraj.setText(z.getKraj());
                    igrzyska.getSelectedStuff().setZawodnik(z);
                }
                else if(newValue != null && showZespoly){
                    int i = osoby.getSelectionModel().getSelectedIndex();
                    Zespol z = igrzyska.getZespol(zespolyTable.getId().get(i));
                    imie.setText("Rep. : " + z.getKraj());
                    nazwisko.setText(z.getDyscyplina());
                    igrzyska.getSelectedStuff().setZespol(z);
                }
            }
        });
      
        
        iv = assignIV(dMedalButton, "res\\dodajmedal.png");
        dMedalButton.setGraphic(iv);
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
        
        iv = assignIV(buttonNZawodnik, "res\\dodajzawodnika.png");
        buttonNZawodnik.setGraphic(iv);
        buttonNZawodnik.setOnAction((event) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setController(new FXMLNowyZawodnikController());
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
    
    private class Kraj implements Comparable{
        private String kraj;
        private int score;
        public Kraj(String kraj, int score){
            this.kraj = kraj;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public String getKraj() {
            return kraj;
        }
        
        @Override
        public int compareTo(Object k) {
            int comp=((Kraj)k).getScore();
            return comp - this.getScore();
        }
        
    }
    
    private void setKrajeList(){
        ArrayList<Kraj>kList = new ArrayList<>();
        ArrayList<String> kraje = igrzyska.getCountries();
        for(String k : kraje){
            kList.add(new Kraj(k, igrzyska.getKrajScore(k.toUpperCase())));
        }
//        Comparator<Kraj> comparator = Comparator.comparingInt(Kraj::getScore);
//        kList.sort(comparator);

        Collections.sort(kList);
        
        kraje = new ArrayList<>();
        
        for(Kraj k : kList){
            kraje.add(k.getKraj());
        }
        
        krajeList = FXCollections.observableArrayList(kraje);
        
        countries.setItems(krajeList);
    }
    
    private void showmedal(){
        try {     
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/FXMLMedale.fxml"));  
                    
                    Parent root = (Parent)fxmlLoader.load(); 
                    
                    FXMLMedaleController controller = fxmlLoader.<FXMLMedaleController>getController();
                    if(!showZespoly){
                        controller.setMedaleTable(igrzyska.getMedale(igrzyska.getSelectedStuff().getZawodnik().getId()));
                        controller.getLabel().setText(igrzyska.getSelectedStuff().getZawodnik().getImie() + " " 
                            +igrzyska.getSelectedStuff().getZawodnik().getNazwisko());
                    }
                    else{
                        controller.setMedaleTable(igrzyska.getMedale(igrzyska.getSelectedStuff().getZespol().getNumer(),true));
                        controller.getLabel().setText(igrzyska.getSelectedStuff().getZespol().getKraj()+ ": " 
                            +igrzyska.getSelectedStuff().getZespol().getDyscyplina());
                    }
                    
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Medale Zawodnika/Reprezentacji");
                    stage.show();  
                    stage.show();  
                        
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    public void refreshView(){
        setKrajeList();
        
        labelError.setText(igrzyska.getErrorText());
        labelInfo.setText(igrzyska.getInfoText());
        
        countries.setItems(krajeList);
        
        setOsobyListView(igrzyska.getSelectedStuff().getKraj()
                    , igrzyska.getSelectedStuff().getDyscyplina(), showZespoly, false);
        setMedaleList();
        
        dyscyplinyList = FXCollections.observableArrayList(igrzyska.getDyscypliny());
        if(cbDyscyplina.getItems().size() < dyscyplinyList.size())
            cbDyscyplina.setItems(dyscyplinyList);
        
        changeZZButton.setGraphic(!showZespoly? ivTeam: ivUser);
    }
    
    public void setMedaleList(){
        goldList.setItems(getMedaleArray(igrzyska.getSelectedStuff().getDyscyplina(), "ZŁOTO"));
        silverList.setItems(getMedaleArray(igrzyska.getSelectedStuff().getDyscyplina(), "SREBRO"));
        bronzeList.setItems(getMedaleArray(igrzyska.getSelectedStuff().getDyscyplina(), "BRĄZ"));
    }
    
    private ObservableList<Integer> getMedaleArray(String dysc, String kolor){
        ArrayList<Integer> list = new ArrayList<>();
        for (String kraj : krajeList){
            list.add(igrzyska.countMedale(kraj, dysc, kolor));
        }
        return FXCollections.observableArrayList(list);
    }
    
    private void setOsobyListView(String kraj, String dyscyplina, boolean zespoly, boolean returnHigher){
        osobyTable = igrzyska.getZawodnicy(kraj, dyscyplina);
        zespolyTable = igrzyska.getZespoly(kraj, dyscyplina);
        ObservableList<String> i;
        
        if(returnHigher){
            i = osobyTable.getArray().size() >= zespolyTable.getArray().size()? 
                FXCollections.observableArrayList(osobyTable.getArray()) 
                :FXCollections.observableArrayList(zespolyTable.getArray());
        }  
        else{ 
            i = zespoly?FXCollections.observableArrayList(zespolyTable.getArray()) 
                :FXCollections.observableArrayList(osobyTable.getArray());
        }
        osoby.setItems(i);
    }
    
    private void reset(){
        igrzyska.setErrorText("");
        igrzyska.setInfoText("");
        
        countries.getSelectionModel().clearSelection();
        cbDyscyplina.getSelectionModel().clearSelection();
        dyscyplinyList = FXCollections.observableArrayList(igrzyska.getDyscypliny());
        cbDyscyplina.setItems(dyscyplinyList);
        osoby.getSelectionModel().clearSelection();
        showZespoly = false;
        igrzyska.setSelectedStuff(new SelectedStuff());
        refreshView();
    }
    
    private ImageView assignIV(Button button, String imagePath){
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(button.getMinHeight()-5);
        iv.setFitWidth(button.getMinWidth()-5);
        return iv;
    }
}
