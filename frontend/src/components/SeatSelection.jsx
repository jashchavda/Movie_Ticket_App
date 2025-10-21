import React, { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "../styles/SeatSelection.css";

const ROWS = 8;
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
  const [showTime, setShowTime] = useState("");

  
  const availableTimes = ["09:00 AM", "12:30 PM", "03:00 PM", "06:45 PM", "09:15 PM"];

  const allSeatIds = useMemo(() => Array.from({ length: ROWS * COLS }, (_, i) => i + 1), []);

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

  const toggleSeat = (id) => {
    const s = new Set(selected);
    if (s.has(id)) s.delete(id);
    else s.add(id);
    setSelected(s);
  };

  const getSeatLabels = () => {
    return Array.from(selected)
      .map((id) => {
        const row = String.fromCharCode(65 + Math.floor((id - 1) / COLS));
        const col = ((id - 1) % COLS) + 1;
        return `${row}${col}`;
      })
      .join(", ");
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

    const seatNumbers = getSeatLabels();
    setSubmitting(true);

    try {
      if (bookingId) {
        await axios.put(
          `/api/bookings/${bookingId}`,
          { seats: selected.size, showTime, seatNumbers },
          { withCredentials: true }
        );
        alert("Booking updated!");
      } else {
        await axios.post(
          "/api/bookings",
          { movieId: movie.id, seats: selected.size, showTime, seatNumbers },
          { withCredentials: true }
        );
        alert("Booking confirmed!");
      }
      navigate("/bookings");
    } catch (e) {
      alert("Error saving booking.");
      console.error(e);
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <div className="seat-page"><p>Loading...</p></div>;
  if (error || !movie) return <div className="seat-page"><p>{error || "Movie not found."}</p></div>;

  return (
    <div className="seat-page">
      <h2 className="choose-title">Choose Your Seats</h2>

      <div className="screen">SCREEN</div>

      
      <div className="time-selection">
        <h4>Select Show Time:</h4>
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

      <div className="seats-grid" style={{ gridTemplateColumns: `repeat(${COLS}, 1fr)` }}>
        {allSeatIds.map((id) => {
          const label = String.fromCharCode(65 + Math.floor((id - 1) / COLS)) + (((id - 1) % COLS) + 1);
          const isReserved = ["A5", "B3", "C2", "C11", "D9", "E10", "G10", "H11"].includes(label);
          return (
            <button
              key={id}
              type="button"
              className={`seat ${selected.has(id) ? "selected" : ""} ${isReserved ? "reserved" : ""}`}
              onClick={() => !isReserved && toggleSeat(id)}
            >
              {label}
            </button>
          );
        })}
      </div>

      <div className="legend">
        <span><span className="dot available" /> Available</span>
        <span><span className="dot selected" /> Selected</span>
        <span><span className="dot reserved" /> Reserved</span>
      </div>

      <div className="footer">
        <p>
          Selected seats: <strong>{selected.size}</strong> | Total: ₹{totalPrice}
        </p>
        <button className="confirm-btn" onClick={handleConfirm} disabled={submitting || selected.size === 0}>
          {bookingId ? "Update Booking" : "Confirm Booking"}
        </button>
      </div>
    </div>
  );
}
