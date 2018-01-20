/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layouts;

import igrzyska.medale.FXMLNowyZawodnikController;
import igrzyska.medale.IgrzyskaSingleton;
import igrzyska.medale.structures.Zawodnik;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 *
 * @author Marcin
 */
public class FXMLModZawodnikController extends FXMLNowyZawodnikController{
    
    private Zawodnik z;
    
    
    
    public FXMLModZawodnikController(){
        super();
        
    }
    public FXMLModZawodnikController(Zawodnik z){
        this.z = z;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        igrzyska = IgrzyskaSingleton.getInstance();
        tImie.setText(z.getImie());
        tNazwisko.setText(z.getNazwisko());
        tKraj.setText(z.getKraj());
        tData.setText(z.getDataUr());
        tDyscyplina.setText(z.getDyscyplina());
        bDodaj.setOnAction((event) -> {
            igrzyska.updateZawodnik(z.getId(), tImie.getText(), tNazwisko.getText(), 
                    zespol.isSelected()?tKraj.getText():null, 0, tDyscyplina.getText(), tKraj.getText(), tData.getText());
            igrzyska.getMainWindow().refreshView();
            Stage stage = (Stage) bDodaj.getScene().getWindow();
            stage.close();
        });
    }

}
