import React, { useState } from "react";
import axios from "axios";
import "../styles/AuthForm.css";


axios.defaults.baseURL = process.env.REACT_APP_API_URL || "http://localhost:8080";

const AuthForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isLogin, setIsLogin] = useState(true);
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const url = isLogin ? "/api/auth/login" : "/api/auth/register";
      const res = await axios.post(url, { username, password }, { withCredentials: true });

      if (isLogin) {
        localStorage.setItem("user", JSON.stringify(res.data));
        window.location = "/home";
      } else {
        setMessage("✅ Registered successfully! Please log in.");
        setIsLogin(true);
      }
    } catch (err) {
      const msg = err.response?.data || "❌ Something went wrong";

      if (msg.toLowerCase().includes("invalid")) {
        alert("❌ Wrong password! Try again.");
      }

      setMessage(msg);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1 className="app-title">🎬 Movie Ticket Booking</h1>
        <p className="app-tagline">Book your favorite movies instantly!</p>

        <h2>{isLogin ? "Login" : "Register"}</h2>

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="auth-input"
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="auth-input"
            required
          />

          <button type="submit" className="auth-button">
            {isLogin ? "Login" : "Register"}
          </button>
        </form>

        {message && <p style={{ color: "#ffcc00", marginTop: "10px" }}>{message}</p>}

        <div className="toggle-link" onClick={() => setIsLogin(!isLogin)}>
          {isLogin ? "New user? Register" : "Already registered? Login"}
        </div>
      </div>
    </div>
  );
};

export default AuthForm;
