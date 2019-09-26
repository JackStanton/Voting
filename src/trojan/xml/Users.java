package trojan.xml;

public class Users {
    private String userName, mode;
    private int level;


    public Users(int level,String userName, String mode){
        this.level = level;
        this.userName = userName;
        this.mode = mode;
    }

    public String getUserName(){
        return userName;
    }

    public String getMode(){
        return mode;
    }

    public int getLevel(){
        return level;
    }
}
