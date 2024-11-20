package variant1;

import  java.util.Random;


class Obraz {
    private int size_n;
    private int size_m;
    private char[][] tab;
    private char[] tab_symb;
    private int[] histogram;
    private int[] hist_parallel; //histogram obliczony w sposob rownolegly

    public char[][] get_tab() {return tab;}
    public int get_size_n() {return size_n;}
    public int get_size_m() {return size_m;}

    public Obraz(int n, int m) {
        this.size_n = n;
        this.size_m = m;
        tab = new char[n][m];
        tab_symb = new char[94]; //tablica symboli ASCII

        final Random random = new Random();

        // for general case where symbols could be not just integers
        for(int k = 0; k < 94; k++) {
            tab_symb[k] = (char)(k+33); // substitute symbols
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                tab[i][j] = tab_symb[random.nextInt(94)];  // ascii 33-127
                //tab[i][j] = (char)(random.nextInt(94)+33);  // ascii 33-127
                System.out.print(tab[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");

        this.histogram =  new int[94];
        this.hist_parallel = new int[94];
        fill_histogram_with_zeros(histogram);
        fill_histogram_with_zeros(this.hist_parallel);
    }

    public void calculate_histogram(){

        for(int i = 0; i < size_n; i++) {
            for(int j = 0; j < size_m; j++) {

                // optymalna wersja obliczania histogramu, wykorzystująca fakt, że symbole w tablicy
                // można przekształcić w indeksy tablicy histogramu
                // histogram[(int)tab[i][j]-33]++;

                // wersja bardziej ogólna dla tablicy symboli nie utożsamianych z indeksami
                // tylko dla tej wersji sensowne jest zrównoleglenie w dziedzinie zbioru znaków ASCII
                for(int k = 0; k < 94; k++) {
                    if(tab[i][j] == tab_symb[k]) histogram[k]++;
                    //if(tab[i][j] == (char)(k+33)) histogram[k]++;
                }
            }
        }

    }

// uniwersalny wzorzec dla różnych wariantów zrównoleglenia - można go modyfikować dla
// różnych wersji dekompozycji albo stosować tak jak jest zapisane poniżej zmieniając tylko
// parametry wywołania w wątkach
//
//calculate_histogram_wzorzec(start_wiersz, end_wiersz, skok_wiersz,
//                            start_kol, end_kol, skok_kol,
//                            start_znak, end_znak, skok_znak){
//
//  for(int i=start_wiersz;i<end_wiersz;i+=skok_wiersz) {
//     for(int j=start_kol;j<end_kol;j+=skok_kol) {
//        for(int k=start_znak;k<end_znak;k+=skok_znak) {
//           if(tab[i][j] == tab_symb[k]) histogram[k]++;
//


    public void print_histograms(){
        System.out.println("Symbol | Sequential | Parallel");
        for(int i = 0; i < 94; i++) {
            System.out.println(tab_symb[i] + " | " + histogram[i] + " | " + hist_parallel[i]);
            //System.out.print(tab_symb[i] + " " + histogram[i]+"\n");
            //System.out.print((char)(i+33)+" "+histogram[i]+"\n");
        }
    }

    private void fill_histogram_with_zeros(int[] histogram){
        for(int i = 0; i < 94; i++) histogram[i] = 0;
    }

    public synchronized void increment_parallel_histogram(int index){
        this.hist_parallel[index]++;
    }

    public void compare_histograms(){
        boolean isCorrect = true;
        for(int i = 0; i < 94; i++){
            if(histogram[i] != hist_parallel[i]){
                System.out.println("Mismatch at character: " + tab_symb[i]);
                isCorrect = false;
            }
        }
        display_match_message(isCorrect);
    }

    private void display_match_message(boolean isCorrect){
        if(isCorrect) System.out.println("Histograms match!");
        else System.out.println("Histograms do not match");
    }

    public void display_parallel_histogram() {
        for (int i = 0; i < 94; i++) {
            if(hist_parallel[i] > 0){
                System.out.println(tab_symb[i] + " : " + "=".repeat(hist_parallel[i]));
            }
        }
    }

}
