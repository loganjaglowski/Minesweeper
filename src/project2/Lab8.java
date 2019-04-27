package project2;

public class Lab8 {

    public static void main(String[] args) {
        MineSweeperGame game = new MineSweeperGame (5000, 1);    // board size of 100 with 1 mine
        Long t = System.currentTimeMillis();
        game.select (2,3)	;
        System.out.println ("Time:" + (System.currentTimeMillis() - t));
    }
}
