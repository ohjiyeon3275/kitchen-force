import React from 'react';
import './App.css';
import MainLayout from "./layouts/MainLayout";
import {BrowserRouter} from "react-router-dom";


function App() {
  return (
      <div className="App">
          <BrowserRouter>
              <MainLayout title={"KITCHEN-FORCE-DEMO"}/>
          </BrowserRouter>
      </div>
  );
}

export default App;
