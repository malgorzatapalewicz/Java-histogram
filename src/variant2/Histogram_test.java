package variant2;

import java.util.Scanner;


class Histogram_test {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Set image size: n (#rows), m(#kolumns)");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Obraz obraz_1 = new Obraz(n, m);


        System.out.println("Set number of threads");
        int num_threads = scanner.nextInt();


        int block_size = (int) Math.ceil((double) 94 / num_threads);
        int start_char = 33;
        Thread[] threads = new Thread[num_threads];

        for(int i = 0; i < num_threads; i++){
            final int current_start_char = start_char;
            threads[i] = new Thread(new HelloRunnable(obraz_1, start_char, block_size));
            threads[i].start();
            start_char += block_size;
        }

        try {
            for (int i = 0; i < num_threads; i++) {
                threads[i].join();
            }

            obraz_1.calculate_sequential_histogram();
            //obraz_1.print_histograms();
            obraz_1.compare_histograms();
            obraz_1.display_parallel_histogram();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

