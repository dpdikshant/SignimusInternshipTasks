package com.algo;

import java.util.*;


class Graph {
    private final Map<Integer, List<Edge>> adjacencyList = new HashMap<>();

   
    public void addEdge(int source, int destination, int weight) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.putIfAbsent(destination, new ArrayList<>());
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // For undirected graph
    }

    
    public Map<Integer, Integer> dijkstra(int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        for (int node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        priorityQueue.add(new Edge(start, 0));

        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            int currentNode = current.destination;

            
            if (adjacencyList.containsKey(currentNode)) {
                for (Edge edge : adjacencyList.get(currentNode)) {
                    int newDist = distances.get(currentNode) + edge.weight;
                    if (newDist < distances.get(edge.destination)) {
                        distances.put(edge.destination, newDist);
                        priorityQueue.add(new Edge(edge.destination, newDist));
                    }
                }
            }
        }
        return distances;
    }

    static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();

        System.out.println("Enter the number of edges:");
        int edgeCount = scanner.nextInt();

        for (int i = 0; i < edgeCount; i++) {
            System.out.println("Enter edge (source destination weight):");
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(source, destination, weight);
        }

        System.out.println("Enter the starting node for Dijkstra's algorithm:");
        int startNode = scanner.nextInt();
        
        
        if (graph.adjacencyList.containsKey(startNode)) {
            Map<Integer, Integer> shortestPaths = graph.dijkstra(startNode);
            System.out.println("Shortest paths from node " + startNode + ":");
            for (Map.Entry<Integer, Integer> entry : shortestPaths.entrySet()) {
                System.out.println("To node " + entry.getKey() + " : " + entry.getValue());
            }
        } else {
            System.out.println("The starting node " + startNode + " does not exist in the graph.");
        }

        scanner.close();
    }
}
