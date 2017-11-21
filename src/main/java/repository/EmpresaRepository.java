package repository;

import java.util.List;
import model.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {

    public Empresa findByCnpj(String cnpj);
    
    public List<Empresa> findByNomeLikeIgnoreCaseOrFantasiaIgnoreCase(String nome, String fantasia);
    
}
