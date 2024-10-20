import React, { useState } from "react";
import Login from "./Login";
import Signup from "./Signup";

const Home = () => {
    const [currentPage, setCurrentPage] = useState("home");

    const goToLogin = () => {
        setCurrentPage("login");
    }

    const goToSignup = () => {
        setCurrentPage("signup");
    }

    return (
        <div>
            {currentPage === "home" && (
                <div>
                    <button onClick={goToLogin}>Login</button>
                    <button onClick={goToSignup}>Signup</button>
                </div>
            )}
            {currentPage === "login" && <Login />}
            {currentPage === "signup" && <Signup />}
        </div>
    );
}

export default Home;
