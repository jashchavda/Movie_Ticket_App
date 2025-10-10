import React from "react";

const Navbar = () => {
  const handleLogout = async () => {
    await fetch("/api/auth/logout", { method: "POST", credentials: "include" });
    localStorage.removeItem("user");
    window.location = "/";
  };

  return (
    <div style={{ display: "flex", justifyContent: "space-between", background: "#ddd", padding: "10px" }}>
      <h3>🎬 Movie Ticket Booking</h3>
      <div>
        <a href="/home" style={{ marginRight: "15px" }}>Home</a>
        <a href="/bookings" style={{ marginRight: "15px" }}>Bookings</a>
        <button onClick={handleLogout}>Logout</button>
      </div>
    </div>
  );
};

export default Navbar;
