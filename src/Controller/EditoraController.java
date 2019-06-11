package Controller;

import Model.Editora;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;


public class EditoraController  implements Initializable
{
    @FXML
    private TableView<Editora> tableView = new TableView<>();
    @FXML private TableColumn<Editora,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Editora,String> colNome = new TableColumn<>("NOME");
    @FXML private TableColumn<Editora,String> colEnd = new TableColumn<>("ENDERECO");
    @FXML private TableColumn<Editora,String> colBairro = new TableColumn<>("BAIRRO");
    @FXML private TableColumn<Editora,String> colTel = new TableColumn<>("TELEFONE");
    @FXML private TableColumn<Editora,Integer> colMun = new TableColumn<>("MUNICIPIO_ID");

    @FXML private Button btnList,btnSalvar,btnDeletar;
    @FXML private TextField txfNome,txfEmail;

    private Editora autor = new Editora();
    private Editora ObjetoSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
    }

    public void InitTable() {
        ObjetoSelecionado = null;
    }

    public void Deletar()
    {
        try
        {
            if(ObjetoSelecionado.getId() != 0)
            {
                InitTable();
            }

        }catch (Exception e){
            System.out.println("Erro ao Deletar");
            System.out.println(e);
        }

    }

    public void Inserir()
    {
        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de autores");
        alert.setHeaderText("Autor cadastrado com sucesso");
        alert.showAndWait();

        InitTable();
    }

    private void limparCampos()
    {
        txfNome.setText("");
        txfEmail.setText("");
        txfNome.requestFocus();
    }


}
