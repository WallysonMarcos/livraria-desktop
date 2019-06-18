package DAO;

import Model.Municipio;
import Model.Uf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO extends ConnectionMgr
{
    public MunicipioDAO() {
    }

    public void Inserir(Municipio mun)
    {
        String sql = "INSERT INTO municipio (NOME, UF_ID) VALUES (?, ?)";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setString(1, mun.getNome());
            stmt.setInt(2, mun.getUf_id());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList listarTodos()
    {
        String sql = "select mun.id as id, mun.nome as nome, mun.uf_id as uf_id, uf.nome as ufnome from municipio as mun inner join ufs as uf  on uf.id = mun.uf_id";

        List<Municipio> muns = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Municipio mun = new Municipio();
                mun.setId(resultados.getInt(1));
                mun.setNome(resultados.getString(2));
                mun.setUf_id(resultados.getInt(3));
                mun.setEstado(resultados.getString(4));
                muns.add(mun);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos municipios");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(muns);
        return data;
    }


    public ObservableList ListarPorEstado( int estado_id)
    {
        String sql = "select mun.id as id, mun.nome as nome, mun.uf_id as uf_id, uf.nome as ufnome from municipio as mun "+
                "inner join ufs as uf  on uf.id = mun.uf_id" +
                " where mun.uf_id = ?";

        List<Municipio> muns = new ArrayList<>();

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, estado_id);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()){
                Municipio mun = new Municipio();
                mun.setId(resultados.getInt(1));
                mun.setNome(resultados.getString(2));
                mun.setUf_id(resultados.getInt(3));
                mun.setEstado(resultados.getString(4));
                muns.add(mun);
            }
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos municipios por Estado");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        ObservableList data = FXCollections.observableList(muns);
        return data;
    }

    public void Deletar(Municipio mun)
    {
        String sql = "delete from municipio where id = ?";

        try
        {
            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, mun.getId());
            stmt.execute();
            super.CloseConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Municipio");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void alterar(Municipio mun)
    {
        String sql = "update municipio set uf = ?, nome = ? where id = ?";

        try {

            super.InitConnection();
            PreparedStatement stmt = super.getConexao().prepareStatement(sql);
            stmt.setInt(1, mun.getUf_id());
            stmt.setString(2, mun.getNome());
            stmt.execute();

            System.out.println("Alterado " + mun.getNome());
            super.CloseConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
