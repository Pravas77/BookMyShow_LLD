import java.time.LocalDate;
import java.util.*;

public class TheaterFactory {
    private Map<City, List<Theater>> factory;

    public TheaterFactory(Map<City, List<Theater>> factory) {
        this.factory = factory;
    }

    public List<Show> getReleventShows(Theater theater, Movie movie, LocalDate localDate) {
        return theater.getReleventShows(movie, localDate);
    }

    public List<Theater> searchTheater(City city, Movie movie, LocalDate localDate) {
        List<Theater> theaters = factory.getOrDefault(city, new ArrayList<>());
        List<Theater> result = new ArrayList<>();

        for (Theater theater : theaters) {

            boolean isAvailable = false;
            for (Screen screen : theater.getScreens()) {
                for (Show show : screen.getShows()) {
                    if (show.getMovie().getName().equals(movie.getName()) && show.getLocalDate().equals(localDate))
                        isAvailable = true;
                }
            }

            if (isAvailable == true) result.add(theater);
        }
        return result;
    }

    public Set<Movie> searchMovies(City city, LocalDate localDate) {

        List<Theater> theaters = factory.getOrDefault(city, new ArrayList<>());
        Set<Movie> movies = new HashSet<>();

        for (Theater theater : theaters) {
            for (Screen screen : theater.getScreens()) {
                for (Show show : screen.getShows()) {
                    if (show.getLocalDate().equals(localDate)) movies.add(show.getMovie());
                }
            }
        }

        return movies;
    }
}
