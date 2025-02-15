import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import Configuration from './Components/Configuration/Configuration.jsx'

import Control from './Components/ControlPanel/Control.jsx'
import AvailableTickets from './Components/TicketDisplay/AvailableTickets.jsx'
import Logs from './Components/LogDisplay/Logs.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
    <Configuration />
    <AvailableTickets></AvailableTickets>
    <Control></Control>
    <Logs></Logs>
    
  </StrictMode>,
)
