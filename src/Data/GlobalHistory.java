package Domain;

import java.util.ArrayList;

public class GlobalHistory {

    private ArrayList<LocalHistory> globalHistory;

    public GlobalHistory() {
        globalHistory = new ArrayList<>();
    }

    public void addLocalHistory(Domain.LocalHistory LH) {
        globalHistory.add(LH);
    }

    public ArrayList<ArrayList<Object>> getInformation () {
        ArrayList<ArrayList<Object>> globalHistory = new ArrayList<>();
        for (Domain.LocalHistory LH : this.globalHistory) {
            globalHistory.add(LH.getInformation());
        }
        return globalHistory;
    }

    public ArrayList<Domain.LocalHistory> getGlobalHistory() {
        return globalHistory;
    }

    public void delete(Domain.LocalHistory localHistory) {
        globalHistory.remove(localHistory);
    }

    public void deleteAll() {
        globalHistory.clear();
    }
}
