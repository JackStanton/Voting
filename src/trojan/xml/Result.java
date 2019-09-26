package trojan.xml;

public class Result {

    private int level;
    private String username, mode;
    private boolean choose;

    public Result(int level, String username, boolean choose,String mode){
        this.choose = choose;
        this.level = level;
        this.username = username;
        this.mode = mode;
    }

    public int getLevel(){
        return level;
    }

    public String getUsername(){
        return username;
    }

    public boolean getChoose(){
        return choose;
    }

    public String getMode(){
        return mode;
    }
}
