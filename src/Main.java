import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello");

        Show show1 = new Show(
                LocalDate.of(2027, 1, 1),
                LocalTime.of(12, 0),
                new Movie("titanic"),
                null,
                Map.of(
                        SeatCatogary.SILVER, 100,
                        SeatCatogary.GOLD, 200,
                        SeatCatogary.PLATINUM, 500
                ));

        Show show2 = new Show(
                LocalDate.of(2027, 1, 2),
                LocalTime.of(12, 0),
                new Movie("avatar"),
                null,
                Map.of(
                        SeatCatogary.SILVER, 100,
                        SeatCatogary.GOLD, 200,
                        SeatCatogary.PLATINUM, 500
                ));


        Seat seat1 = new Seat(1, SeatCatogary.SILVER);
        Seat seat2 = new Seat(2, SeatCatogary.SILVER);
        Seat seat3 = new Seat(3, SeatCatogary.PLATINUM);

        Screen screen1 = new Screen(1, List.of(seat1, seat2, seat3), List.of(show1, show2));
        show1.setScreen(screen1);
        show2.setScreen(screen1);

        Theater theater1 = new Theater(1, List.of(screen1));

        TheaterManager theaterManager = new TheaterManager(
                Map.of(City.DELHI, List.of(theater1))
        );

        User user1 = new User(1);
        User user2 = new User(2);

        BookMyShow bookMyShow = new BookMyShow(theaterManager, new BookingManager(), List.of(user1, user2));

        System.out.println(bookMyShow.searchShows(City.DELHI, new Movie("titanic")));
        System.out.println(bookMyShow.searchShows(City.DELHI, new Movie("xyz")));
        System.out.println(bookMyShow.searchShows(City.DELHI, new Movie("avatar")));

        Thread thread1 = new Thread(() -> {
            Booking booking = bookMyShow.bookSeats(user1, show1, new ArrayList<>(List.of(seat1, seat2)), new RazorpayPaymentStrategy());
            System.out.println(booking);
        });

        Thread thread2 = new Thread(() -> {
            Booking booking = bookMyShow.bookSeats(user2, show1, new ArrayList<>(List.of(seat2, seat3)), new RazorpayPaymentStrategy());
            System.out.println(booking);
        });

        thread1.start();
//        Thread.sleep(100);
        thread2.start();

    }
}