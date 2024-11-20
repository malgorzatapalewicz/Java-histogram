package variant1;

public class HelloThread extends Thread{
    private Obraz obraz;
    private char symbol;

    public HelloThread(Obraz obraz, char symbol){
        this.obraz = obraz;
        this.symbol = symbol;
    }

    @Override
    public void run(){
        for(int i = 0; i < obraz.get_size_n(); i++){
            for(int j = 0; j < obraz.get_size_m(); j++){
                if(obraz.get_tab()[i][j] == symbol){
                    obraz.increment_parallel_histogram((int) symbol - 33);
                }
            }
        }
    }
}
