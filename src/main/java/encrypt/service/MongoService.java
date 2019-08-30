package encrypt.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.inject.Singleton;

@Singleton
public class MongoService {

	private final MongoClient mongoClient;

	public MongoService() {
		this.mongoClient = new MongoClient();
	}

	public Datastore getDataStore() {
		Morphia morphia = new Morphia();
		return morphia.createDatastore(mongoClient, "encrypt-service");
	}
}
