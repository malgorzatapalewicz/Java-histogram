package variant1;

import java.util.Scanner;


class Histogram_test {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Set image size: n (#rows), m(#kolumns)");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Obraz obraz_1 = new Obraz(n, m);

        obraz_1.calculate_histogram();

        HelloThread[] threads = new HelloThread[94];
        for (int i = 0; i < 94; i++) {
            threads[i] = new HelloThread(obraz_1, (char) (i + 33));
            threads[i].start();
        }


        for (int i = 0; i < 94; i++) {
            try {
                threads[i].join();
                obraz_1.print_histograms();
                obraz_1.compare_histograms();
                obraz_1.display_parallel_histogram();

            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }



    }

}

