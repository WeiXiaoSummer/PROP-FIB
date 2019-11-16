package Drivers;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DriverFitxer {


    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(FitxerTest.class);
        int NumError = 0;
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            ++NumError;
        }
        if (result.wasSuccessful()) {
            System.out.println("Ejecución correcta. Numero de tests pasados: 12/12");
        } else {
            System.out.println("Error de ejecución. Numero de tests pasados: " + (12 - NumError) + "/12");
        }
    }
}
