import axios from "axios";
import React, { useEffect, useState } from "react";

const Dashboard = () => {
    const [user, setUser] = useState(null)
 
    useEffect(() => {
        axios.get('http://localhost:8080/user/userdetails', {withCredentials: true})
        .then(response => {
            setUser(response.data)
        }).catch(error => {
            console.log(error);
            
        })
    });

    // const handleLogout = () => {
    //     setProfile(null)
    //     setUsername("")
    //     setPassword("")
    //     setMessage("")
    //     setJwt("")
    // }

    return (
        <div>
            {user ? (<div>
                <h3>User profile</h3>
                <p>Username: {user.name}</p>
                <p>email: {user.email}</p>
                
                </div>)
                : (<p>loading data.....</p>)}
        </div>
    );
}

export default Dashboard;

