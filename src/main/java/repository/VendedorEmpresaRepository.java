/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.Empresa;
import model.Vendedor;
import model.VendedorEmpresa;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author mathe
 */
public interface VendedorEmpresaRepository extends MongoRepository<VendedorEmpresa, String> {
    public List<VendedorEmpresa> findByVendedor(Vendedor vendedor);    
    public VendedorEmpresa findByVendedorAndEmpresa(Vendedor vendedor, Empresa empresa);
    public void deleteByVendedor(Vendedor vendedor);

    
}
