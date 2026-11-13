# BookMyShow (Movie Ticket Booking) – Low Level Design

Low-level design of a movie ticket booking system in Java, similar to BookMyShow. Users search shows by city and movie and book seats. The main focus is **concurrent seat booking**: two users can never book the same seat, and the system cannot deadlock.

## Features
- Search shows by city and movie across all theatres and screens
- Seat categories (`SILVER`, `GOLD`, `PLATINUM`) with per-show pricing
- Book multiple seats in one booking
- Pluggable payment (mock Razorpay)
- Thread-safe booking with per-seat locks

## Design

| Class | Responsibility |
|---|---|
| `BookMyShow` | Facade: search shows and book seats |
| `TheaterManager` | Maps each `City` to its `Theater`s and searches shows |
| `Theater` → `Screen` → `Show` | Theatre has screens, screen has seats and shows |
| `Show` | Date, time, movie, price per category, seat status per seat and a `StampedLock` per seat |
| `BookingManager` | Booking flow: availability check, locking, payment and confirmation |
| `Seat`, `SeatCatogary`, `SeatStatus` | Seat with category and booking status |
| `PaymentStrategy` / `RazorpayPaymentStrategy` | Payment abstraction |
| `Booking`, `User`, `Movie`, `City` | Domain objects |

```
City ─▶ Theater ─▶ Screen ─▶ Show ─▶ Seat status + StampedLock (per seat)
```

## Booking flow and concurrency
1. **Sort seats by id.** All threads lock seats in the same order, which removes the chance of a deadlock (for example, user A wants seats 1 and 2 while user B wants seats 2 and 1).
2. **Optimistic read (fast path).** `StampedLock.tryOptimisticRead()` checks whether all selected seats are free without taking a lock. If any seat is already booked, the request fails immediately without blocking others.
3. **Write-lock every selected seat** in sorted order.
4. **Check again under the lock.** Another thread may have booked a seat between steps 2 and 3, so availability is verified again.
5. **Calculate the price, take payment, then mark the seats `BOOKED`.**
6. **Release every lock in `finally`**, even if payment fails.

Locks are **per seat**, not per show, so users booking different seats in the same show do not block each other.

## Design patterns
- **Strategy** – payment provider is an interface.
- **Facade** – `BookMyShow` hides the managers behind one entry point.

## How to run
Requires Java 17+.
```bash
javac -d out src/*.java
java -cp out Main
```
`Main` sets up one theatre in Delhi with two shows and searches for movies. Then two threads book overlapping seats in the same show at the same time (seats 1 & 2 and seats 2 & 3). Only one booking succeeds and the other is rejected because seat 2 is already taken.

## Possible extensions
- Temporarily hold seats with a timeout while the user pays, instead of holding the lock during payment
- Booking cancellation and refunds
- Seat map view per show
