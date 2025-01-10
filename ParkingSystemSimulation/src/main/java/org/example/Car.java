package org.example;

class Car implements Runnable {
    private final ParkingLot parkingLot;
    private final int parkingDuration;
    private final String name;
    private final int arrivalTime;

    public Car(String name, ParkingLot parkingLot, int arrivalTime, int parkingDuration) {
        this.name = name;
        this.parkingLot = parkingLot;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
    }

    @Override
    public void run() {
        try {
            System.out.println("Car " + name + " arrived at time " + arrivalTime);
            Thread.sleep(arrivalTime * 1000L); // Wait for the arrival time delay
            parkingLot.parkCar(this); // Try to park
            Thread.sleep(parkingDuration * 1000L); // Stay parked
            parkingLot.leaveCar(this); // Leave
        } catch (InterruptedException e) {
            System.out.println("Car " + name + " was interrupted.");
        }
    }

    public String getName() {
        return name;
    }
}
