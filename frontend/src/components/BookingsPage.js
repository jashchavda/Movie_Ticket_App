import React, { useEffect, useState } from "react";
import axios from "axios";

const BookingsPage = () => {
  const [bookings, setBookings] = useState([]);

  const fetchBookings = async () => {
    const res = await axios.get("/api/bookings", { withCredentials: true });
    setBookings(res.data);
  };

  useEffect(() => {
    fetchBookings();
  }, []);

  const updateSeats = async (id, seats) => {
    await axios.put(`/api/bookings/${id}`, { seats }, { withCredentials: true });
    fetchBookings();
  };

  const cancelBooking = async (id) => {
    await axios.delete(`/api/bookings/${id}`, { withCredentials: true });
    fetchBookings();
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Your Bookings</h2>
      {bookings.length === 0 && <p>No bookings yet.</p>}
      {bookings.map((b) => (
        <div key={b.id} style={{ border: "1px solid gray", margin: "10px", padding: "10px" }}>
          <h3>{b.movie.title}</h3>
          <p>Seats: {b.seats}</p>
          <p>Total Price: ₹{b.totalPrice}</p>
          <button onClick={() => updateSeats(b.id, b.seats + 1)}>+ Seat</button>
          <button onClick={() => updateSeats(b.id, b.seats - 1)} disabled={b.seats <= 1}>- Seat</button>
          <button onClick={() => cancelBooking(b.id)}>Cancel</button>
        </div>
      ))}
    </div>
  );
};

export default BookingsPage;
