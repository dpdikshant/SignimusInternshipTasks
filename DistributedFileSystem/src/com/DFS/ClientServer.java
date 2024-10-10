package com.DFS;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientServer {
    public static void main(String[] args) {
        String operation = "DELETE"; // Change to "STORE", "RETRIEVE", or "DELETE" to perform different operations
        String fileName = "hello.txt"; // File to be stored, retrieved, or deleted
        List<String> chunkLocations = Arrays.asList("localhost:9001/hello_chunk0", "localhost:9001/hello_chunk1");

        switch (operation.toUpperCase()) {
            case "STORE":
                storeFile(fileName, chunkLocations);
                break;
            case "RETRIEVE":
                retrieveFile(fileName, chunkLocations);
                break;
            case "DELETE":
                deleteFile(chunkLocations);
                break;
            default:
                System.out.println("Invalid operation specified. Please choose STORE, RETRIEVE, or DELETE.");
        }
    }

    private static void storeFile(String fileName, List<String> chunkLocations) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int chunkIndex = 0;

            for (String chunkLocation : chunkLocations) {
                String[] parts = chunkLocation.split("/");
                String[] hostPortParts = parts[0].split(":");

                String nodeHost = hostPortParts[0];
                int nodePort = Integer.parseInt(hostPortParts[1]);
                String chunkName = parts[1];

                try (Socket nodeSocket = new Socket(nodeHost, nodePort);
                     ObjectOutputStream nodeOut = new ObjectOutputStream(nodeSocket.getOutputStream());
                     ObjectInputStream nodeIn = new ObjectInputStream(nodeSocket.getInputStream())) {

                    nodeOut.writeObject("STORE_CHUNK");
                    nodeOut.writeObject(chunkName);

                    int bytesRead = fis.read(buffer);
                    nodeOut.writeObject(Arrays.copyOf(buffer, bytesRead));

                    String response = (String) nodeIn.readObject();
                    System.out.println("Response from DataNode: " + response);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error while communicating with DataNode: " + e.getMessage());
                }

                chunkIndex++;
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }
    }

    private static void retrieveFile(String fileName, List<String> chunkLocations) {
        try (FileOutputStream fos = new FileOutputStream("retrieved_" + fileName)) {
            for (String chunkLocation : chunkLocations) {
                String[] parts = chunkLocation.split("/");
                String[] hostPortParts = parts[0].split(":");

                String nodeHost = hostPortParts[0];
                int nodePort = Integer.parseInt(hostPortParts[1]);
                String chunkName = parts[1];

                try (Socket nodeSocket = new Socket(nodeHost, nodePort);
                     ObjectOutputStream nodeOut = new ObjectOutputStream(nodeSocket.getOutputStream());
                     ObjectInputStream nodeIn = new ObjectInputStream(nodeSocket.getInputStream())) {

                    nodeOut.writeObject("RETRIEVE_CHUNK");
                    nodeOut.writeObject(chunkName);

                    Object response = nodeIn.readObject();

                    if (response instanceof byte[]) {
                        fos.write((byte[]) response);
                        System.out.println("Chunk " + chunkName + " retrieved successfully.");
                    } else {
                        System.err.println("Error retrieving chunk " + chunkName + ": " + response);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error while communicating with DataNode: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error while saving retrieved file: " + e.getMessage());
        }
    }

    private static void deleteFile(List<String> chunkLocations) {
        for (String chunkLocation : chunkLocations) {
            String[] parts = chunkLocation.split("/");
            String[] hostPortParts = parts[0].split(":");

            String nodeHost = hostPortParts[0];
            int nodePort = Integer.parseInt(hostPortParts[1]);
            String chunkName = parts[1];

            try (Socket nodeSocket = new Socket(nodeHost, nodePort);
                 ObjectOutputStream nodeOut = new ObjectOutputStream(nodeSocket.getOutputStream());
                 ObjectInputStream nodeIn = new ObjectInputStream(nodeSocket.getInputStream())) {

                nodeOut.writeObject("DELETE_CHUNK");
                nodeOut.writeObject(chunkName);

                String response = (String) nodeIn.readObject();
                System.out.println("Response from DataNode: " + response);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error while communicating with DataNode: " + e.getMessage());
            }
        }
    }
}