package DAO;

import Model.Livro;
import Model.Municipio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends ConnectionMgr
{
    public LivroDAO() {
    }

    public ObservableList ListarTodos()
    {

        String sql = "select lvr.id, " +
                "lvr.titulo," +
                " lvr.quantidade," +
                " lvr.preco," +
                " lvr.data_lancamento," +
                " lvr.editora_id," +
                " edt.nome " +
                " from livros as lvr " +
                " inner join editoras as edt  " +
                " on edt.id = lvr.editora_id " +
                " group by lvr.id,lvr.titulo,lvr.quantidade,lvr.preco,lvr.data_lancamento,lvr.editora_id,edt.nome";
        List<Livro> livros = new ArrayList<>();
        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Livro lvr = new Livro();
                lvr.setId(resultados.getInt(1));
                lvr.setTitulo(resultados.getString(2));
                lvr.setQuantidade(resultados.getInt(3));
                lvr.setPreco(resultados.getFloat(4));
                lvr.setData_lancamento(LocalDate.parse (resultados.getString(5)) );
                lvr.setEditora_id(resultados.getInt(6));
                lvr.setEditora(resultados.getString(7));
                livros.add(lvr);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos Livros");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(livros);
        return data;
    }

    public void Inserir(Livro lvr)
    {
        //INSERT INTO livros(ID, TITULO, QUANTIDADE, EDITORA_ID, PRECO, DATA_LANCAMENTO) VALUES
        String sql = "INSERT INTO livros(TITULO, QUANTIDADE, PRECO, DATA_LANCAMENTO, EDITORA_ID) VALUES (?, ?, ?, ?, ?)";
        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, lvr.getTitulo());
            stmt.setInt(2, lvr.getQuantidade());
            stmt.setFloat(3, lvr.getPreco());
            stmt.setString(4, lvr.getData_lancamento().toString());
            stmt.setInt(5, lvr.getEditora_id());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir Livro!");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void Deletar(Livro lvr)
    {
        String sql = "delete from livros where id = ?";
        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, lvr.getId());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Livro");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void Alterar(Livro edt)
    {
        String sql = "UPDATE livros SET TITULO= ?,QUANTIDADE= ? ,EDITORA_ID= ?,PRECO= ?  WHERE ID = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, edt.getTitulo());
            stmt.setInt(2, edt.getQuantidade());
            stmt.setInt(3, edt.getEditora_id());
            stmt.setFloat(4, edt.getPreco());
            //stmt.setDate(5, LocalDate.parse( edt.getData_lancamento()));
            stmt.setInt(5, edt.getId());
            stmt.execute();
            System.out.println("Alterado " + edt.getTitulo());
            super.CloseConnection();

        } catch (SQLException e) {
            System.out.println("Erro ao Alterar Editora");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }


    public int Count()
    {
        int num_livros = 0;
        String sql = "select count(*) from livros";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            while (resultados.next()) {
                num_livros = resultados.getInt(1);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return num_livros;
    }
}
