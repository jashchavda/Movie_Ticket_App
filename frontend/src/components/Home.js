import React, { useEffect, useState } from "react";
import axios from "axios";
import MovieCard from "./MovieCard";
import "../styles/Home.css"; 

const Home = () => {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    axios.get("/api/movies").then((res) => setMovies(res.data));
  }, []);

  return (
    <div className="home-container">
      <h2 className="home-title">Available Movies 🎬</h2>

      <div className="movie-grid">
        {movies.map((m) => (
          <MovieCard key={m.id} movie={m} />
        ))}
      </div>
    </div>  
  );
};

export default Home;
