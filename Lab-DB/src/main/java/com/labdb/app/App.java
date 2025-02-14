package com.labdb.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.util.Map.entry;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;

public class App {


    // public static String concatenateAttributes(HashMap<String, String> atributos) {
    //     // Initialize an empty string for concatenation
    //     StringBuilder concatenatedString = new StringBuilder("{ ");

    //     // Iterate over each key-value pair in the HashMap
    //     for (Map.Entry<String, String> entry : atributos.entrySet()) {
    //         concatenatedString.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
    //     }

    //     // Remove the last ", " if the string is not empty
    //     if (concatenatedString.length() > 2) {
    //         concatenatedString.setLength(concatenatedString.length() - 2);
    //     }
    //     concatenatedString.append(" }");
    //     return concatenatedString.toString();
    // }

  // public static void creategen(Driver driver, String node_name, String node_name_att, String relation, String relation_att ,String node_namefinal, String node_name_attfinal) {
  //   var result = driver.executableQuery("MATCH (m:" + node_name + " " + node_name_att + "), " +
  //                           "(u:" + node_namefinal + " " + node_name_attfinal + ") " +
  //                           "CREATE (u)-[r:" + relation + " " + relation_att + "]->(m)")
  //   .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
  //   .execute();
	// }



	public static void createUser(Driver driver, User user) {
		var result = driver.executableQuery("MERGE (u:User {name: $name, userId: $userId})")
			.withParameters(Map.of("name", user.Name, "userId", user.UserId))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

	public static void createMovie(Driver driver, Movie movie) {
    var result = driver.executableQuery(
        "MERGE (u:Movie {title: $title, tmdbId: $tmdbId, release: $release, imdbRating: $imdbRating, " +
        "movieId: $movieId, year: $year, imdbId: $imdbId, runtime: $runtime, countries: $countries, " +
        "imdbVotes: $imdbVotes, url: $url, revenue: $revenue, plot: $plot, poster: $poster, " +
        "budget: $budget, languages: $languages})"
    )
    .withParameters(Map.ofEntries(
      entry("title", movie.Title),
      entry("tmdbId", movie.tmdbld),
      entry("release", movie.release),
      entry("imdbRating", movie.IMDRating),
      entry("movieId", movie.MovieId),
      entry("year", movie.Year),
      entry("imdbId", movie.imdbid),
      entry("runtime", movie.runtime),
      entry("countries", movie.countries),
      entry("imdbVotes", movie.imdbVotes),
      entry("url", movie.url),
      entry("revenue", movie.revenue),
      entry("plot", movie.Plot),
      entry("poster", movie.poster),
      entry("budget", movie.budget),
      entry("languages", movie.languages)
    ))
    .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
    .execute();
}

public static void createGenre(Driver driver, Genre genre) {
  var result = driver.executableQuery(
    "Merge (u:Genre {name: $name})"
  ).withParameters(Map.of(
    "name", genre.name
  ))
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
		driver.executableQuery("MATCH (n) DETACH DELETE n")
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();
	}

	public static User findUserBasedOnReview(Driver driver, RatedRelation rel) {
		var result = driver.executableQuery("MATCH (u:User) -[r:Rated {rating: $rating, timestamp: $timestamp}]->(m:Movie) RETURN u.name as name")
			.withParameters(Map.of("rating", rel.Rating, "timestamp", rel.Timestamp))
			.withConfig(QueryConfig.builder().withDatabase("neo4j").build())
			.execute();

		var records = result.records();
		var r = records.get(0);
		User user = new User(r.get("name").asString(), null);

		return user;
	}

  public static void createPerson(Driver driver, String pType, Person person) {
    driver.executableQuery("MERGE (:"+pType+" {name: $name, tmdbid: $tmdbid, born: $born, died: $died, bornIn: $bornIn, url: $url, imdbId: $imdbId, bio: $bio, poster: $poster})")
      .withParameters(Map.of("name", person.Name, "tmdbid", person.TMDBId, "born", person.Born, "died", person.Died, "bornIn", person.BornIn, "url", person.Url, "imdbId", person.IMDBId, "bio", person.Bio, "poster", person.Poster))
      .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
      .execute();
  }

  public static void createPersonMovieRelation(Driver driver, Person person, Movie movie, String role, String personNodeType, String relationType) {
    driver.executableQuery("MATCH (u:"+personNodeType+" {name: $name, tmdbid: $tmdbid, born: $born, died: $died, bornIn: $bornIn, url: $urlPerson, imdbId: $imdbIdPerson, bio: $bio, poster: $posterPerson}), (u:Movie {title: $title, tmdbId: $tmdbId, release: $release, imdbRating: $imdbRating, " +
        "movieId: $movieId, year: $year, imdbId: $imdbId, runtime: $runtime, countries: $countries, " +
        "imdbVotes: $imdbVotes, url: $url, revenue: $revenue, plot: $plot, poster: $poster, " +
        "budget: $budget, languages: $languages}) CREATE (u)-[:"+relationType+" {role: $role}]->(m)")
      .withParameters(Map.ofEntries(
        entry("name", person.Name),
        entry("tmdbid", person.TMDBId),
        entry("born", person.Born),
        entry("died", person.Died),
        entry("bornIn", person.BornIn),
        entry("urlPerson", person.Url),
        entry("imdbIdPerson", person.IMDBId),
        entry("bio", person.Bio),
        entry("posterPerson", person.Poster),
        entry("title", movie.Title),
        entry("tmdbId", movie.tmdbld),
        entry("release", movie.release),
        entry("imdbRating", movie.IMDRating),
        entry("movieId", movie.MovieId),
        entry("year", movie.Year),
        entry("imdbId", movie.imdbid),
        entry("runtime", movie.runtime),
        entry("countries", movie.countries),
        entry("imdbVotes", movie.imdbVotes),
        entry("url", movie.url),
        entry("revenue", movie.revenue),
        entry("plot", movie.Plot),
        entry("poster", movie.poster),
        entry("budget", movie.budget),
        entry("languages", movie.languages),
        entry("role", role)
      ))
      .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
      .execute();
  }

  public static void createMovieGenreRelation(Driver driver, Movie movie, Genre genre, String relationType) {
    driver.executableQuery("MATCH (m:Movie {title: $title, tmdbId: $tmdbId}), " +
            "(g:Genre {name: $genreName}) " +
            "CREATE (m)-[:" + relationType + "]->(g)")
        .withParameters(Map.ofEntries(
            entry("title", movie.Title),
            entry("tmdbId", movie.tmdbld),
            entry("genreName", genre.name)
        ))
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

            System.out.println("Deleting DB...");
						deleteDB(driver);

            System.out.println("Exercise 1!");

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
    new Movie(
        "Cyber Warriors", 101, LocalDate.of(2022, 5, 15), 7.8f, 1, 2022, 
        98765, 120, List.of("USA", "Canada"), 15000, "https://example.com/cyber-warriors", 
        500000000, "A futuristic battle between AI and humans.", "https://example.com/poster1.jpg", 
        200000000, List.of("English", "French")
    ),
    new Movie(
        "Galaxy Pirates", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
    ),
    new Movie(
        "Dinosaur Invasion", 309, LocalDate.of(2010, 3, 10), 5.5f, 3, 2010, 
        76543, 95, List.of("USA", "Mexico"), 5000, "https://example.com/dinosaur-invasion", 
        200000000, "A secret experiment brings dinosaurs back to modern times.", "https://example.com/poster3.jpg", 
        100000000, List.of("English")
    ),
    new Movie(
        "Quantum Thieves", 412, LocalDate.of(2023, 11, 5), 8.2f, 4, 2023, 
        65432, 130, List.of("Germany", "Japan"), 12000, "https://example.com/quantum-thieves", 
        600000000, "A team of thieves uses quantum technology to steal the impossible.", "https://example.com/poster4.jpg", 
        250000000, List.of("English", "Japanese")
    ),
    new Movie(
        "Shadow Samurai", 528, LocalDate.of(2007, 6, 30), 7.0f, 5, 2007, 
        54321, 105, List.of("Japan", "South Korea"), 7000, "https://example.com/shadow-samurai", 
        300000000, "An ancient warrior fights against a corrupt empire.", "https://example.com/poster5.jpg", 
        180000000, List.of("Japanese", "Korean")
    )
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
						System.out.println("Found user: " + foundUser.Name);



           System.out.println("Exercise 2!");
           final Person[] persons = new Person[] {
            new Person("Flavio", 12343, LocalDateTime.now(), LocalDateTime.now(), "Guatemala", "www.google.com", "ABCD", "laksjdflkajsdlkfjasd", "ABC"),
            new Person("Jose", 2334, LocalDateTime.now(), LocalDateTime.now(), "Guatemala", "www.google.com", "ABCD", "laksjdflkajsdlkfjasd", "ABC"),
            new Person("Andre", 9867, LocalDateTime.now(), LocalDateTime.now(), "Guatemala", "www.google.com", "ABCD", "laksjdflkajsdlkfjasd", "ABC"),
           };

           for (int index = 0; index < persons.length; index++) {
            var pType = "";
            switch (index) {
              case 0:
                pType = "PersonActorDirector";
                break;
              case 1:
                pType = "PersonActor";
                break;
              case 2:
                pType = "PersonDirector";
                break;
            
              default:
                break;
            }

            createPerson(driver, pType, persons[index]);
           }

          createUser(driver, new User("ElrohirGT", "USER1"));
          createMovie(driver, new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ));


          createPersonMovieRelation(driver,   
          persons[0],
          new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ), "director_actor", "ActorDirector", "Directed_IN");
          createPersonMovieRelation(driver,   
           persons[0],
          new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ), "director_actor", "ActorDirector", "Acted_IN");
          createPersonMovieRelation(driver,   
            persons[1],
            new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ), "Actor", "Actor", "Acted_In");
          createPersonMovieRelation(driver,   
          new Person("Flavio", 12343, LocalDateTime.now(), LocalDateTime.now(), "Guatemala", "www.google.com", "ABCD", "laksjdflkajsdlkfjasd", "ABC"),
          new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ), "director_actor", "ActorDirector", "Acted_IN");
          createPersonMovieRelation(driver,   
          persons[2],
          new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ), "Director", "Director", "Directed_in");

          createGenre(driver, new Genre("Adventure"));

          createMovieGenreRelation(driver, new Movie(
            "Galaxy Pirates 2", 205, LocalDate.of(2018, 8, 21), 6.9f, 2, 2018, 
        87654, 110, List.of("UK", "Australia"), 8000, "https://example.com/galaxy-pirates", 
        350000000, "A group of space outlaws searches for the ultimate treasure.", "https://example.com/poster2.jpg", 
        150000000, List.of("English", "Spanish")
          ), new Genre("Adventure"), "in_genre");

        }
    }
}
