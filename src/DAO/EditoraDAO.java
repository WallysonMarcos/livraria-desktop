package DAO;

import Model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO extends ConnectionMgr
{
    public EditoraDAO() {
    }

    public ObservableList ListarTodos()
    {
        String sql = "select edt.id, " +
                "edt.nome," +
                " edt.site," +
                " edt.endereco," +
                " edt.bairro," +
                " edt.telefone," +
                "edt.municipio_id," +
                " mun.nome " +
                " from editoras as edt " +
                " inner join municipio as mun  " +
                " on mun.id = edt.municipio_id " +
                " group by edt.id, edt.nome, edt.site, edt.endereco, edt.bairro, edt.telefone,edt.municipio_id";

        List<Editora> muns = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Editora editora = new Editora();
                editora.setId(resultados.getInt(1));
                editora.setNome(resultados.getString(2));
                editora.setSite(resultados.getString(3));
                editora.setEndereco(resultados.getString(4));
                editora.setBairro(resultados.getString(5));
                editora.setTelefone(resultados.getString(6));
                editora.setMunicipio_id(resultados.getInt(7));
                editora.setMunicipio(resultados.getString(8));

                muns.add(editora);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos Editoras");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(muns);
        return data;
    }

    public void Inserir(Editora edt)
    {
        String sql = "INSERT INTO editoras( NOME, SITE, ENDERECO, BAIRRO, TELEFONE, MUNICIPIO_ID) VALUES  (?, ?, ?, ?, ?, ?)";
        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, edt.getNome());
            stmt.setString(2, edt.getSite());
            stmt.setString(3, edt.getEndereco());
            stmt.setString(4, edt.getBairro());
            stmt.setString(5, edt.getTelefone());
            stmt.setInt(6, edt.getMunicipio_id());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir Editora!");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }


    public void Deletar(Editora edt)
    {
        String sql = "delete from editoras where id = ?";
        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, edt.getId());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Editora");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }


    public void Alterar(Editora edt)
    {
        String sql = "UPDATE editoras SET NOME= ?, " +
                " SITE = ?," +
                " ENDERECO = ?," +
                " BAIRRO= ?," +
                " TELEFONE= ?," +
                " MUNICIPIO_ID= ?" +
                " WHERE ID = ?";
        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, edt.getNome());
            stmt.setString(2, edt.getSite());
            stmt.setString(3, edt.getEndereco());
            stmt.setString(4, edt.getBairro());
            stmt.setString(5, edt.getTelefone());
            stmt.setInt(6, edt.getMunicipio_id());
            stmt.setInt(7, edt.getId());
            stmt.execute();
            System.out.println("Alterado " + edt.getNome());
            super.CloseConnection();

        } catch (SQLException e) {
            System.out.println("Erro ao Alterar Editora");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public int Count()
    {
        int num_editora = 0;
        String sql = "select count(*) from editoras";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next())
            {
                num_editora = resultados.getInt(1);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return num_editora;
    }
}
