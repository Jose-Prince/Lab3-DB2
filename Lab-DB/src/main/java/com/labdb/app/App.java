package com.labdb.app;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;

public class App {

    public static void main(String... args) {

        // URI examples: "neo4j://localhost", "neo4j+s://xxx.databases.neo4j.io"
        final String dbUri = "neo4j+s://4b79cc40.databases.neo4j.io";
        final String dbUser = "neo4j";
        final String dbPassword = "w2x6GKnBbaGXq8xMUO-Jxm_lQfYWYATgImJRrXIfCs8";

        try (var driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword))) {
            driver.verifyConnectivity();
            System.out.println("Connection established.");
        }
    }
}
