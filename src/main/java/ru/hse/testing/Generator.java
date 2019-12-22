package ru.hse.testing;

import java.io.*;
import java.util.*;

public class Generator {
    public static void main(String... argc) throws FileNotFoundException {
        List<Character[]> pp = new ArrayList<>();
        List<Boolean> isValid = new ArrayList<>();

        for (char a = 'A'; a <= 'E'; a++) {
            for (char b = 'F'; b <= 'H'; b++) {
                for (char c = 'K'; c <= 'M'; c++) {
                    for (char d = 'N'; d <= 'O'; d++) {
                        pp.add(new Character[]{a, b, c, d});
                        isValid.add(true);
                    }
                }
            }
        }

        for (int i = 0; i < pp.size(); i++) {
            boolean[][] found = new boolean[4][4];

            for (int j = 0; j < pp.size(); j++) {
                if (i == j || !isValid.get(j)) {
                    continue;
                }

                for (int a = 0; a < 4; a++) {
                    for (int b = a + 1; b < 4; b++) {
                        if (pp.get(i)[a] == pp.get(j)[a] && pp.get(i)[b] == pp.get(j)[b]) {
                            found[a][b] = true;
                        }
                    }
                }
            }

            boolean cool = false;
            for (int a = 0; a < 4; a++) {
                for (int b = a + 1; b < 4; b++) {
                    if (!found[a][b]) {
                        cool = true;
                    }
                }
            }

            if (!cool) {
                isValid.set(i,false);
            }
        }

        for (int i = 0; i < pp.size(); i++) {
            if (isValid.get(i)) {
                for (int a = 0; a < 4; a++) {
                    System.out.print((pp.get(i)[a]));
                    if (a + 1 < 4) {
                        System.out.print("\t");
                    } else {
                        System.out.println("\n");
                    }
                }
            }

        }
    }
}
