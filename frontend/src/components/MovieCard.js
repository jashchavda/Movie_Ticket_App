import React, { useState } from "react";
import axios from "axios";

const MovieCard = ({ movie }) => {
  const [seats, setSeats] = useState(1);
  const [message, setMessage] = useState("");

  const handleBook = async () => {
    try {
      await axios.post("/api/bookings", { movieId: movie.id, seats }, { withCredentials: true });
      setMessage("🎟 Movie booked successfully!");
    } catch (err) {
      setMessage("Error booking movie");
    }
  };

  return (
    <div style={{ border: "1px solid gray", borderRadius: "10px", padding: "10px", width: "250px", textAlign: "center" }}>
      <img src={movie.imageUrl} alt={movie.title} width="200" height="250" /><br/>
      <h3>{movie.title}</h3>
      <p>Price: ₹{movie.price}</p>
      <p>Rating: ⭐{movie.rating}</p>
      <input
        type="number"
        min="1"
        value={seats}
        onChange={(e) => setSeats(e.target.value)}
        style={{ width: "50px" }}
      />
      <button onClick={handleBook}>Book</button>
      <p style={{ color: "green" }}>{message}</p>
    </div>
  );
};

export default MovieCard;
