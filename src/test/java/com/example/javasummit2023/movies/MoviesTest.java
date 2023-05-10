package com.example.javasummit2023.movies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.Driver;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MoviesTest {

	@Inject
	private MovieRepository movieRepository;

	@Inject
	Driver driver;

	@BeforeAll
	void setupData() {
		driver.executableQuery("CREATE (n:Movie {title: 'The Matrix'})")
			.execute();
	}

	@Test
	void shouldCreateMovie() {
		var movie = movieRepository.save(new Movie("Bullet Train", "A movie about vengeance."));
		assertThat(movie.getId()).isNotNull();
	}

	@Test
	void findByTitleShouldWork() {
		var title = "The Matrix";

		assertThat(movieRepository.findByTitle(title))
			.isPresent()
			.hasValueSatisfying(movie -> {
				assertThat(movie.getTitle()).isEqualTo(title);
				assertThat(movie.getId()).isNotNull();
				assertThat(movie.getId()).isNotNegative();
			});
	}
}
