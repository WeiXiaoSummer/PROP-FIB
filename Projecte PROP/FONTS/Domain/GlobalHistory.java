package Domain;

import java.util.ArrayList;

public class GlobalHistory {

    private ArrayList<LocalHistory> globalHistory;

    public GlobalHistory() {
        globalHistory = new ArrayList<>();
    }

    public void addLocalHistory(LocalHistory LH) {
        globalHistory.add(LH);
    }

    public ArrayList<ArrayList<Object>> getInformation () {
        ArrayList<ArrayList<Object>> globalHistory = new ArrayList<>();
        for (Domain.LocalHistory LH : this.globalHistory) {
            globalHistory.add(LH.getInformation());
        }
        return globalHistory;
    }

    public ArrayList<LocalHistory> getGlobalHistory() {
        return globalHistory;
    }

    public void delete(LocalHistory localHistory) {
        globalHistory.remove(localHistory);
    }

    public void deleteAll() {
        globalHistory.clear();
    }
}
