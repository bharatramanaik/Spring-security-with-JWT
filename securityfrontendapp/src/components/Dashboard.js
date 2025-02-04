import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { TokenContext } from "../App";

const Dashboard = () => {
    const {token} = useContext(TokenContext);
    const [profile, setProfile] = useState("")

    const fetchUserprofile = async (token) => {

        try {
            // console.log(token);
            
            const response = await fetch("http://localhost:8080/user/profile",{
                method: "GET",
                headers:{
                    "Authorization":`Bearer ${token} `
                }
                
            });
            console.log(response);
            
            if(response.ok){
                const data = await response.json();
                setProfile(data)
                console.log(data);
                
            }else{
                // setMessage("failed to fetch profile");
            }
        } catch (error) {
            // setMessage("error occured in fetching");
            console.log(error);
            
        }
        
    }



    useEffect(() => {
        axios.get('http://localhost:8080/user/userdetails', {withCredentials: true})
        .then(response => {
            setProfile(response.data)
            console.log(response.data);
            
        }).catch(error => {
            console.log(error);
            
        })
        fetchUserprofile(token);
    },[]);

    // const handleLogout = () => {
    //     setProfile(null)
    //     setUsername("")
    //     setPassword("")
    //     setMessage("")
    //     setJwt("")
    // }

    return (
        // <div>
        //     {profile ? (<div>
        //         <h3>User profile</h3>
        //         <p>Username: {profile.name}</p>
        //         <p>Role: {profile.email}</p>
        //         {profile.picture && <img src={profile.picture} referrerPolicy="no-referrer"></img>}
                
        //         </div>)
        //         : (<p>loading data.....</p>)}
        // </div>
        <div>
            {token ? (
                <div>
                <h3>User profile</h3>
                <p>Username: {profile.name}</p>
                <p>Role: {profile.email}</p>
                </div>
            ):(<p>loading data.....</p>)}
        </div>
    );
}

export default Dashboard;

