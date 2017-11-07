package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repository.VendedorRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = VendedorRepository.class)
public class DBConfig extends AbstractMongoConfiguration {

    //Define o nome do banco
    @Override
    protected String getDatabaseName() {
        return "controledecomissao";
    }

    //Define a origem do cliente
    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient("localhost");
        return client;
    }
}
