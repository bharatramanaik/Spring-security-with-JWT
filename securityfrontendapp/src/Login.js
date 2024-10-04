import React, {useState} from "react";

const Login = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [message, setMessage] = useState("")
    const [jwt, setJwt] = useState("")
    const [profile, setProfile] = useState(null)

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8080/api/signin",{
                method: "POST",
                headers:{
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({username, password})
            });

            if(response.ok){
                const data = await response.json();
                console.log(data);
                
                setJwt(data.jwtToken);
                setMessage("login successfull");
                const token = data.jwtToken
                fetchUserprofile(token);
            }else{
                setMessage("login fail");
            }
        } catch (error) {
            setMessage("error occured");
            console.log(error);
            
        }
        
    }

    const fetchUserprofile = async (token) => {

        try {
            console.log(token);
            // e.preventDefault();
            const response = await fetch("http://localhost:8080/user/profile",{
                method: "GET",
                headers:{
                    "Authorization":`Bearer ${token}`
                }
                
            });
            // console.log(response);
            
            if(response.ok){
                const data = await response.json();
                setProfile(data)
            }else{
                setMessage("failed to fetch profile");
            }
        } catch (error) {
            setMessage("error occured in fetching");
            console.log(error);
            
        }
        
    }


    return (
        <div>
            <h2>Login form</h2>
            {!profile ? (
            <form onSubmit={handleLogin}>
                <div>
                    <label>Username: </label>
                    <input type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password: </label>
                    <input type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            ) : (
                <div>
                    <h3>User profile</h3>
                    <p>Username: {profile.username}</p>
                    <p>Role: {profile.roles.join(", ")}</p>
                    <p>Message: {profile.message}</p>
                </div>
            )}
        </div>
            // {message && <p>{message}</p>}
            // {jwt && <p>{jwt}</p>}
    );
}

export default Login;