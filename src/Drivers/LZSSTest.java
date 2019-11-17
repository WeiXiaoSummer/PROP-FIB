package Drivers;

import Domain.*;
import Data.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;

public class LZSSTest {

    private LZSS lzss = new LZSS(0, 0, 0, 0, 0, 0, 0);

    @Test
    public void ComprimeixPle(){
        String Path = new File("").getAbsolutePath() + "\\Prueba.txt";
        String text = DataCtrl.getInstance().getInputFile(Path);
        Fitxer fitxer = new Fitxer("", "", text);
        int midaNormal = text.length();
        Fitxer fitxerComp = lzss.comprimir(fitxer);
        int midaComprimida = fitxerComp.getFileContent().length();
        assertTrue("L'algorisme LZSS no ha comprimit correctament", (midaNormal > midaComprimida));
    }

    @Test
    public void DescomprimeixPle(){
        String Path = new File("").getAbsolutePath() + "\\Prueba.txt";
        String text = DataCtrl.getInstance().getInputFile(Path);
        Fitxer fitxer = new Fitxer("", "", text);
        int midaNormal = text.length();
        Fitxer fitxerComp = lzss.comprimir(fitxer);
        Fitxer fitxerDescomp = lzss.descomprimir(fitxerComp);
        int midaDescomprimida = fitxerDescomp.getFileContent().length();
        assertTrue("L'algorisme LZSS no ha descomprimit correctament", (midaNormal == midaDescomprimida));
        String textDescomp = fitxerDescomp.getFileContent();
        assertFalse("L'algorisme LZSS no ha descomprimit correctament", (text == textDescomp));
    }

    @Test
    public void ComprimeixBuit(){
        String text = "";
        Fitxer fitxer = new Fitxer("", "", text);
        int midaNormal = text.length();
        Fitxer fitxerComp = lzss.comprimir(fitxer);
        int midaComprimida = fitxerComp.getFileContent().length();
        assertTrue("L'algorisme LZSS no ha comprimit correctament", (midaNormal >= midaComprimida));
    }

    @Test
    public void DescomprimeixBuit(){
        String text = "";
        Fitxer fitxer = new Fitxer("", "", text);
        int midaNormal = text.length();
        Fitxer fitxerComp = lzss.comprimir(fitxer);
        Fitxer fitxerDescomp = lzss.descomprimir(fitxerComp);
        int midaDescomprimida = fitxerDescomp.getFileContent().length();
        assertTrue("L'algorisme LZSS no ha descomprimit correctament", (midaNormal == midaDescomprimida));
    }

}