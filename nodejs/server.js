const http = require('http');

const hostname = 'localhost';
const port = 3001;


const server = http.createServer((req,res) => {
    // console.log(req);
    res.statusCode = 200
    res.setHeader('Content-Type', 'text/plain')
    res.end('Hello World\n')
})

server.listen(port, hostname, () =>{
    console.log("samyak your server is booming on http://localhost:3001");
})


/*
    This code first includes the Node.js http module.
    Node.js has a fantastic standard library, including first-class support for networking.
    The createServer() method of http creates a new HTTP server and returns it.
    The server is set to listen on the specified port and host name. 
    When the server is ready, the callback function is called, in this case informing us that the server is running.
    Whenever a new request is received, the request event is called, providing two objects: 
    a request (an http.IncomingMessage object) and a response (an http.ServerResponse object).
    Those 2 objects are essential to handle the HTTP call.
    The first provides the request details. In this simple example, this is not used, but you could access the request headers and request data.
    The second is used to return data to the caller.  
*/