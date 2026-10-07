import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Screen {

    private int id;
    private List<Seat> seats;
    private List<Show> shows;

    public Screen(int id, List<Seat> seats, List<Show> shows) {
        this.id = id;
        this.seats = seats;
        this.shows = shows;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public List<Show> searchShows(Movie movie) {

        List<Show> relevantShows = new ArrayList<>();
        for (Show show : shows) {
            if (show.getMovie().equals(movie)) relevantShows.add(show);
        }

        return relevantShows;
    }
}
