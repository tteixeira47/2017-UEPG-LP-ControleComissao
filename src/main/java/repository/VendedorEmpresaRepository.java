package repository;

import java.util.List;
import model.Empresa;
import model.Vendedor;
import model.VendedorEmpresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendedorEmpresaRepository extends MongoRepository<VendedorEmpresa, String> {

    public List<VendedorEmpresa> findByVendedor(Vendedor vendedor);

    public VendedorEmpresa findByVendedorAndEmpresa(Vendedor vendedor, Empresa empresa);

    public void deleteByVendedor(Vendedor vendedor);

}
