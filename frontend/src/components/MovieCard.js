
import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/MovieCard.css";

const MovieCard = ({ movie }) => {
  const navigate = useNavigate();

  const handleBook = () => {
    navigate(`/book/${movie.id}`, { state: { movie } });
  };

  return (
    <div className="movie-card-container"> 
      <img
        src={movie.imageUrl}
        alt={movie.title}
        width="200"
        height="250"
        style={{ borderRadius: "8px" }}
      />
      <h3>{movie.title}</h3>
      <p>Price: ₹{movie.price}</p>
      <p>Rating: ⭐{movie.rating}</p>
      <button onClick={handleBook}>Book</button>
    </div>
  );
};

export default MovieCard;
