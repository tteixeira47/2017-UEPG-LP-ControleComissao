package model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Empresa {

    @Id
    private String id;
    @Indexed(unique = true)
    private String cnpj;
    private String nome;
    private String fantasia;
    @DBRef
    private Cidade cidade;

    public Empresa() {
    }

    public Empresa(String cnpj, String nome) {
        setCnpj(cnpj);
        setNome(nome);
        setFantasia(nome);
    }

    public Empresa(String cnpj, String nome, Cidade cidade) {
        setCnpj(cnpj);
        setNome(nome);
        setFantasia(nome);
        setCidade(cidade);
    }

    public Empresa(String cnpj, String nome, String fantasia, Cidade cidade) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.fantasia = fantasia;
        this.cidade = cidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fantasia;
    }
}
