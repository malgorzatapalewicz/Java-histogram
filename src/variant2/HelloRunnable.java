package variant2;

public class HelloRunnable implements Runnable {
    private Obraz obraz;
    private int startChar;
    private int blockSize;

    public HelloRunnable(Obraz obraz, int startChar, int blockSize) {
        this.obraz = obraz;
        this.startChar = startChar;
        this.blockSize = blockSize;
    }

    @Override
    public void run(){
        int endChar = Math.min(startChar + blockSize, 127);

        for(int i = 0; i < obraz.get_size_n(); i++){
            for(int j = 0; j < obraz.get_size_m(); j++){
                for(int k = startChar; k < endChar; k++){
                    if(obraz.get_tab()[i][j] == (char) k)
                        obraz.increment_parallel_histogram(k - 33);
                }
            }
        }
        synchronized (obraz) {
            System.out.println("Thread (" + startChar + "-" + (endChar - 1) + ") completed.");
        }
    }
}
