import axios from 'axios'
import React, { useEffect, useState } from 'react'
import './Logs.css'

function Logs() {

  const [logs, setLogs] = useState([]);
  const [error, setError] = useState('');
  const [isPolling , setIsPolling] = useState(true);

  //save to a server
  const saveToServer = async (logs) =>{
    try{
      const response = await axios.post('http://localhost:8080/restapi/tickets/logSave' , logs);
      console.log('logs saved to a file' , response.data);
    } catch (e){
      console.log('Error saving to a log file' , e);
    }

  }

  // Fetch logs function to be called in useEffect
  const fetchLogs = async () => {
    try {
      const response = await axios.get('http://localhost:8080/restapi/tickets/logs');
      if (Array.isArray(response.data)) {
        setLogs(response.data);
      

        response.data.forEach((logMessage) =>{
          console.log('Fetched logs: ' , logMessage);

        });

        saveToServer(response.data);


        setIsPolling(false);


      } else {
        setError(" ");
      }
    } catch (e) {
      console.log("Error fetching logs:", e);
      setError("Error fetching logs.");
    }
  };

  // Start polling for logs
  useEffect(() => {
    if(isPolling){
      fetchLogs();
      const intervalId = setInterval(() =>{
        fetchLogs();
      },5000);

      // Clean up the interval on component unmount
      return () => clearInterval(intervalId);

    }
    
    // returns only when ispolling changes
  }, [isPolling]);


  return (
    <div>
      <br /><br />
      <h3>Ticket Pool Logs</h3>
      {error && <p>{error}</p>}
      <ul>
        {logs.length > 0 ? (
          logs.map((log, index) => (
            <li key={index}>{log}</li>
          ))
        ) : (
          <p>No logs available</p>
        )}
      </ul>
    </div>
  );
}

export default Logs;