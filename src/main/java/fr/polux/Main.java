package fr.polux;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

public class Main {

    /**
     * Need Mongo in standalone build or docker.
     * docker run -d -p 27017:27017 mongo:3.4.9
     */
    public static void main(String[] args) throws UnknownHostException {
        MongoCredential credential = MongoCredential.createCredential("robert", "my_db", "password".toCharArray());
        ServerAddress address = new ServerAddress(InetAddress.getLocalHost(), 27017);

        // without this timeout, it takes 30 seconds to get the exception...
        MongoClientOptions options = MongoClientOptions.builder().serverSelectionTimeout(5000).build();

        MongoClient mongoClient = new MongoClient(address, Collections.singletonList(credential), options);

        // I do that because if I don't it's lazy so the server is not called.
        mongoClient.getDatabase("my_db").getCollection("toto").count();
    }
}
