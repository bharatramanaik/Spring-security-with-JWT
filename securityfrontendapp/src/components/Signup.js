import React, {useState} from "react";
import Login from "./Login";

const Signup = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [role, setRole] = useState("")
    const [message, setMessage] = useState("")

    const handleSignup = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8080/api/signup",{
                method: "POST",
                headers:{
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({username, password, role})
            });

            if (response.ok) {

                const data = await response.json();
                console.log(data);
                setMessage("User created")
            }else{
                setMessage("User already exists");
            }
        } catch (error) {
            console.log("error in signup",error);
        }
    }

    return (
        <div>
            {message ? (<Login/>):
                (<form onSubmit={handleSignup}>
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
                    <div>
                        <label>Role: </label>
                        <input type="text"
                        value={role}
                        onChange={(e) => setRole(e.target.value)}
                        />
                    </div>
                    <button type="submit">Signup</button>
                </form>)
            }
            
             {message && <p>{message}</p>}
            
        </div>
    );
}

export default Signup;