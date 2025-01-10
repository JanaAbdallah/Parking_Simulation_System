package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialize the parking lot with available spots (example size: 5)
        ParkingLot parkingLot = new ParkingLot(5);

        // Prepare a map to store cars associated with each gate
        Map<String, List<Car>> gateCarsMap = new HashMap<>();
        gateCarsMap.put("Gate 1", new ArrayList<>());
        gateCarsMap.put("Gate 2", new ArrayList<>());
        gateCarsMap.put("Gate 3", new ArrayList<>());

        // Read and parse car data from file
        try (BufferedReader reader = new BufferedReader(new FileReader("cars.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseCarData(line, gateCarsMap, parkingLot);
            }
        }

        // Create and start threads for each gate
        List<Gate> gates = new ArrayList<>();
        for (Map.Entry<String, List<Car>> entry : gateCarsMap.entrySet()) {
            Gate gate = new Gate(entry.getKey(), parkingLot, entry.getValue());
            gates.add(gate);
            gate.start();
        }

        // Wait for all gates to complete
        for (Gate gate : gates) {
            try {
                gate.join();
            } catch (InterruptedException e) {
                System.out.println(gate.getName() + " was interrupted.");
            }
        }

        // Final summary after all cars have been processed
        parkingLot.printSummary();
    }

    private static void parseCarData(String line, Map<String, List<Car>> gateCarsMap, ParkingLot parkingLot) {
        line = line.trim();
        if (!line.isEmpty()) {
            String[] parts = line.split(",\\s*");

            String gateName = parts[0].trim();
            String carName = parts[1].split("\\s+")[1].trim();
            int arrivalTime = Integer.parseInt(parts[2].split("\\s+")[1].trim());
            int parkingDuration = Integer.parseInt(parts[3].split("\\s+")[1].trim());

            Car car = new Car(carName, parkingLot, arrivalTime, parkingDuration);
            gateCarsMap.getOrDefault(gateName, new ArrayList<>()).add(car);
        }
    }
}
