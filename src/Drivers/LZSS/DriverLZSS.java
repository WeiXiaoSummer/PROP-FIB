/*package Drivers.LZSS;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DriverLZSS {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(LZSSTest.class);
        int NumError = 0;
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            ++NumError;
        }
        if (result.wasSuccessful()) {
            System.out.println("Ejecución correcta. Numero de tests pasados: 4/4");
        } else {
            System.out.println("Error de ejecución. Numero de tests pasados: " + (4 - NumError) + "/4");
        }
    }
}*/
