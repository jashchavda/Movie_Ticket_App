import React, { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "../styles/SeatSelection.css";

const ROWS = 8; // seats layout (simple)
const COLS = 12;

export default function SeatSelection() {
  const { movieId, bookingId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const passedMovie = location.state?.movie || null;
  const currentSeats = location.state?.currentSeats || 0;

  const [movie, setMovie] = useState(passedMovie);
  const [selected, setSelected] = useState(new Set());
  const [loading, setLoading] = useState(!passedMovie);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  // ✅ New state to store the selected show timing
  const [showTime, setShowTime] = useState("");

  // Predefined available show times
  const availableTimes = ["09:00 AM", "12:30 PM", "03:00 PM", "06:45 PM", "09:15 PM"];

  // Build seat ids 1..N once
  const allSeatIds = useMemo(() => {
    return Array.from({ length: ROWS * COLS }, (_, i) => i + 1);
  }, []);

  // Fetch movie if not passed from previous page
  useEffect(() => {
    if (movie) return;
    (async () => {
      try {
        const res = await axios.get("/api/movies");
        const found = res.data.find((m) => String(m.id) === String(movieId));
        setMovie(found || null);
      } catch (e) {
        setError("Failed to load movie.");
      } finally {
        setLoading(false);
      }
    })();
  }, [movie, movieId]);

  // Preselect seats if updating
  useEffect(() => {
    if (currentSeats > 0) {
      const s = new Set(allSeatIds.slice(0, currentSeats));
      setSelected(s);
    }
  }, [currentSeats, allSeatIds]);

  const toggleSeat = (id) => {
    const s = new Set(selected);
    if (s.has(id)) s.delete(id);
    else s.add(id);
    setSelected(s);
  };

  const totalPrice = movie ? selected.size * Number(movie.price) : 0;

  const handleConfirm = async () => {
    if (!movie) return;
    if (selected.size === 0) {
      alert("Please select at least 1 seat.");
      return;
    }
    if (!showTime) {
      alert("Please select a show time.");
      return;
    }

    setSubmitting(true);
    try {
      if (bookingId) {
        // Update existing booking
        await axios.put(
          `/api/bookings/${bookingId}`,
          { seats: selected.size, showTime },
          { withCredentials: true }
        );
        alert("Booking updated!");
      } else {
        // Create new booking with showTime
        await axios.post(
          "/api/bookings",
          { movieId: movie.id, seats: selected.size, showTime },
          { withCredentials: true }
        );
        alert("Booking confirmed!");
      }
      navigate("/bookings");
    } catch (e) {
      alert("Error saving booking.");
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <div className="seat-page"><p>Loading...</p></div>;
  if (error || !movie) return <div className="seat-page"><p>{error || "Movie not found."}</p></div>;

  return (
    <div className="seat-page">
      <div className="movie-header">
        <img src={movie.imageUrl} alt={movie.title} className="poster" />
        <div className="movie-info">
          <h1 className="title">{movie.title}</h1>
          <p className="meta"><strong>Price:</strong> ₹{movie.price}</p>
          <p className="meta"><strong>Rating:</strong> ⭐ {movie.rating}</p>
        </div>
      </div>

      {/* ✅ Show time selection */}
      <div className="time-selection">
        <h3>Select Show Time:</h3>
        <div className="time-options">
          {availableTimes.map((time) => (
            <button
              key={time}
              className={`time-btn ${showTime === time ? "selected" : ""}`}
              onClick={() => setShowTime(time)}
            >
              {time}
            </button>
          ))}
        </div>
      </div>

      <h2 className="choose-title">Choose your seats by clicking on the available seats</h2>

      <div className="screen">SCREEN</div>

      <div className="seats-grid" style={{ gridTemplateColumns: `repeat(${COLS}, 1fr)` }}>
        {allSeatIds.map((id) => (
          <button
            key={id}
            type="button"
            className={`seat ${selected.has(id) ? "selected" : ""}`}
            onClick={() => toggleSeat(id)}
          >
            {/* Seat button */}
          </button>
        ))}
      </div>

      <div className="legend">
        <span><span className="dot available" /> Available</span>
        <span><span className="dot selected" /> Selected</span>
      </div>

      <div className="footer">
        <p>
          You have selected <strong>{selected.size}</strong> seat(s)
          {selected.size > 0 && <> — total <strong>₹{totalPrice}</strong></>}
        </p>
        <button className="confirm-btn" onClick={handleConfirm} disabled={submitting || selected.size === 0}>
          {bookingId ? "Update Booking" : "Confirm Booking"}
        </button>
      </div>
    </div>
  );
}
