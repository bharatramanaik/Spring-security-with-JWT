import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Dashboard from './components/Dashboard';
import { Children, createContext, useState } from 'react';


export const TokenContext = createContext();

const TokenContextProvider = ({ children }) => {
  const [token, setToken] = useState(null);

  return(
    <TokenContext.Provider value={{token, setToken}}>{children}</TokenContext.Provider>
  );
}

function App() {
  return (
    <TokenContextProvider>
      <Router>
        <Routes>
          <Route path='/' element={<Home/>}/>
          <Route path='/dashboard' element={<Dashboard/>}/>
        </Routes>
      </Router>
    </TokenContextProvider>
  );
}

export default App;
