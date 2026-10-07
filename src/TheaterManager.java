import java.time.LocalDate;
import java.util.*;

public class TheaterManager {
    private Map<City, List<Theater>> theaterManager;

    public TheaterManager(Map<City, List<Theater>> theaterManager) {
        this.theaterManager = theaterManager;
    }

    public List<Show> searchShows(City city, Movie movie) {

        List<Theater> theaters = theaterManager.getOrDefault(city, new ArrayList<>());
        List<Show> shows = new ArrayList<>();
        for (Theater theater : theaters) {
            shows.addAll(theater.searchShows(movie));
        }

        return shows;
    }

}
