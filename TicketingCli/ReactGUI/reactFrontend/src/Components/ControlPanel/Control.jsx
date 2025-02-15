import React, { useState } from 'react'
import './Control.css'
import axios from 'axios'

function Control() {

  const [isPolling , setIsPolling] = useState(true);

  const stopPolling = () =>{
    setIsPolling(false);
  }

  const startThreads = async() =>{
    try{
      const response = await axios.post('http://localhost:8080/restapi/tickets/start');
      console.log(response.data);

    }catch (e) {
      console.log("Error starting the threads" , e)
    }
  }


  //stopping the system

  const stopThreads = async() =>{
    try{
      const response = await axios.post('http://localhost:8080/restapi/tickets/stop');
      console.log(response.data);

      if(response.data === 'Threads stopped susscessfully'){
        stopPolling();
      }

    }catch(e){
      console.log("Error stopping the threads" , e)
    }
  
  }


  return (
    <div className='control' >
        <button onClick={startThreads} >Start System </button> <button onClick={stopThreads} >Stop System</button>

    </div>
  )
}

export default Control