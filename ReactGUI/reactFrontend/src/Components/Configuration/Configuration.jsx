import React, { useState } from 'react'
import './Configuration.css'
import axios from 'axios';


function Configuration() {

    //to get the current ticket configuration
    const[maximumCapacity , setMaximumCapacity] = useState('');
    const[totalTickets , setTotalTickets] = useState('');
    const[releaseRate , setReleaseRate] = useState('');
    const[retrievalRate , setRetrievalRate] = useState('');


    //to display the current configuration after user input data
    const[currentConfig , setCurrentConfig] = useState('');

    const[error, setError] = useState('');

    //submit new configuration
    const setNewConfiguration = async () =>{
        const newConfiguration = {
            maxTicketCapacity : parseInt(maximumCapacity , 10),
            totalTickets : parseInt(totalTickets , 10),
            ticketReleaseRate : parseInt(releaseRate , 10),
            customerRetrievalRate : parseInt(retrievalRate , 10)
        };

        try{
            const response = await axios.post('http://localhost:8080/restapi/tickets/newConfig' , newConfiguration);
            console.log("Full Response: " , response);
                    
            if (response.data === 'Configuration set'){
                console.log('New Configuration set');
            }else{
                console.log('Error setting up new configuration' , response.data);
            }
            
        }catch (e) {
            console.log("Error setting up new configuration" , e)
        }

    };

    // function to validate the input values
    const validatingInput = (value) =>{
        return Number.isInteger(Number(value)) && Number(value)>0;
    };

    const validatingConfiguration = async () =>{
        if(
            !validatingInput(maximumCapacity) ||
            !validatingInput(totalTickets) ||
            !validatingInput(releaseRate) ||
            !validatingInput(retrievalRate)
        ){
            const errorMessage = "****All input values must be positive*****";
            setError(errorMessage);
            console.log(errorMessage);
            return;
        }

    }



  return (
    <div className="configuration">
        <h2>Set A New Configuration</h2>
        {error && <p> {error} </p> }

        <p>Enter the Maximum Ticket Capacity: </p>
        <input type="number" placeholder='0' id='maximumCapacity' onChange={(e) => setMaximumCapacity(e.target.value)}/>
        <p>Enter the Total Number of Tickets: </p>
        <input type="number" placeholder='Cannot exceed maximum capacity' id='totalTickets' onChange={(e) => setTotalTickets(e.target.value)} />
        <p>Enter the Ticket Release Rate: </p>
        <input type="number" placeholder='0' id='releaseRate' onChange={(e) => setReleaseRate(e.target.value)} />
        <p>Enter the Customer Retrieval Rate: </p>
        <input type="number" placeholder='0' id='retrievalRate' onChange={(e) => setRetrievalRate(e.target.value)} />
        <br /><br />

        <button onClick={ async ()=> {
            await validatingConfiguration();
            if (!error){
                await setNewConfiguration();
            }
        }}>Set Configuration</button>

        <br /><br /><br /><hr />
        

        <h2>Current Configuration </h2>
        <p>Maximum Ticket Capacity: {maximumCapacity} </p>
        <p>Total Number of Tickets:{totalTickets} </p>
        <p>Ticket Release Rate:{releaseRate} </p>
        <p>Customer Retrieval Rate: {retrievalRate} </p>
        <br />

    </div>
    )
  
}

export default Configuration