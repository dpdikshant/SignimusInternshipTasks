package com.DFS;

import java.io.*;
import java.net.*;
import java.util.*;

public class MasterServer {
    private static final Map<String, List<String>> fileToChunksMap = new HashMap<>();
    private static final List<String> dataNodes = Arrays.asList("localhost:9001", "localhost:9002"); // Replace with actual DataNode addresses
    private static int currentDataNodeIndex = 0;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
			System.out.println("Master server started...");

			while (true) {
			    Socket clientSocket = serverSocket.accept();
			    new Thread(new ClientHandler(clientSocket)).start();
			}
		}
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                String command = (String) in.readObject();
                if (command.equals("STORE")) {
                    handleStore(in, out);
                } else if (command.equals("RETRIEVE")) {
                    handleRetrieve(in, out);
                } else if (command.equals("DELETE")) {
                    handleDelete(in, out);
                }

                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void handleStore(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
            String fileName = (String) in.readObject();
            long fileSize = (long) in.readObject();
            int numChunks = (int) Math.ceil(fileSize / 1024.0);
            List<String> chunkLocations = new ArrayList<>();

            for (int i = 0; i < numChunks; i++) {
                String selectedNode = dataNodes.get(currentDataNodeIndex);
                chunkLocations.add(selectedNode + "/" + fileName + "_chunk" + i);
                currentDataNodeIndex = (currentDataNodeIndex + 1) % dataNodes.size();
            }

            fileToChunksMap.put(fileName, chunkLocations);
            out.writeObject(chunkLocations);
        }

        private void handleRetrieve(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
            String fileName = (String) in.readObject();
            List<String> chunkLocations = fileToChunksMap.get(fileName);
            if (chunkLocations != null) {
                out.writeObject(chunkLocations);
            } else {
                out.writeObject(null);
            }
        }

        private void handleDelete(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
            String fileName = (String) in.readObject();
            if (fileToChunksMap.containsKey(fileName)) {
                fileToChunksMap.remove(fileName);
                out.writeObject("File deleted successfully.");
            } else {
                out.writeObject("File not found.");
            }
        }
    }
}