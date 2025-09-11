public class Player{
    private static Player instance;
    public int line = 0;
    public int column = 0;
    public String display = "\\o/";
    private Player(){}
    public static Player getPlayer(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }
}
