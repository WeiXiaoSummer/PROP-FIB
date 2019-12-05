package Domain;

import java.util.ArrayList;

public class GlobalHistory {

    private ArrayList<LocalHistory> globalHistory;

    public GlobalHistory() {
        globalHistory = new ArrayList<>();
    }

    public void addLocalHistory(LocalHistory lh) {
        globalHistory.add(lh);
    }

    public void clearHistory(LocalHistory lh) {
        globalHistory.clear();
    }

    public void setGlobalHistory(ArrayList<LocalHistory> globalHistory) {
        this.globalHistory = globalHistory;
    }

    public ArrayList<ArrayList<Object>> getInformation() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        for (int i = 0; i < globalHistory.size(); ++i) {
            information.add(globalHistory.get(i).getInformation());
        }
        return information;
    }
}
