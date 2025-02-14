package com.labdb.app;

import java.util.Map;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;
import org.neo4j.driver.Driver;
import com.labdb.app.User;
import com.labdb.app.Movie;
import java.util.ArrayList;

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
		var result = driver.executableQuery("MATCH (m:Movie {title: $title, movieId: $movieId, year: $year, plot: $plot}), (u:User {name: $name, userId: $userId}) CREATE (u)-[r:Rated {rating: $rating, timestamp: $timestamp}]->(m)")
			.withParameters(Map.of("title", movie.Title, "movieId", movie.MovieId, "year", movie.Year, "plot", movie.Plot, "name", user.Name, "userId", user.UserId, "rating", relation.Rating, "timestamp", relation.Timestamp))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

	public static void deleteDB(Driver driver) {
		driver.executableQuery("MATCH (n)-[r]->(n2) DETACH DELETE n,r,n2")
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

	public static User findUserBasedOnReview(Driver driver, RatedRelation rel) {
		var result = driver.executableQuery("MATCH (u:User) -[r:Rated {rating: $rating, timestamp: $timestamp}]->(m:Movie) RETURN u")
			.withParameters(Map.of("rating", rel.Rating, "timestamp", rel.Timestamp))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();

		var records = result.records();
		var r = records.get(0);
		User user = new User(r.get("name").asString(), r.get("userId").asString());

		return user;
	}

    public static void main(String... args) {

        // URI examples: "neo4j://localhost", "neo4j+s://xxx.databases.neo4j.io"
        final String dbUri = "neo4j+s://4b79cc40.databases.neo4j.io";
        final String dbUser = "neo4j";
        final String dbPassword = "w2x6GKnBbaGXq8xMUO-Jxm_lQfYWYATgImJRrXIfCs8";

        HashMap<String, String> personAttributes = new HashMap<>();
        String nodo_name1 = "Person (Actor/Director)"

        personAttributes.put("name", "John Doe");
        personAttributes.put("tmdbid", '12345');  // Integer value
        personAttributes.put("born", "2024");  // Date object representing birth time
        personAttributes.put("died", "Na");  // Died might be null if still alive
        personAttributes.put("bornin", "New York");
        personAttributes.put("url", "https://www.example.com");
        personAttributes.put("imdbid", "67890");  // Integer value for IMDb ID
        personAttributes.put("bio", "John Doe is an actor known for his roles in movies.");
        personAttributes.put("poster", "https://www.example.com/johndoe.jpg")




        ArrayList<HashMap<String, String>> atributos2 = new ArrayList<>();
        String nodo_name12 = "Actor"
        HashMap<String, String> personAttributesA = new HashMap<>();
        personAttributesA.put("name", "John Doe");
        personAttributesA.put("tmdbid", '12345');  // Integer value
        personAttributesA.put("born", "2024");  // Date object representing birth time
        personAttributesA.put("died", "NA");  // Died might be null if still alive
        personAttributesA.put("bornin", "New York");
        personAttributesA.put("url", "https://www.example.com");
        personAttributesA.put("imdbid", "67890");  // Integer value for IMDb ID
        personAttributesA.put("bio", "John Doe is an actor known for his roles in movies.");
        personAttributesA.put("poster", "https://www.example.com/johndoe.jpg")


        ArrayList<HashMap<String, String>> atributos3 = new ArrayList<>();
        String nodo_name13 = "Person (Actor/Director)"
        HashMap<String, String> personAttributesD = new HashMap<>();
        personAttributesD.put("name", "John Doe");
        personAttributesD.put("tmdbid", '12345');  // Integer value
        personAttributesD.put("born", "2024");  // Date object representing birth time
        personAttributesD.put("died", "NA");  // Died might be null if still alive
        personAttributesD.put("bornin", "New York");
        personAttributesD.put("url", "https://www.example.com");
        personAttributesD.put("imdbid", "67890");  // Integer value for IMDb ID
        personAttributesD.put("bio", "John Doe is an actor known for his roles in movies.");
        personAttributesD.put("poster", "https://www.example.com/johndoe.jpg")


        ArrayList<HashMap<String, String>> atributos4 = new ArrayList<>();
        String nodo_name14 = "Movie"
        HashMap<String, String> movieAttributes = new HashMap<>();
        // Adding key-value pairs
        movieAttributes.put("title", "Inception");  // String
        movieAttributes.put("tmdbid", "12345");  // Integer
        movieAttributes.put("released", "2025");  // Date (datetime)
        movieAttributes.put("imdbRating", "8.8");  // Decimal (0-10)
        movieAttributes.put("movield", "67890");  // Integer
        movieAttributes.put("year", "2010");  // Integer
        movieAttributes.put("imdbid", "456789");  // Integer
        movieAttributes.put("runtime", "148");  // Integer (minutes)
        movieAttributes.put("countries", "USA, UK");  // List of strings
        movieAttributes.put("imdbVotes", "2000000");  // Integer
        movieAttributes.put("url", "https://www.example.com/inception");  // String
        movieAttributes.put("revenue", "829895144");  // Integer
        movieAttributes.put("plot", "A mind-bending thriller by Christopher Nolan.");  // String
        movieAttributes.put("poster", "https://www.example.com/inception.jpg");  // String
        movieAttributes.put("languages","English, French");  // List of strings
        movieAttributes.put("budget", "160000000");  // Integer


         ArrayList<HashMap<String, String>> atributos45 = new ArrayList<>();
        String nodo_name145 = "Genre"
        HashMap<String, String> personAttributesD = new HashMap<>();
        personAttributesA.put("genre", "Accion");


         ArrayList<HashMap<String, String>> atributos46 = new ArrayList<>();
        String nodo_name146 = "User"
        HashMap<String, String> useratt = new HashMap<>();
        useratt.put("nombre", "juan");
        useratt.put("userid", "21");



        HashMap<String, String> relacted = new HashMap<>();
        relacted.put("role", "string");
        String relacion1 = "ACTED_IN"

        String DIRECTED = "DIRECTED"
         HashMap<String, String> EC = new HashMap<>();
        EC.put("role", "string");


         String rated = "RATED"
            HashMap<String, String> ratin = new HashMap<>();
        EC.put("rating", "0.5");
         EC.put("timestamp", "2024-12-1");


         String rated = "in_genre"







        try (var driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword))) {
            driver.verifyConnectivity();
            System.out.println("Connection established.");

            System.out.println("Deleting DB...");
						deleteDB(driver);

						final User[] users = new User[] {
							new User("Flavio", "12345"),
							new User("Jose", "668"),
							new User("Andre", "50"),
							new User("Rayo", "0909"),
							new User("Juan", "8765"),
						};

						final RatedRelation[] relations = new RatedRelation[] {
							new RatedRelation(3, 1234556),
							new RatedRelation(4, 432343),
							new RatedRelation(1, 98787656),
							new RatedRelation(5, 12323454),
							new RatedRelation(2, 123323454),
							new RatedRelation(5, 12323),
							new RatedRelation(3, 87986745),
							new RatedRelation(4, 87673454),
							new RatedRelation(5, 9806752),
							new RatedRelation(4, 98012342),
						};

						final Movie[] movies = new Movie[] {
							new Movie("The Watchmen", 76, 2015, "Random Movie 1"),
							new Movie("The Lego Movie", 30, 2005, "Random Movie 2"),
						};

            System.out.println("Creating users...");
						for (int i = 0; i< users.length; i++) {
							createUser(driver, users[i]);
						}

            System.out.println("Creating movies...");
						for (int i = 0; i< movies.length; i++) {
							createMovie(driver, movies[i]);
						}

            System.out.println("Creating relations...");
						for (int i = 0; i< relations.length; i++) {
							int userIdx = (i >= users.length) ? i % users.length : i;
							int moviesIdx = (i >= movies.length) ? i % movies.length : i;
							createRatedConnection(driver, users[userIdx], movies[moviesIdx], relations[i]);
						}

						System.out.println("Finding user based on review...");
						var foundUser = findUserBasedOnReview(driver, relations[0]);
						System.out.println("Found user: " + foundUser.Name + " " + foundUser.UserId);
        }
    }
}
