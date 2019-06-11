package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.Autor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO extends ConnectionMgr
{
    public AutorDAO()
    {
    }

    public void inserir(Autor autor)
    {
        String sql = "INSERT INTO AUTORES (NOME, EMAIL) VALUES (?, ?)";

        try
        {
            super.InitConnection();

            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getEmail());
            stmt.execute();

            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList listarTodos()
    {
        String sql = "select id,nome,email from autores";
        List<Autor> autores = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Autor autor = new Autor();
                autor.setId(resultados.getInt("id"));
                autor.setNome(resultados.getString("nome"));
                autor.setEmail(resultados.getString("email"));
                autores.add(autor);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(autores);
        return data;
    }

    public void alterar(Autor autor)
    {
        String sql = "update autores set nome = ?, email = ? where id = ?";

        try {

            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getEmail());
            stmt.setInt(3, autor.getId());

            stmt.execute();

            System.out.println("Alterado " + autor.getNome());
            super.CloseConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Autor autor) {
        String sql = "delete from autores where id = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, autor.getId());
            //Executar
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar");
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    public Autor listarPorId(Autor autor)
    {
        String sql = "select id, nome, email from autores where id = ?";

        try {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, autor.getId());

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Encontra resultado
            while(resultados.next()) {
                autor.setId(resultados.getInt("id"));
                autor.setNome(resultados.getString("nome"));
                autor.setEmail(resultados.getString("email"));
            }

            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return autor;
    }
}
