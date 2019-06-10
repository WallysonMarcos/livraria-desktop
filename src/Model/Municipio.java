package Model;

public class Municipio
{
    private int id;
    private String nome;
    private String uf_id;

    public Municipio() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf_id() {
        return uf_id;
    }

    public void setUf_id(String uf_id) {
        this.uf_id = uf_id;
    }
}
