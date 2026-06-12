import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello");

        Seat seat1 = new Seat(1, SeatCatogary.SILVER);
        Seat seat2 = new Seat(2, SeatCatogary.GOLD);
        Seat seat3 = new Seat(3, SeatCatogary.PLATINUM);

        List<Seat> seats1 = new ArrayList<>(List.of(seat1, seat2, seat3));
        Screen screen1 = new Screen(1, seats1);

        Theater theater1 = new Theater(1, new ArrayList<>(List.of(screen1)));

        City city = City.DELHI;
        TheaterFactory theaterFactory = new TheaterFactory(Map.of(city, List.of(theater1)));

        BookingManager bookingManager = new BookingManager();

        BookMyShow bookMyShow = new BookMyShow(theaterFactory, bookingManager);


        // addind show in theater 1
        Movie movie = new Movie("Titanic");

        Map<SeatCatogary,Integer> priceStructure = new EnumMap<>(SeatCatogary.class);
        priceStructure.put(SeatCatogary.SILVER,200);
        priceStructure.put(SeatCatogary.GOLD,300);
        priceStructure.put(SeatCatogary.PLATINUM,500);

        Show show1 = new Show(LocalDate.of(2026, 12, 5), LocalTime.of(14, 0), movie, screen1, priceStructure);
        Show show2 = new Show(LocalDate.of(2026, 12, 5), LocalTime.of(17, 0), movie, screen1, priceStructure);
        screen1.addShow(show1);
        screen1.addShow(show2);


        // Client
        User user1 = new User(1);
        User user2 = new User(2);

        Set<Movie> movies = bookMyShow.searchMovies(City.DELHI, LocalDate.of(2026, 12, 5));
        for (Movie movie1 : movies) System.out.println(movie1.getName());

        List<Theater> theaters = bookMyShow.searchTheater(City.DELHI, movies.iterator().next(), LocalDate.of(2026, 12, 5));
        for (Theater theater : theaters) System.out.println(theater.getId());

        List<Show> shows = bookMyShow.SearchShows(theaters.get(0), movies.iterator().next(), LocalDate.of(2026, 12, 5));
        for (Show show : shows) System.out.println(show.getLocalTime());

        Thread thread1 = new Thread(() -> {
            Booking booking = bookMyShow.bookSeats(
                    user1,
                    shows.get(0),
                    new ArrayList<>(List.of(seat1, seat2)),
                    new RazorpayPaymentStrategy());

            System.out.println("User id who booked " + booking.getUser().getId() + "        " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            bookMyShow.cancelBooking(booking);
//            System.out.println("Booking canceled Uesr id " + booking.getUser().getId() + "        " + Thread.currentThread().getName());
        });

        Thread thread2 = new Thread(() -> {
            Booking booking = bookMyShow.bookSeats(
                    user2,
                    shows.get(0),
                    new ArrayList<>(List.of(seat1,seat3)),
                    new RazorpayPaymentStrategy());

            System.out.println("User id who booked " + booking.getUser().getId() + "        " + Thread.currentThread().getName());
        });

        thread1.start();
//        Thread.sleep(6000);
        thread2.start();

    }
}