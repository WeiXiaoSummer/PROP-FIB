package Domain;

import java.util.Scanner;

public class text {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        System.out.print("path please:\n");
        DomainCtrl.getInstance().compressFileTo(str,str,"LZ78");
    }
}
