package repository;

import java.util.List;
import model.DocFiscal;
import model.Empresa;
import model.Vendedor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocFiscalRepository extends MongoRepository<DocFiscal, String> {

    public DocFiscal findByEmpresaAndNota(Empresa empresa, String nota);

    public List<DocFiscal> findByVendedor(Vendedor vendedor);

    public List<DocFiscal> findByVendedorAndEmpresa(Vendedor vendedor, Empresa empresa);
}
