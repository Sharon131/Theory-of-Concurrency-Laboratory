import java.util.*;

public class main {

    static void removeParentheses(String[] array) {
        array[0] = array[0].substring(1);
        array[array.length-1] = array[array.length-1].substring(0, array[array.length-1].length()-1);
    }

    static String getPairInString(String first, String second) {
        return "(" + first + "," + second + ")";
    }

    static Set<String> getDependenceRelations(Set<String> I, String[] A) {
        Set<String> D = new HashSet<String>();
        for (int i=0;i<A.length;i++) {
            D.add(getPairInString(A[i], A[i]));
            for (int j=i+1;j<A.length;j++) {
                String pair = getPairInString(A[i], A[j]);
                if (!I.contains(pair)) {
                    D.add(pair);
                    D.add(getPairInString(A[j], A[i]));
                }
            }
        }
        return D;
    }

    static Set<String> getTrace(Set<String> I,String w) {
        Set<String> trace = new HashSet<String>();
        trace.add(w);
        boolean addedToTrace = true;

        while (addedToTrace) {
            addedToTrace = false;
            Set<String> tmpSet = new HashSet<String>();
            for (String v : trace) {
                for (int i=0;i<v.length()-1;i++) {
                    String pair = getPairInString(String.valueOf(v.charAt(i)), String.valueOf(v.charAt(i+1)));
                    if (I.contains(pair)) {
                        String to_add = v.substring(0, i) + v.charAt(i+1) + v.charAt(i) + v.substring(i+2);
                        if (!trace.contains(to_add)) {
                            addedToTrace = true;
                            tmpSet.add(to_add);
                        }
                    }
                }
            }
            trace.addAll(tmpSet);
        }

        return trace;
    }

    static String getFoatasNormalFormFromWord(Set<String> I, String w, String[] A) {
        Map<String, Stack<String>> stacks = new HashMap<>();

        // create stacks for every letter
        for (String letter : A) {
            stacks.put(letter, new Stack<>());
        }

        // operate on those stacks
        for (int i=w.length()-1;i>=0;i--) {
            String letter = String.valueOf(w.charAt(i));

            // put letter to its column
            Stack<String> stack = stacks.get(letter);
            stack.push(letter);

            // fill other columns of dependent letters
            for (String otherLetter : A) {
                String pair = getPairInString(letter, otherLetter);
                if (!I.contains(pair) && !letter.equals(otherLetter)) {
                    Stack<String> otherStack = stacks.get(otherLetter);
                    otherStack.push("*");
                }
            }
        }

        // get down on the stacks and get normal form
        String to_return = "";
        int takenLetters = 0;
        while (takenLetters < w.length()) {
            // scan stacks
            LinkedList<String> taken = new LinkedList<>();
            to_return = to_return + "(";

            for (String letter : A) {
                Stack<String> stack = stacks.get(letter);
                String mark = stack.peek();
                if (mark.equals(letter)) {
                    to_return = to_return + mark;
                    stack.pop();
                    taken.add(mark);
                    takenLetters++;
                }
            }

            // remove star from dependent letters
            for (String letter : taken) {
                for (String otherLetter : A) {
                    String pair = getPairInString(letter, otherLetter);
                    if (!I.contains(pair) && !letter.equals(otherLetter)) {
                        Stack<String> stack = stacks.get(otherLetter);
                        if (stack.peek().equals("*")) {
                            stack.pop();
                        }
                    }
                }
            }

            to_return = to_return + ")";
        }

        return to_return;
    }

    static String getDiekertsDependenceGraph(Set<String> I, String w, String[] A) {

        Set<Integer> V = new HashSet<>();
        Set<String> E = new HashSet<>();
        String graphDotFormat = "digraph g{\n";

        // adding vertices. Each vertex is equivalent to letters from w starting from left
        for (int i=0;i<w.length();i++) {
            V.add(i);
        }

        for (int i=w.length()-1;i>=0;i--) {
            for (int j=i+1;j<w.length();j++) {
                String pair = getPairInString(String.valueOf(w.charAt(i)), String.valueOf(w.charAt(j)));
                String edge = getPairInString(String.valueOf(i+1), String.valueOf(j+1));
                if (!I.contains(pair)) {
                    // check if that edge is not transitive
                    boolean canAdd = true;
                    for (int k=i+1;k<j;k++) {
                        String pair1 = getPairInString(String.valueOf(i+1), String.valueOf(k+1));
                        String pair2 = getPairInString(String.valueOf(k+1), String.valueOf(j+1));
                        if (E.contains(pair1) && E.contains(pair2)) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd) {
                        E.add(edge);
                        graphDotFormat = graphDotFormat + "\t" + (i+1) + " -> " + (j+1) + "\n";
                    }
                }
            }
        }

        // add vertices to dot format
        for (int i=0;i<w.length();i++) {
            graphDotFormat = graphDotFormat + "\t" + (i+1) + "[label=" + String.valueOf(w.charAt(i)) + "]\n";
        }
        graphDotFormat = graphDotFormat + "}";

        return graphDotFormat;
    }

    static String getFoatasNormalFormFromGraph(String[] I, String[] graph) {

        return null;
    }

    // arguments: A, I, w
    // A-alphabet
    // I-independence relations
    // w-exemplary word
    public static void main(String[] args){

        if (args.length < 3) {
            System.out.println("Not enough arguments");
            return;
        }
        String[] A = args[0].split(",");
        removeParentheses(A);
        String[] I_array = args[1].split(";");
        removeParentheses(I_array);
        Set<String> I_set = new HashSet<String>(Arrays.asList(I_array));
        String w = args[2];

        Set<String> D = getDependenceRelations(I_set, A);
        Set<String> trace = getTrace(I_set, w);
        String normalForm1 = getFoatasNormalFormFromWord(I_set, w, A);
        String graphEdges = getDiekertsDependenceGraph(I_set, w, A);

        System.out.println(D.toString());
        System.out.println(trace.toString());
        System.out.println(normalForm1);
        System.out.println(graphEdges);
    }
}
