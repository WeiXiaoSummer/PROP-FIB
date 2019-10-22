package Domain;

import java.util.ArrayList;

public class GlobalHistory {

    private ArrayList<LocalHistory> globalHistory;

    public GlobalHistory(){
        globalHistory = new ArrayList<>();
    }

    public void addLocalHistory(LocalHistory LH) {
        globalHistory.add(LH);
    }

    public ArrayList<ArrayList<Object>> getInformation () {
        ArrayList<ArrayList<Object>> globalHistory = new ArrayList<>();
        for (LocalHistory LH : this.globalHistory) {
            globalHistory.add(LH.getInformation());
        }
        return globalHistory;
    }
}
