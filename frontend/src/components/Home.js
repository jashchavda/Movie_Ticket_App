import React, { useEffect, useState } from "react";
import axios from "axios";
import MovieCard from "./MovieCard";

const Home = () => {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    axios.get("/api/movies").then((res) => setMovies(res.data));
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2 style={{ textAlign: "center" }}>Available Movies</h2>
      <div style={{ display: "flex", gap: "20px", justifyContent: "center", flexWrap: "wrap" }}>
        {movies.map((m) => (
          <MovieCard key={m.id} movie={m} />
        ))}
      </div>
    </div>
  );
};

export default Home;
