import java.util.ArrayList;
import java.util.List;

public class Screen {

    private int id;
    private List<Seat> seats;
    private List<Show> shows = new ArrayList<>();

    public Screen(int id, List<Seat> seats) {
        this.id = id;
        this.seats = seats;
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

    public void addShow(Show show) {
        shows.add(show);
    }
}
