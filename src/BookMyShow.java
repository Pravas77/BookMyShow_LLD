import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookMyShow {
    private TheaterManager theaterManager;
    private BookingManager bookingManager;
    private List<User> users;

    public BookMyShow(TheaterManager theaterManager, BookingManager bookingManager, List<User> users) {
        this.theaterManager = theaterManager;
        this.bookingManager = bookingManager;
        this.users = users;
    }

    public List<Show> searchShows(City city, Movie movie) {
        return theaterManager.searchShows(city, movie);
    }

    public Booking bookSeats(User user, Show show, List<Seat> seats, PaymentStrategy paymentStrategy) {
        return bookingManager.bookSeats(user, show, seats, paymentStrategy);
    }
}
