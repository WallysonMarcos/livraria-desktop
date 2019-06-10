package Controller;

import Model.Autor;
import DAO.AutorDAO;

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
    @FXML private Button btnList,btnSalvar,btnDeletar;
    @FXML private TextField txfNome,txfEmail;

    private Autor autor = new Autor();
    private AutorDAO autorDao = new AutorDAO();
    private Autor AutorSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
    }

    public void InitTable()
    {
        AutorSelecionado = null;
        tableView.setEditable(true);
        colId.setCellValueFactory( new PropertyValueFactory<Autor,Integer>("id") );

        colNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());

        colEmail.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEmail()) );
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit(
                new EventHandler<CellEditEvent<Autor, String>>() {
                    @Override
                    public void handle(CellEditEvent<Autor, String> t)
                    {

                        ((Autor) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                        autorDao.alterar( ((Autor) t.getTableView().getItems().get(t.getTablePosition().getRow())));
                    }
                }
        );

        tableView.setItems(autorDao.listarTodos());

        EventHandler<MouseEvent> clickListener = evt -> {
            AutorSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (AutorSelecionado != null)
                System.out.println("Selecionado: " + AutorSelecionado.getNome());
        };

        tableView.setOnMouseClicked(clickListener);
    }

    public void Deletar()
    {
        try
        {
            if(AutorSelecionado.getId() != 0)
            {
                autorDao.deletar(AutorSelecionado);
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
