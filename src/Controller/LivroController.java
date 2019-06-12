package Controller;
import DAO.EditoraDAO;
import Model.Editora;
import Model.Livro;
import DAO.LivroDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.value.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LivroController implements Initializable
{

    @FXML private TableView<Livro> tableView = new TableView<>();
    @FXML private TableColumn<Livro,Integer> colId = new TableColumn<>("ID");
    @FXML private TableColumn<Livro,String> colTit = new TableColumn<>("TITULO");
    @FXML private TableColumn<Livro,Integer> colQtd = new TableColumn<>("QUANTIDADE");
    @FXML private TableColumn<Livro,Float> colPrc = new TableColumn<>("PRECO");
    @FXML private TableColumn<Livro,LocalDate> colDtLan = new TableColumn<>("DATA_LANCAMENTO");
    @FXML private TableColumn<Livro,String> colEdit = new TableColumn<>("EDITORA");

    @FXML private Button btnList,btnSalvar,btnDeletar;
    @FXML private ComboBox<Editora> cbEdt = new ComboBox();
    @FXML private TextField txfTitulo,txfQuant, txfPreco;
    @FXML private DatePicker dpLanc;

    private LivroDAO livroDao = new LivroDAO();
    private EditoraDAO editoraDao = new EditoraDAO();
    private Livro livro = new Livro();
    private Livro LvrObjetoSelecionado = null;
    private Editora EdtObjetoSelecionado = null;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        InitTable();
        InitCombox();
    }

    public void Inserir()
    {
        livro.setTitulo(txfTitulo.getText());
        livro.setQuantidade(Integer.parseInt(txfQuant.getText()));
        livro.setPreco(Float.parseFloat(txfPreco.getText()));
        livro.setData_lancamento( dpLanc.getValue() );
        livro.setEditora_id(EdtObjetoSelecionado.getId());
        livro.setEditora(EdtObjetoSelecionado.getNome());

        livroDao.Inserir(livro);

        limparCampos();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Editoras");
        alert.setHeaderText("Autor cadastrado com sucesso");
        alert.showAndWait();
        InitTable();
    }

    private void limparCampos()
    {
        txfTitulo.setText("");
        txfPreco.setText("");
        txfQuant.setText("");
    }

    public void Deletar()
    {
        try
        {
            if(LvrObjetoSelecionado.getId() != 0)
            {
                livroDao.Deletar(LvrObjetoSelecionado);
                InitTable();
            }

        }catch (Exception e){
            System.out.println("Erro ao Deletar");
            System.out.println(e);
        }
    }

    public void InitTable()
    {
        LvrObjetoSelecionado = null;
        tableView.setEditable(true);
        colId.setCellValueFactory( new PropertyValueFactory<Livro,Integer>("id") );

        colTit.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTitulo()) );
        colTit.setCellFactory(TextFieldTableCell.forTableColumn());
        colTit.setOnEditCommit(SendCommitTitulo);

        colQtd.setCellValueFactory(new PropertyValueFactory<Livro,Integer>("quantidade") );

        colPrc.setCellValueFactory(new PropertyValueFactory<Livro,Float>("preco") );

        colDtLan.setCellValueFactory(new PropertyValueFactory<Livro,LocalDate>("data_lancamento") );

        colEdit.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEditora()) );

        tableView.setItems(livroDao.ListarTodos());
        tableView.setOnMouseClicked(TableClick);

    }

    private EventHandler<TableColumn.CellEditEvent<Livro, String> > SendCommitTitulo = evt -> {
        ((Livro) evt.getTableView().getItems().get(
                evt.getTablePosition().getRow())
        ).setTitulo(evt.getNewValue());
        livroDao.Alterar( ((Livro) evt.getTableView().getItems().get(evt.getTablePosition().getRow())));
    };

    private EventHandler<MouseEvent> TableClick = evt -> {
        LvrObjetoSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (LvrObjetoSelecionado != null)
            System.out.println("Selecionado: " + LvrObjetoSelecionado.getTitulo());
    };


    public void InitCombox()
    {
        cbEdt.setPromptText("Selecione uma Editora");
        cbEdt.cellFactoryProperty();
        cbEdt.setOnMouseClicked(ComboClick);
        cbEdt.setCellFactory(ComboFactory);
        cbEdt.setOnAction(ComboAction);
        cbEdt.setConverter(new StringConverter<Editora>() {
            @Override
            public String toString(Editora edt) {
                if (edt == null){
                    return null;
                } else {
                    return edt.getNome();
                }
            }
            @Override
            public Editora fromString(String edtId) {
                return null;
            }
        });
    }

    private EventHandler<MouseEvent> ComboClick = evt -> {
        cbEdt.setItems(editoraDao.ListarTodos());
    };
    private EventHandler<ActionEvent> ComboAction = evt -> {
        EdtObjetoSelecionado = cbEdt.getSelectionModel().getSelectedItem();
        if (EdtObjetoSelecionado != null)
            System.out.println("Selecionado: " + EdtObjetoSelecionado.getNome());
    };
    private Callback<ListView<Editora>,ListCell<Editora>> ComboFactory = evt ->
    {
        return new ListCell<Editora>() {
            @Override
            protected void updateItem(Editora item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getNome());
                }
            }
        };
    };
}
