package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public class GlobalHistory {
    /**
     * represents the set of local histories
     */
    private ArrayList<LocalHistory> globalHistory;


    //------------------------------------------------Constructor-----------------------------------------------------//

    /**
     * construct a GlobalHistory
     */
    public GlobalHistory() {
        globalHistory = new ArrayList<>();
    }

    //------------------------------------------------Constructor-----------------------------------------------------//


    //--------------------------------------------------Setters-------------------------------------------------------//

    /**
     * set attribute globalHistory of object GlobalHistory
     * @param globalHistory
     */
    public void setGlobalHistory(ArrayList<LocalHistory> globalHistory) {
        this.globalHistory = globalHistory;
    }

    //--------------------------------------------------Setters-------------------------------------------------------//


    //--------------------------------------------------Getters-------------------------------------------------------//

    /**
     * get attribute globalHistory of object GlobalHistory
     * @return attribute globalHistory of object GlobalHistory
     */
    public ArrayList<LocalHistory> getGlobalHistory() { return globalHistory; }

    /**
     * get the name of every column of table
     * @return a arraylist contain all name
     */
    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>(Arrays.asList("Input Path", "OutPut Path",
                "Operation", "Algorithm", "Time Used", "File Extension", "Compression Ratio"));
        return  columnNames;
    }

    //--------------------------------------------------Getters-------------------------------------------------------//


    //---------------------------------------------------Adders-------------------------------------------------------//

    /**
     * add a new localHistory into attribute globalHistory of object GlobalHistory
     * @param lh localHistory to be added
     */
    public void addLocalHistory(LocalHistory lh) { globalHistory.add(lh); }

    //---------------------------------------------------Adders-------------------------------------------------------//


    //--------------------------------------------------Deleters------------------------------------------------------//

    /**
     * empty attribute globalHistory of object GlobalHistory
     */
    public void clearHistory() {
        globalHistory.clear();
    }

    //--------------------------------------------------Deleters------------------------------------------------------//

}
