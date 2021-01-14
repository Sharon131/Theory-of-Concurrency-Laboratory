import java.util.Arrays;
import java.util.StringTokenizer;

public class main {

    static void removeParenthess(String[] array) {
        ;
    }

    // arguments: A, I, w
    // A-alphabet
    // I-independence relations
    // w-example word
    public static void main(String[] args){

        if (args.length < 3) {
            System.out.println("Not enough arguments");
            return;
        }
        String[] A = args[0].split(","); //.split(",")
        A[0] = A[0].substring(1);
        A[A.length-1] = A[A.length-1].substring(0, A[A.length-1].length()-1);
        String[] I_string = args[1].split(";");
        String w = args[2];

        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(I_string));
        System.out.println(w);
    }
}
