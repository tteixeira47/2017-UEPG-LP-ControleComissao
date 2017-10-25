/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;
import model.DocFiscal;
import model.Empresa;
import model.Vendedor;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author mathe
 */
public interface DocFiscalRepository extends MongoRepository<DocFiscal, String> {
    
     public DocFiscal findByEmpresaAndNota(Empresa empresa, String nota);
     public List<DocFiscal> findByVendedor(Vendedor vendedor);
}
