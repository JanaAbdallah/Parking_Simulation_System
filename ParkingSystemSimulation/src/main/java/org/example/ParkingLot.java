package org.example;

import java.util.concurrent.Semaphore;

class ParkingLot {
    private final Semaphore spots;
    private final int totalSpots;
    private int occupiedSpots = 0;
    private int totalCarsServed = 0;

    public ParkingLot(int totalSpots) {
        this.totalSpots = totalSpots;
        this.spots = new Semaphore(totalSpots, true); // Fair semaphore
    }

    public void parkCar(Car car) throws InterruptedException {
        spots.acquire(); // Semaphore ensures thread-safe access
        synchronized (this) {
            occupiedSpots++;
            totalCarsServed++;
            System.out.println("Car " + car.getName() + " parked. (Parking Status: " + occupiedSpots + " spots occupied)");
        }
    }

    public void leaveCar(Car car) {
        spots.release();
        synchronized (this) {
            occupiedSpots--;
            System.out.println("Car " + car.getName() + " left. (Parking Status: " + occupiedSpots + " spots occupied)");
        }
    }

    public void printSummary() {
        System.out.println("Total Cars Served: " + totalCarsServed);
        System.out.println("Current Cars in Parking: " + occupiedSpots);
    }
}
