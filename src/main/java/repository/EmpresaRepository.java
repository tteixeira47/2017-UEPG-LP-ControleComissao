package repository;

import java.util.List;
import model.Cidade;
import model.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {

    public Empresa findByCnpj(String cnpj);
    
    public Integer countByCidade(Cidade cidade);
    
    public List<Empresa> findByNomeLikeIgnoreCaseOrFantasiaLikeIgnoreCase(String nome, String fantasia);
    
    public List<Empresa> findByCnpjLike(String cnpj);
    
}

