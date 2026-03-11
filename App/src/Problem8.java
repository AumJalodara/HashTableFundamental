import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime;
}

public class Problem8 {

    int size = 500;
    ParkingSpot[] table = new ParkingSpot[size];

    int occupied = 0;
    int totalProbes = 0;

    // Hash function
    int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    // Park vehicle
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {

            index = (index + 1) % size;
            probes++;
        }

        ParkingSpot spot = new ParkingSpot();
        spot.licensePlate = plate;
        spot.entryTime = System.currentTimeMillis();

        table[index] = spot;

        occupied++;
        totalProbes += probes;

        System.out.println("Assigned spot #" + index + " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index] != null) {

            if (table[index].licensePlate.equals(plate)) {

                long duration =
                        (System.currentTimeMillis() - table[index].entryTime) / 60000;

                double fee = duration * 0.1;

                table[index] = null;
                occupied--;

                System.out.println(
                        "Spot #" + index + " freed. Duration: "
                                + duration + " minutes. Fee: $" + fee);

                return;
            }

            index = (index + 1) % size;
        }

        System.out.println("Vehicle not found");
    }

    // Statistics
    public void getStatistics() {

        double occupancy = (occupied * 100.0) / size;
        double avgProbes = (occupied == 0) ? 0 : (double) totalProbes / occupied;

        System.out.println("Occupancy: " + occupancy + "%");
        System.out.println("Average Probes: " + avgProbes);
    }

    public static void main(String[] args) {

        Problem8 parking = new Problem8();

        parking.parkVehicle("ABC-1234");
        parking.parkVehicle("ABC-1235");
        parking.parkVehicle("XYZ-9999");

        parking.exitVehicle("ABC-1234");

        parking.getStatistics();
    }
}