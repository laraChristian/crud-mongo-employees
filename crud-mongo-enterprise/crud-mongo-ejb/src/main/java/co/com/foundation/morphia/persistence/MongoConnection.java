package co.com.foundation.morphia.persistence;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoConnection {

	private final Logger LOGGER = LogManager.getLogger(MongoConnection.class);

	private MongoClient mongoClient;
	private Datastore datastore;

	@Lock(LockType.READ)
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	@Lock(LockType.READ)
	public Datastore getDataStore() {
		return datastore;
	}

	@PostConstruct
	public void init() {
		LOGGER.info("start -- init method");
		mongoClient = new MongoClient(ConnectionParameters.HOST, ConnectionParameters.PORT);
		Morphia morphia = new Morphia();
		morphia.map(ConnectionParameters.ENTITIES);
		datastore = morphia.createDatastore(mongoClient, ConnectionParameters.DBNAME);
		LOGGER.info("end -- init method");
	}

}