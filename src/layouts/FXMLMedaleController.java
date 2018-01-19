/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layouts;

import igrzyska.medale.IgrzyskaSingleton;
import igrzyska.medale.TableArray;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Marcin
 */
public class FXMLMedaleController implements Initializable {

    private TableArray medaleTable;
    private IgrzyskaSingleton igrzyska = IgrzyskaSingleton.getInstance();
    
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
        ImageView iv = assignIV(removeButton, "src/delete.png");
        removeButton.setGraphic(iv);
        
        removeButton.setOnAction((event) -> {
            int selectedIdx = list.getSelectionModel().getSelectedIndex();
            int id = medaleTable.getId().get(selectedIdx);
            try {
                igrzyska.usunMedal(id);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLMedaleController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
               medaleTable.getArray().remove(selectedIdx);
               medaleTable.getId().remove(selectedIdx);
               refresh(); 
            }
        });
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
    
    private ImageView assignIV(Button button, String imagePath){
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(button.getMinHeight()-5);
        iv.setFitWidth(button.getMinWidth()-5);
        return iv;
    }
    
}
