
/*Reads a set of data (lattice points - ordered pairs with both coordinates integer) from a file,
stores that data into a data structure, and finds a lattice point which is \most central" (technically there can be ties)
to the data set and the total distance to a lattice point which is \most central" to 
the data using both the L1 (ie Manhattan) and L2 (ie normal distance) metrics.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class MostCentralLaticePoint {
    private static Scanner scan;

    public static void main(String[] args) throws FileNotFoundException {
        // Open the file
        File input2 = new File("input2.text");

        // Create a new scanner to read the file
        scan = new Scanner(input2);
        int[] array_x = new int[100];
        int[] array_y = new int[100];
        int ele = 0;

        // Store x values into array_x and y values into array_y
        for (ele = 0; scan.hasNextInt(); ele++) {
            array_x[ele] = scan.nextInt();
            array_y[ele] = scan.nextInt();
        }

        // find max x and max y values
        int max_x = Arrays.stream(array_x).max().getAsInt();
        int max_y = Arrays.stream(array_y).max().getAsInt();

        // Base case if the file contain only (0,0) or multiple of (0,0)
        if (max_x == 0 && max_y == 0) {
            System.out.println("(" + max_x + "," + max_y + ") " + 0);
            System.out.println("(" + max_x + "," + max_y + ") " + 0);
            System.exit(0);
        }

        // ..............................L1 Process...................................
        int[] arrayX = new int[ele];
        int[] arrayY = new int[ele];
        int medianx = 0, mediany = 0, totaldist = 0;

        for (int i = 0; i < ele; i++) {
            arrayX[i] = array_x[i];
            arrayY[i] = array_y[i];
        }
        Arrays.sort(arrayX);
        Arrays.sort(arrayY);

        // if number of points is even then pick the first middle point to be the median
        if (ele % 2 == 0) {
            medianx = (arrayX[(ele / 2) - 1]);
            mediany = (arrayY[(ele / 2) - 1]);
        } else {
            medianx = (arrayX[ele / 2]);
            mediany = (arrayY[ele / 2]);
        }
        // Calculating the total distance of the median point
        for (int i = 0; i < ele; i++) {
            totaldist += (Math.abs(medianx - arrayX[i]) + Math.abs(mediany - arrayY[i]));
        }
        // Print out the central point and the minimal total distance to get to this
        // point
        System.out.println("(" + medianx + "," + mediany + ") " + totaldist);

        // End L1

        // ................................L2 Process..................................
        int[] array_x2 = new int[100];
        int[] array_y2 = new int[100];
        double smallest2 = 0.0;
        int mark2 = 0, ele2 = 0;

        // fill in all points for x-axis and y-axis
        if (max_x > 0 && max_y == 0) {
            for (int i = 0; i <= max_x; i++) {
                array_x2[i] = i;
                array_y2[i] = 0;
            }
            ele2 = max_x + 1;
        } else if (max_x == 0 && max_y > 0) {
            for (int i = 0; i <= max_y; i++) {
                array_x2[i] = 0;
                array_y2[i] = i;
            }
            ele2 = max_y + 1;
        } else if (max_y > 0 && max_x > 0 && max_y == max_x) {
            for (int i = 0; i <= max_y; i++) {
                array_y2[i] = i;
                array_x2[i] = i;
            }
            ele2 = (max_x++) * (max_y++);
        } else if (max_y > 0 && max_x > 0 && max_y > max_x) {
            for (int i = 0; i <= max_y; i++)
                array_y2[i] = i;
            for (int i = 0; i <= max_x; i++)
                array_x2[i] = i;
            for (int i = (max_x + 1); i <= max_y; i++)
                array_x2[i] = 0;
            ele2 = (max_x++) * (max_y++);
        } else if (max_x > 0 && max_y > 0 && max_x > max_y) {
            for (int i = 0; i <= max_x; i++)
                array_x2[i] = i;
            for (int i = 0; i <= max_y; i++)
                array_y2[i] = i;
            for (int i = (max_y + 1); i <= max_x; i++)
                array_y2[i] = 0;
            ele2 = (max_x++) * (max_y++);
        }

        double[] totaldist2 = new double[ele2];

        // calculate total distance for all points
        for (int i = 0; i < ele; i++) {
            for (int j = 0; j < ele2; j++) {
                totaldist2[j] += Math.sqrt(
                        Double.valueOf(Math.pow(array_x[i] - array_x2[j], 2) + Math.pow(array_y[i] - array_y2[j], 2)));
            }
        }

        // Finding the minimum total distance and mark the index at that point
        smallest2 = Arrays.stream(totaldist2).min().getAsDouble();
        mark2 = Arrays.binarySearch(totaldist2, smallest2);

        // Print out the meeting point and the minimal total distance to get to this
        // point
        System.out.println("(" + array_x2[mark2] + "," + array_y2[mark2] + ") " + smallest2);
        // End L2

        // Close the scanner object
        scan.close();
    }

}
