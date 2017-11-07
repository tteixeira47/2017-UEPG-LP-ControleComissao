package model;

import static config.Config.df;
import static config.Config.nfc;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DocFiscal {

    @Id
    private String id;
    @DBRef
    private Empresa empresa;
    @DBRef
    private Vendedor vendedor;
    private String nota;
    private String situacao;
    private LocalDate emissao;
    private double valorNota;
    private double valorCredito;

    public DocFiscal() {
    }

    public DocFiscal(Empresa empresa, Vendedor vendedor, String nota, LocalDate emissao, double valorNota,
            double valorCredito, String situacao) {
        setEmpresa(empresa);
        setVendedor(vendedor);
        setNota(nota);
        setEmissao(emissao);
        setValorNota(valorNota);
        setValorCredito(valorCredito);
        setSituacao(situacao);
    }

    public DocFiscal(Empresa empresa, Vendedor vendedor, String nota, LocalDate emissao, double valorNota,
            double valorCredito) {
        setEmpresa(empresa);
        setVendedor(vendedor);
        setNota(nota);
        setEmissao(emissao);
        setValorNota(valorNota);
        setValorCredito(valorCredito);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    //Retorna data formatada
    public String getFormatEmissao() {
        if (emissao != null) {
            return (df.format(emissao));
        } else {
            return "";
        }
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public double getValorNota() {
        return valorNota;
    }

    //Retorna Valor da Nota formatado
    public String getFormatValorNota() {
        return nfc.format(valorNota);
    }

    public void setValorNota(double valorNota) {
        this.valorNota = valorNota;
    }

    public double getValorCredito() {
        return valorCredito;
    }

    //Retorna Valor da Nota formatado
    public String getFormatValorCredito() {
        return nfc.format(valorCredito);
    }

    public void setValorCredito(double valorCredito) {
        this.valorCredito = valorCredito;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final DocFiscal other = (DocFiscal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return empresa + " -> " + " " + nota + " " + vendedor;
    }
}
