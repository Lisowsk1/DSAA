package dsaa.lab12;

import java.util.LinkedList;

public class Automaton implements IStringMatcher {

    @Override
    public LinkedList<Integer> validShifts(String P, String T) {
        LinkedList<Integer> shifts = new LinkedList<>();
        int m = P.length(), n = T.length();

        if (m == 0 || n < m) //no pattern or pattern bigger than text
            return shifts;

        // optimise alphabet size
        int lo = Integer.MAX_VALUE, hi = Integer.MIN_VALUE;
        for (char c : (P + T).toCharArray()) {
            lo = Math.min(lo, c);
            hi = Math.max(hi, c);
        }
        int sigma = hi - lo + 1;

        //build prefix tables
        int[] pi = computePrefix(P);
        int[] pi2 = new int[m + 1];
        pi2[0] = 0;
        for (int q = 1; q <= m; q++) {
            pi2[q] = pi[q - 1];
        }

        //build delta table
        int[][] delta = new int[m + 1][sigma];

        // state 0 from pattern[0]
        for (int a = 0; a < sigma; a++) {
            delta[0][a] = (P.charAt(0) == (char) (lo + a)) ? 1 : 0;
        }
        // states 1..m
        for (int q = 1; q <= m; q++) {
            for (int a = 0; a < sigma; a++) {
                char ch = (char) (lo + a);
                if (q < m && P.charAt(q) == ch) {
                    delta[q][a] = q + 1;
                } else {
                    delta[q][a] = delta[pi2[q]][a];
                }
            }
        }

        // 4) Run over T in O(n)
        int q = 0;
        for (int i = 0; i < n; i++) {
            int a = T.charAt(i) - lo;
            if (a < 0 || a >= sigma) {
                q = 0;
            } else {
                q = delta[q][a];
            }
            if (q == m) {
                shifts.add(i - m + 1);
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
