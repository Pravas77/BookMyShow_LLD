import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class BookMyShow {
    private TheaterFactory theaterFactory;
    private BookingManager bookingManager;

    public BookMyShow(TheaterFactory theaterFactory, BookingManager bookingManager) {
        this.theaterFactory = theaterFactory;
        this.bookingManager = bookingManager;
    }

    public Set<Movie> searchMovies(City city, LocalDate localDate) {
        return theaterFactory.searchMovies(city, localDate);
    }

    public List<Theater> searchTheater(City city, Movie movie, LocalDate localDate) {
        return theaterFactory.searchTheater(city, movie, localDate);
    }

    public List<Show> SearchShows(Theater theater, Movie movie, LocalDate localDate) {
        return theaterFactory.getReleventShows(theater, movie, localDate);
    }

    public Booking bookSeats(User user, Show show, List<Seat> seats,PaymentStrategy paymentStrategy){
        return bookingManager.bookSeats(user, show, seats, paymentStrategy);
    }
    public void cancelBooking(Booking booking){
        bookingManager.cancelBooking(booking);
    }
}
