import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Theater {
    private int id;
    private List<Screen> screens;

    public Theater(int id, List<Screen> screens) {
        this.id = id;
        this.screens = screens;
    }

    public int getId() {
        return id;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public List<Show> searchShows(Movie movie) {

        List<Show> shows = new ArrayList<>();
        for (Screen screen : screens) {
            shows.addAll(screen.searchShows(movie));
        }

        return shows;
    }
}
