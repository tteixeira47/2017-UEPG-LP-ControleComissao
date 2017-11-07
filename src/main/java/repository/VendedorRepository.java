package repository;

import model.Vendedor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendedorRepository extends MongoRepository<Vendedor, String> {

    public Vendedor findByNome(String nome);
}
