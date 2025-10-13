import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import axios from "axios";
import AuthForm from "./components/AuthForm";
import Home from "./components/Home";
import BookingsPage from "./components/BookingsPage";
import Navbar from "./components/Navbar";
import SeatSelection from "./components/SeatSelection"; // ✅ NEW IMPORT

// ✅ Axios interceptor to handle session expiry (401 Unauthorized)
axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      const msg = error.response?.data?.toLowerCase() || "";

      // ✅ Only show session expiry alert if it's NOT a wrong password case
      if (!msg.includes("invalid")) {
        alert("⚠️ Session expired. Please log in again.");
        localStorage.removeItem("user");
        window.location = "/";
      }
    }
    return Promise.reject(error);
  }
);

function App() {
  // ✅ React state to re-render when user logs in/out
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem("user")));

  // ✅ Keep user state in sync with localStorage
  useEffect(() => {
    const handleStorageChange = () => {
      const updatedUser = JSON.parse(localStorage.getItem("user"));
      setUser(updatedUser);
    };
    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  return (
    <Router>
      {/* ✅ Navbar now gets setUser to handle instant logout */}
      {user && <Navbar setUser={setUser} />}

      <Routes>
        {/* Auth routes */}
        <Route path="/" element={user ? <Navigate to="/home" /> : <AuthForm />} />

        {/* Main app routes */}
        <Route path="/home" element={user ? <Home /> : <Navigate to="/" />} />
        <Route path="/bookings" element={user ? <BookingsPage /> : <Navigate to="/" />} />

        {/* ✅ Seat selection routes */}
        <Route path="/book/:movieId" element={user ? <SeatSelection /> : <Navigate to="/" />} />
        <Route path="/book/:movieId/edit/:bookingId" element={user ? <SeatSelection /> : <Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;
