package DAO;

import Model.Autor;
import Model.Uf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UfDAO extends ConnectionMgr
{
    public UfDAO() {
    }

    public void inserir(Uf uf)
    {
        String sql = "INSERT INTO UFS (UF, NOME) VALUES (?, ?)";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, uf.getUf());
            stmt.setString(2, uf.getNome());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList listarTodos()
    {
        String sql = "select id,uf,nome from ufs";
        List<Uf> ufs = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Uf uf = new Uf();
                uf.setId(resultados.getInt("id"));
                uf.setUf(resultados.getString("uf"));
                uf.setNome(resultados.getString("nome"));
                ufs.add(uf);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(ufs);
        return data;
    }

    public void Alterar(Uf uf)
    {
        String sql = "update ufs set uf = ?, nome = ? where id = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, uf.getUf());
            stmt.setString(2, uf.getNome());
            stmt.setInt(3, uf.getId());

            stmt.execute();

            System.out.println("Alterado " + uf.getNome());
            super.CloseConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean Deletar(Uf uf)
    {
        String sql = "delete from ufs where id = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, uf.getId());
            stmt.execute();
            super.CloseConnection();
            return true;
        } catch (SQLException e)
        {

            System.out.println("Erro ao Deletar");
            System.out.println(e);
            return false;
        }
    }

}
