import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import AuthForm from "./components/AuthForm";
import Home from "./components/Home";
import BookingsPage from "./components/BookingsPage";
import Navbar from "./components/Navbar";

function App() {
  const user = JSON.parse(localStorage.getItem("user"));

  return (
    <Router>
      {user && <Navbar />}
      <Routes>
        <Route path="/" element={user ? <Navigate to="/home" /> : <AuthForm />} />
        <Route path="/home" element={user ? <Home /> : <Navigate to="/" />} />
        <Route path="/bookings" element={user ? <BookingsPage /> : <Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;
