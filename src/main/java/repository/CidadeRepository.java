/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import model.Cidade;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author mathe
 */
public interface CidadeRepository extends MongoRepository<Cidade, String> {
    public Cidade findByNome(String nome);
}
