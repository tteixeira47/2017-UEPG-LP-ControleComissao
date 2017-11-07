package model;

import static config.Config.nf;
import static config.Config.nfc;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//Classe que relaciona um vendedor a uma empresa
@Document
@CompoundIndexes({
    @CompoundIndex(
            name = "vendedorEmpresa_idx",
            def = "{'vendedor': 1,'empresa': 1}", unique = true)
})
public class VendedorEmpresa {

    @Id
    private String id;
    @DBRef
    private Vendedor vendedor;
    @DBRef
    private Empresa empresa;
    private Integer numNotas;
    private Double valorTotal;
    private Double valorComissao;

    public VendedorEmpresa() {
    }

    public VendedorEmpresa(Vendedor vendedor, Empresa empresa, Integer numNotas, Double valorTotal, Double valorComissao) {
        setVendedor(vendedor);
        setEmpresa(empresa);
        setNumNotas(numNotas);
        setValorTotal(valorTotal);
        setValorComissao(valorComissao);
    }

    //Método de incrementar número de notas da relação
    public void addNumNotas() {
        this.numNotas++;
    }

    //Método de incrementar valor total das notas da relação
    public void addValorTotal(Double valor) {
        this.valorTotal += valor;
    }

    //Método de incrementar o valor de comissão do vendedor da relação
    public void addValorComissao(Double valor) {
        this.valorComissao += valor;
    }

    //Método que realiza o cálculo de percentual da comissão
    public Double getPercComissao() {
        if (valorTotal != 0) {
            return (valorComissao / valorTotal * 100);

        } else {
            return 0.0;
        }
    }

    //Retorna Percentual de Comissão formatado
    public String getFormatPercComissao() {
        return nf.format(getPercComissao()) + " %";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getEmpresaFantasia() {
        if (empresa != null) {
            return empresa.getFantasia();
        } else {
            return "";
        }
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Integer getNumNotas() {
        return numNotas;
    }

    public void setNumNotas(Integer numNotas) {
        this.numNotas = numNotas;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    //Retorna Valor Total formatado
    public String getFormatValorTotal() {
        return nfc.format(valorTotal);
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorComissao() {
        return valorComissao;
    }

    //Retorna Valor Comissao formatado
    public String getFormatValorComissao() {
        return nfc.format(valorComissao);
    }

    public void setValorComissao(Double valorComissao) {
        this.valorComissao = valorComissao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final VendedorEmpresa other = (VendedorEmpresa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return vendedor + " - " + empresa;
    }

}
