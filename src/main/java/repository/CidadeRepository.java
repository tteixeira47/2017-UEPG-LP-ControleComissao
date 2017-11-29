package repository;

import model.Cidade;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CidadeRepository extends MongoRepository<Cidade, String> {

    public Cidade findByNome(String nome);
    
    public Integer countByNome(String nome);
}
