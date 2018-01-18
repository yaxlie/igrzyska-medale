/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layouts;

import igrzyska.medale.TableArray;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLMedaleController implements Initializable {

    private TableArray medaleTable;
    
//    public FXMLMedaleController(TableArray medaleTable){
//        super();
//        this.medaleTable = medaleTable;
//    }
    
    @FXML
    private Button removeButton;
    
    @FXML
    private ListView list;
    
    @FXML
    private Label label;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        ArrayList<String> mList = new ArrayList<>();
//        for (String s : medaleTable.getArray()){
//            mList.add(s);
//        } 
//        list.setItems(FXCollections.observableArrayList(mList));
    }    

    public TableArray getMedaleTable() {
        return medaleTable;
    }

    public void setMedaleTable(TableArray medaleTable) {
        this.medaleTable = medaleTable;
        refresh();
    }

    private void refresh(){
        ArrayList<String> mList = new ArrayList<>();
        for (String s : medaleTable.getArray()){
            mList.add(s);
        } 
        list.setItems(FXCollections.observableArrayList(mList));
    }

    public Label getLabel() {
        return label;
    }
    
}
