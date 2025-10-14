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

          <div className="booking-info">
            <p><strong>Seats:</strong> {b.seats}</p>

            
            {b.seatNumbers && (
              <p style={{ color: "#444", fontWeight: 500 }}>
                <strong>Seat Numbers:</strong> {b.seatNumbers}
              </p>
            )}

            
            {b.showTime && (
              <p>
                <strong>Show Time:</strong> {b.showTime}
              </p>
            )}

            <p>
              <strong>Total Price:</strong> ₹{b.totalPrice}
            </p>
          </div>

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
