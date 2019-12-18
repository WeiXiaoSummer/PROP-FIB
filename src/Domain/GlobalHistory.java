package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public class GlobalHistory {

    private ArrayList<LocalHistory> globalHistory; // represents the set of local histories


    //------------------------------------------------Constructor-----------------------------------------------------//

    public GlobalHistory() {
        globalHistory = new ArrayList<>();
    }

    //------------------------------------------------Constructor-----------------------------------------------------//


    //--------------------------------------------------Setters-------------------------------------------------------//

    public void setGlobalHistory(ArrayList<LocalHistory> globalHistory) {
        this.globalHistory = globalHistory;
    }

    //--------------------------------------------------Setters-------------------------------------------------------//


    //--------------------------------------------------Getters-------------------------------------------------------//

    public ArrayList<LocalHistory> getGlobalHistory() { return globalHistory; }

    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>(Arrays.asList("Input Path", "OutPut Path",
                "Operation", "Algorithm", "Time Used", "File Extension", "Compression Ratio"));
        return  columnNames;
    }

    //--------------------------------------------------Getters-------------------------------------------------------//


    //---------------------------------------------------Adders-------------------------------------------------------//

    public void addLocalHistory(LocalHistory lh) { globalHistory.add(lh); }

    //---------------------------------------------------Adders-------------------------------------------------------//


    //--------------------------------------------------Deleters------------------------------------------------------//

    public void clearHistory() {
        globalHistory.clear();
    }

    //--------------------------------------------------Deleters------------------------------------------------------//

}
