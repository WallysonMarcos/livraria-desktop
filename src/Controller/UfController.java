package Controller;

import Model.Autor;
import Model.Uf;
import DAO.UfDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UfController implements Initializable
{
    @FXML
    private Button btnInserir,btnDeletar,btnRefresh;
    @FXML private TextField txtUf,txtNome;

    @FXML private TableView<Uf> tableView = new TableView<>();
    @FXML private TableColumn<Uf,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Uf,String> colUf = new TableColumn<>("UF");
    @FXML private TableColumn<Uf,String> colNome = new TableColumn<>("NOME");

    private UfDAO ufd = new UfDAO();
    private Uf uf = new Uf();
    private Uf ObjetoSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
    }

    public void InitTable()
    {
        ObjetoSelecionado = null;
        tableView.setEditable(true);
        colId.setCellValueFactory( new PropertyValueFactory<Uf,Integer>("id") );

        colUf.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getUf()) );
        colUf.setCellFactory(TextFieldTableCell.forTableColumn());

        colNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()) );
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(EditCellNome );

        tableView.setItems(ufd.listarTodos());
        tableView.setOnMouseClicked(TableClick);
    }

    public void Inserir()
    {
        uf.setUf(txtUf.getText());
        uf.setNome(txtNome.getText());

        ufd.inserir(uf);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de autores");
        alert.setHeaderText("Estado cadastrado com sucesso");
        alert.showAndWait();

        InitTable();
    }

    public void Deletar()
    {
        try
        {
            if(ObjetoSelecionado.getId() != 0)
            {
                if(!ufd.Deletar(ObjetoSelecionado))
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Deletar Estado");
                    alert.setHeaderText("Erro ao deletar Estado " + ObjetoSelecionado.getNome());
                    alert.showAndWait();
                }
                InitTable();
            }
        }catch (Exception e){
            System.out.println("Erro ao Deletar");
            System.out.println(e);
        }
    }

    private void limparCampos()
    {
        txtUf.setText("");
        txtNome.setText("");
        txtUf.requestFocus();
    }

    private EventHandler<MouseEvent> TableClick = evt -> {
        ObjetoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (ObjetoSelecionado != null)
            System.out.println("Selecionado: " + ObjetoSelecionado.getNome());
    };

    private EventHandler<TableColumn.CellEditEvent<Uf, String> > EditCellNome = evt -> {
        ((Uf) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setNome(evt.getNewValue());
        ufd.Alterar( ((Uf) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };
}
