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

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public List<Show> getReleventShows(Movie movie, LocalDate localDate) {

        List<Show> releventShows = new ArrayList<>();
        for (Screen screen : screens) {
            for (Show show : screen.getShows()) {
                if (show.getMovie().getName().equals(movie.getName()) && show.getLocalDate().equals(localDate))
                    releventShows.add(show);
            }
        }

        return releventShows;
    }


}
