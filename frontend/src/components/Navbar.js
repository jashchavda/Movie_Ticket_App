import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

const Navbar = ({ setUser }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null); 
    navigate("/"); 
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <span className="logo">🎬 Movie Ticket Booking</span>
      </div>

      <div className="navbar-right">
        <Link to="/home" className="nav-btn">Home</Link>
        <Link to="/bookings" className="nav-btn">Bookings</Link>
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>
    </nav>
  );
};

export default Navbar;
