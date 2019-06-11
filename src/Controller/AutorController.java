package Controller;

import Model.Autor;
import DAO.AutorDAO;

import Model.Uf;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;

public class AutorController  implements Initializable
{
    @FXML private TableView<Autor> tableView = new TableView<>();
    @FXML private TableColumn<Autor,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Autor,String> colNome = new TableColumn<>("NOME");
    @FXML private TableColumn<Autor,String> colEmail = new TableColumn<>("EMAIL");
    @FXML private Button btnInserir,btnDeletar,btnRefresh;
    @FXML private TextField txfNome,txfEmail;

    private Autor autor = new Autor();
    private AutorDAO autorDao = new AutorDAO();
    private Autor ObjetoSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
    }

    public void InitTable()
    {
        ObjetoSelecionado = null;
        tableView.setEditable(true);
        colId.setCellValueFactory( new PropertyValueFactory<Autor,Integer>("id") );

        colNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit( SendCommitNome);

        colEmail.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEmail()) );
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit(SendCommitEmail);

        tableView.setItems(autorDao.listarTodos());
        tableView.setOnMouseClicked(TableClick);
    }

    private EventHandler<MouseEvent> TableClick = evt -> {
        ObjetoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (ObjetoSelecionado != null)
            System.out.println("Selecionado: " + ObjetoSelecionado.getNome());
    };

    private EventHandler<TableColumn.CellEditEvent<Autor, String> > SendCommitNome = evt -> {
        ((Autor) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setNome(evt.getNewValue());
        autorDao.alterar( ((Autor) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<TableColumn.CellEditEvent<Autor, String> > SendCommitEmail = evt -> {
        ((Autor) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setEmail(evt.getNewValue());
        autorDao.alterar( ((Autor) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    public void Deletar()
    {
        try
        {
            if(ObjetoSelecionado.getId() != 0)
            {
                autorDao.deletar(ObjetoSelecionado);
                InitTable();
            }

        }catch (Exception e){
            System.out.println("Erro ao Deletar");
            System.out.println(e);
        }

    }

    public void Inserir()
    {
        autor.setNome(txfNome.getText());
        autor.setEmail(txfEmail.getText());

        new AutorDAO().inserir(autor);

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
