import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../styles/BookingsPage.css";

const BookingsPage = () => {
  const [bookings, setBookings] = useState([]);
  const navigate = useNavigate();

  const fetchBookings = async () => {
    try {
      const res = await axios.get("/api/bookings", { withCredentials: true });
      setBookings(res.data);
    } catch (err) {
      console.error("Error fetching bookings:", err);
    }
  };

  useEffect(() => {
    fetchBookings();
  }, []);

  const cancelBooking = async (id) => {
    try {
      await axios.delete(`/api/bookings/${id}`, { withCredentials: true });
      fetchBookings();
    } catch (err) {
      console.error("Error cancelling booking:", err);
    }
  };

  return (
    <div className="bookings-container">
      <h2>Your Bookings</h2>
      {bookings.length === 0 && <p>No bookings yet.</p>}

      {bookings.map((b) => (
        <div key={b.id} className="booking-card">
          <h3>{b.movie.title}</h3>
          <p>Seats: {b.seats}</p>
          <p>Total Price: ₹{b.totalPrice}</p>
          {/* ✅ Show booked timing */}
          {b.showTime && <p>Show Time: {b.showTime}</p>}

          <div className="booking-buttons">
            <button
              className="update-btn"
              onClick={() =>
                navigate(`/book/${b.movie.id}/edit/${b.id}`, {
                  state: { movie: b.movie, currentSeats: b.seats },
                })
              }
            >
              Update
            </button>

            <button
              className="cancel-btn"
              onClick={() => cancelBooking(b.id)}
            >
              Cancel
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default BookingsPage;
