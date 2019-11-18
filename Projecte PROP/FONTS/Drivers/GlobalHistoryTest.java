package Drivers;
import Domain.*;
/*import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
*/
public class GlobalHistoryTest {
    /*@Test
    public void createGlobalHistory(){
        GlobalHistory globalHistory = new GlobalHistory();
        assertNotNull("El globalHistory no s'ha creat correctament", globalHistory);
    }
    @Test
    public void addLocalHistory() {
        GlobalHistory globalHistory = new GlobalHistory();
        LocalHistory localHistory = new LocalHistory();
        globalHistory.addLocalHistory(localHistory);
        assertEquals("El localHistory no s'ha afegit al globalHistory correctament", 1, globalHistory.getGlobalHistory().size());
    }
    @Test
    public void getInformation() {
        GlobalHistory globalHistory = new GlobalHistory();
        LocalHistory localHistory = new LocalHistory("C:\\prop\\input.txt", "C:\\prop\\output.txt", ".lz78", "Compression", "LZ78" , 1.5, 0.46);
        globalHistory.addLocalHistory(localHistory);
        ArrayList<ArrayList<Object>> globalHistoryInformation1 = globalHistory.getInformation();
        ArrayList<ArrayList<Object>> globalHistoryInformation2 = new ArrayList<ArrayList<Object>>();
        globalHistoryInformation2.add(localHistory.getInformation());
        assertEquals("No obtenir correctament l'informació del globalHistory", globalHistoryInformation1, globalHistoryInformation2);
    }
    @Test
    public void getGlobalHistory() {
        GlobalHistory globalHistory = new GlobalHistory();
        ArrayList<LocalHistory> gh = globalHistory.getGlobalHistory();
        assertEquals("No obtenir el globalHistory correctament ", new GlobalHistory().getGlobalHistory(), gh);
    }
    @Test
    public void delete() {
        GlobalHistory globalHistory = new GlobalHistory();
        LocalHistory localHistory = new LocalHistory("C:\\prop\\input.txt", "C:\\prop\\output.txt", ".lz78", "Compression", "LZ78" , 1.5, 0.46);
        globalHistory.addLocalHistory(localHistory);
        globalHistory.delete(localHistory);
        assertEquals("No eliminar el localHistory del globalHistory correctament ", 0, globalHistory.getGlobalHistory().size());
    }*/
}
