package Controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    AnchorPane AfDashboard, AfAutores, AfEditoras, AfLivros, AfMunicipios, AfUfs;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            AfDashboard = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
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

        FadeTransition ft = new FadeTransition(Duration.millis(600));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void toHome(ActionEvent event)
    {
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
            AfAutores = FXMLLoader.load(getClass().getResource("/View/Autores.fxml"));
            this.setNode(AfAutores);
        }catch (Exception e) {
            System.out.println("Erro ao toAutores");
            System.out.println(e);
        }

    }

    @FXML
    private void toEditoras(ActionEvent event)
    {
        try
        {
            AfEditoras = FXMLLoader.load(getClass().getResource("/View/Editoras.fxml"));
            setNode(AfEditoras);
        }catch (Exception e) {
            System.out.println("Erro ao toEditoras");
            System.out.println(e);
        }
    }

    @FXML
    private void toLivros(ActionEvent event)
    {
        try
        {
            AfLivros = FXMLLoader.load(getClass().getResource("/View/Livros.fxml"));
            setNode(AfLivros);
        }catch (Exception e) {
            System.out.println("Erro ao toMunicipio");
            System.out.println(e);
        }
    }

    @FXML
    private void toEstado (ActionEvent event)
    {
        try
        {
            AfUfs = FXMLLoader.load(getClass().getResource("/View/Ufs.fxml"));
            setNode(AfUfs);
        }catch (Exception e) {
            System.out.println("Erro ao toEstado");
            System.out.println(e);
        }
    }

    @FXML
    private void toMunicipio (ActionEvent event)
    {
        try
        {
            AfMunicipios = FXMLLoader.load(getClass().getResource("/View/Municipios.fxml"));
            setNode(AfMunicipios);
        }catch (Exception e) {
            System.out.println("Erro ao toMunicipio");
            System.out.println(e);
        }

    }
}
