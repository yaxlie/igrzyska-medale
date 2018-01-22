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
        setItems();
        igrzyska = IgrzyskaSingleton.getInstance();
        tImie.setText(z.getImie());
        tNazwisko.setText(z.getNazwisko());
        tKraj.setText(z.getKraj());
//        tData.setText(z.getDataUr());
        tDyscyplina.setText(z.getDyscyplina());
        
        try{
            String[] d = z.getDataUr().split("-");
            cbDzien.getSelectionModel().select(Integer.parseInt(d[0])-1);
            cbMiesiac.getSelectionModel().select(Integer.parseInt(d[1])-1);
            cbRok.getSelectionModel().select(Math.abs(Integer.parseInt(d[2])-2005));
        }catch(Exception e){}
        
        bDodaj.setOnAction((event) -> {
            
            String data;
            
            if(cbDzien.getSelectionModel().isEmpty() ||
                    cbMiesiac.getSelectionModel().isEmpty() ||
                    cbRok.getSelectionModel().isEmpty())
                data = "";
            else
                data = cbDzien.getSelectionModel().getSelectedItem().toString() + "-"
                    +cbMiesiac.getSelectionModel().getSelectedItem().toString() + "-"
                    +cbRok.getSelectionModel().getSelectedItem().toString();
            
            boolean b = igrzyska.updateZawodnik(z.getId(), tImie.getText(), tNazwisko.getText(), 
                    zespol.isSelected()?tKraj.getText():null, 0, tDyscyplina.getText(), tKraj.getText(), data);
            
            if(b){
                igrzyska.getMainWindow().refreshView();
                Stage stage = (Stage) bDodaj.getScene().getWindow();
                stage.close();
            }
        });
    }

}
