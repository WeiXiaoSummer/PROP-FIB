package Drivers;
import Data.DataCtrl;
import Domain.Fitxer;
import Domain.LZ78;
/*import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
*/
public class LZ78Test {
   /* private LZ78 lz78 = new LZ78(0,0,0,0,0,0,0);

    @Test
    public void testCompression() {
        String content = DataCtrl.getInstance().getInputFile("C:\\Users\\sheng\\Desktop\\er\\Proba.txt");
        Fitxer fitxer = new Fitxer(null,null,content);
        int midaComprimida = lz78.comprimir(fitxer).getFileContent().length();
        int midaOriginal = content.length();
        assertTrue("L'algorisme LZ78 no ha comprimit correctament", midaComprimida < midaOriginal);
    }

    @Test
    public void testDescompression() {
        String content = DataCtrl.getInstance().getInputFile("C:\\Users\\sheng\\Desktop\\er\\Proba.txt");
        Fitxer fitxer = new Fitxer(null,null,content);
        String contentComprimit = lz78.comprimir(fitxer).getFileContent();
        Fitxer fitxer1 = new Fitxer(null, null, contentComprimit);
        String contentDescomprimit = lz78.descomprimir(fitxer1).getFileContent();
        assertEquals(content, contentDescomprimit);
    }*/
}
