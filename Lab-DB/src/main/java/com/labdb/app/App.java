package com.labdb.app;

import java.util.Map;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;
import com.labdb.app.User;
import com.labdb.app.Movie;

public class App {

	public static void createUser(Driver driver, User user) {
		var result = driver.executableQuery("MERGE (u:User {name: $name, userId: $userId})")
			.withParameters(Map.of("name", user.Name, "userId", user.UserId))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

	public static void createMovie(Driver driver, Movie movie) {
		var result = driver.executableQuery("MERGE (u:Movie {title: $title, movieId: $movieId, year: $year, plot: $plot})")
			.withParameters(Map.of("title", movie.Title, "movieId", movie.MovieId, "year", movie.Year, "plot", movie.Plot))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

	public static void createRatedConnection(Driver driver, User user, Movie movie, RatedRelation relation) {
		var result = driver.executableQuery("MATCH (m:Movie {title: $title, movieId: $movieId, year: $year, plot: $plot}), (u:User {name: $name, userId: $userId}) CREATE (u)-[r:Rated {rating: $rating, timestamp: $timestamp}]")
			.withParameters(Map.of("title", movie.Title, "movieId", movie.MovieId, "year", movie.Year, "plot", movie.Plot, "name", user.Name, "userId", user.UserId, "rating", relation.Rating, "timestamp", relation.Timestamp))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

    public static void main(String... args) {

        // URI examples: "neo4j://localhost", "neo4j+s://xxx.databases.neo4j.io"
        final String dbUri = "neo4j+s://4b79cc40.databases.neo4j.io";
        final String dbUser = "neo4j";
        final String dbPassword = "w2x6GKnBbaGXq8xMUO-Jxm_lQfYWYATgImJRrXIfCs8";

        try (var driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword))) {
            driver.verifyConnectivity();
            System.out.println("Connection established.");

						final User[] users = new User[] {
							new User("Flavio", "12345"),
							new User("Jose", "668"),
							new User("Andre", "50"),
							new User("Rayo", "0909"),
							new User("Juan", "8765"),
						};

            System.out.println("Creating users...");
						for (int i = 0; i< users.length; i++) {
							createUser(driver, users[i]);
						}

						final Movie[] movies = new Movie[] {
							new Movie("The Watchmen", 76, 2015, "Random Movie 1"),
							new Movie("The Lego Movie", 30, 2005, "Random Movie 2"),
						};

            System.out.println("Creating movies...");
						for (int i = 0; i< movies.length; i++) {
							createMovie(driver, movies[i]);
						}

        }
    }
}
