package coms309.chesssuitev1;

/**
 * Created by alecl on 12/2/2017.
 */


/**
 * Class to create pairs of client names and chat ids
 * @author alecl
 */
public class NameIDPair {
    /**
     * The client name
     */
    private String name;
    /**
     * The chat ID
     */
    private int id;

    /**
     * Constructor to create a nameidpair
     * @param name The client name
     * @param id The chat ID
     */
    public NameIDPair(String name, int id){
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the client's name
     * @return Returns name
     */
    public String getName(){
        return name;
    }

    /**
     * Get the chat id
     * @return Returns the chat id
     */
    public int getID(){
        return id;
    }
}
