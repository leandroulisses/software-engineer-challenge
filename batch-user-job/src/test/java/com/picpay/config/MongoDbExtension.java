package com.picpay.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoDbExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static MongoDBContainer mongoDBContainer;
    private static Boolean started = false;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!started) {
            mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
            mongoDBContainer.start();

            started = Boolean.FALSE;
            System.setProperty("MONGO_CONNECTION", mongoDBContainer.getReplicaSetUrl());
        }
    }

    @Override
    public void close() throws Throwable {
        mongoDBContainer.close();
    }

}
