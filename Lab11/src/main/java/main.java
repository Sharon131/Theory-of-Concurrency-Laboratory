import java.util.StringTokenizer;

public class main {

    // arguments: A, I, w
    // A-alphabet
    // I-independence relations
    // w-example word
    public static void main(String[] args){

        if (args.length < 3) {
            System.out.println("Not enough arguments");
            return;
        }
        String[] A_string = args[0].split(","); //.split(",")
        String I_string = args[1];
        String w = args[2];

        System.out.println(A_string + I_string + w);
    }
}
