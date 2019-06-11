package Controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;

public class PrincipalController implements Initializable
{

    @FXML private AnchorPane holderPane;
    @FXML private Button btnAutores,btnHome,btnPricing1;

    AnchorPane AfDashboard, AfAutores, AfMunicipios, AfUfs;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            AfDashboard = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
            AfAutores = FXMLLoader.load(getClass().getResource("/View/Autores.fxml"));
            AfMunicipios = FXMLLoader.load(getClass().getResource("/View/Municipios.fxml"));
            AfUfs = FXMLLoader.load(getClass().getResource("/View/Ufs.fxml"));
            this.setNode(AfDashboard);
        }catch (Exception e) {
            System.out.println("Erro ao iniciar PrincipalController");
            System.out.println(e);
        }
    }

    private void setNode(Node node)
    {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(300));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void toHome(ActionEvent event) {
        try
        {
            this.setNode(AfDashboard);
        }catch (Exception e) {
            System.out.println("Erro ao toAutores");
            System.out.println(e);
        }

    }

    @FXML
    private void toAutores(ActionEvent event) {
        try
        {
            this.setNode(AfAutores);
        }catch (Exception e) {
            System.out.println("Erro ao toAutores");
            System.out.println(e);
        }

    }

    @FXML
    private void toEditoras(ActionEvent event) {
        //setNode(pricing);
    }

    @FXML
    private void toLivros(ActionEvent event) {
        //setNode(pricing);
    }

    @FXML
    private void toEstado (ActionEvent event) {
        setNode(AfUfs);
    }

    @FXML
    private void toMunicipio (ActionEvent event) {
        setNode(AfMunicipios);
    }
}
