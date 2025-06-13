package dsaa.lab12;

import java.util.LinkedList;

public class KMP implements IStringMatcher {

    @Override
    public LinkedList<Integer> validShifts(String P, String T) {
        LinkedList<Integer> shifts = new LinkedList<>();
        int m = P.length(), n = T.length();
        if (m == 0 || n < m) return shifts;

        int[] pi = computePrefix(P);
        int q = 0;
        for (int i = 0; i < n; i++) {
            while (q > 0 && P.charAt(q) != T.charAt(i)) {
                q = pi[q - 1];
            }
            if (P.charAt(q) == T.charAt(i)) {
                q++;
            }
            if (q == m) {
                shifts.add(i - m + 1);
                q = pi[q - 1];
            }
        }
        return shifts;
    }


    private int[] computePrefix(String P) {
        int m = P.length();
        int[] pi = new int[m];
        pi[0] = 0;
        int k = 0;
        for (int i = 1; i < m; i++) {
            while (k > 0 && P.charAt(k) != P.charAt(i)) {
                k = pi[k - 1];
            }
            if (P.charAt(k) == P.charAt(i)) {
                k++;
            }
            pi[i] = k;
        }
        return pi;
    }
}
