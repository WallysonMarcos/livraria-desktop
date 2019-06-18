package Controller;

import DAO.AutorDAO;
import DAO.EditoraDAO;
import DAO.LivroDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable
{
    @FXML private Label lbAutor;
    @FXML private Label lbLivros;
    @FXML private Label lbEditora;

    AutorDAO autorDAO = new AutorDAO();
    LivroDAO livroDAO = new LivroDAO();
    EditoraDAO editoraDao = new EditoraDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbAutor.setText( Integer.toString(autorDAO.Count()));
        lbLivros.setText( Integer.toString(livroDAO.Count()));
        lbEditora.setText( Integer.toString(editoraDao.Count()));
    }
}
