package com.DFS;

import java.io.*;
import java.net.*;

public class DataNode {
    private static final int PORT = 9001;  // Define the port number for the DataNode server

    public static void main(String[] args) {
        System.out.println("DataNode server started on port: " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client: " + clientSocket.getInetAddress());
                new DataNodeHandler(clientSocket).start(); // Start a new thread for each client connection
            }
        } catch (IOException e) {
            System.err.println("Error starting DataNode server: " + e.getMessage());
        }
    }
}

// A separate handler class to manage each client connection in a thread
class DataNodeHandler extends Thread {
    private Socket clientSocket;

    public DataNodeHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String command = (String) in.readObject();

            switch (command) {
                case "STORE_CHUNK":
                    handleStoreChunk(in, out);
                    break;
                case "RETRIEVE_CHUNK":
                    handleRetrieveChunk(in, out);
                    break;
                case "DELETE_CHUNK":
                    handleDeleteChunk(in, out);
                    break;
                default:
                    out.writeObject("ERROR: Unknown command");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void handleStoreChunk(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String chunkName = (String) in.readObject();
        byte[] chunkData = (byte[]) in.readObject();

        try (FileOutputStream fos = new FileOutputStream(chunkName)) {
            fos.write(chunkData);
            out.writeObject("Chunk stored successfully: " + chunkName);
        } catch (IOException e) {
            out.writeObject("ERROR: Failed to store chunk: " + e.getMessage());
        }
    }

    private void handleRetrieveChunk(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String chunkName = (String) in.readObject();
        File chunkFile = new File(chunkName);

        if (chunkFile.exists()) {
            byte[] chunkData = new byte[(int) chunkFile.length()];
            try (FileInputStream fis = new FileInputStream(chunkFile)) {
                fis.read(chunkData);
                out.writeObject(chunkData);
            }
        } else {
            out.writeObject("ERROR: Chunk not found: " + chunkName);
        }
    }

    private void handleDeleteChunk(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String chunkName = (String) in.readObject();
        File chunkFile = new File(chunkName);

        if (chunkFile.delete()) {
            out.writeObject("Chunk deleted successfully: " + chunkName);
        } else {
            out.writeObject("ERROR: Failed to delete chunk: " + chunkName);
        }
    }
}