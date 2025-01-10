package org.example;

import java.util.List;

class Gate extends Thread {
    private final ParkingLot parkingLot;
    private final List<Car> cars;

    public Gate(String name, ParkingLot parkingLot, List<Car> cars) {
        super(name);
        this.parkingLot = parkingLot;
        this.cars = cars;
    }

    @Override
    public void run() {
        for (Car car : cars) {
            try {
                Thread carThread = new Thread(car);
                carThread.start();
                carThread.join(); // Wait for the car to park and leave
                Thread.sleep(1000); // Delay between cars entering this gate
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted.");
            }
        }
    }
}
