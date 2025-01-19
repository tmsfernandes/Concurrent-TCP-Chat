# Concurrent TCP Chat

A simple multi-client chat application built using Java. This project demonstrates a concurrent TCP server and client setup, enabling multiple clients to connect and communicate with each other in real-time.

## Features

- **Multi-client Support:** The server supports multiple client connections concurrently.
- **Broadcast Messaging:** Messages from any client are broadcast to all connected clients.
- **Concurrency:** Utilizes threading for managing simultaneous client connections.

## Technologies Used

- **Programming Language:** Java
- **Build Tool:** Maven

## How It Works

### Server
The server listens for incoming client connections and manages active clients. When a client sends a message, the server broadcasts it to all connected clients.

### Client
The client connects to the server, allowing users to send and receive messages. Each client can set a username upon connection.



