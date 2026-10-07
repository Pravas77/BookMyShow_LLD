import java.util.Objects;

public class Seat {
    private int id;
    private SeatCatogary seatCatogary;

    public Seat(int id, SeatCatogary seatCatogary) {

        this.id = id;
        this.seatCatogary = seatCatogary;
    }

    public int getId() {
        return id;
    }

    public SeatCatogary getSeatCatogary() {
        return seatCatogary;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id == seat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                '}';
    }
}
