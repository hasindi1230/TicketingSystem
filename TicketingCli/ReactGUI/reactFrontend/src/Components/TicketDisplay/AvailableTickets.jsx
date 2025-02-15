import React, { useEffect , useState } from 'react'
import './AvailableTickets.css'
import axios from 'axios';

function AvailableTickets() {

  const[availableTickets , setAvailableTickets] = useState(null);

  useEffect(() =>{
    const getAvailableTickets = async() =>{

      try{
        const response = await axios.get('http://localhost:8080/restapi/tickets/availableTickets');
        setAvailableTickets(response.data);

      }catch(e) {
        console.log("Error when getting available ticket count", e)
      }

    };

    // Getting data in every 8 seconds
    const interval = setInterval(getAvailableTickets, 8000);

  }, []);


  return (
    <div className='available'>
        <h3>Current Available Tickets: {availableTickets} </h3>
        <br/>
    </div>
  )
}

export default AvailableTickets