package org.example.Names;

import java.util.Arrays;

public class JaarcijfersVoor {

    private int[][] jaarcijfers;
    private int jaartal;

    public JaarcijfersVoor(int jaartal) {
        this.jaartal = jaartal;
        jaarcijfers = new int[12][];
        int[] dagenPerMaand = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isSchrikkel(jaartal)) {
            dagenPerMaand[1] = 29;
        }

        for (int i = 0; i < jaarcijfers.length; i++) {
            jaarcijfers[i] = new int[dagenPerMaand[i]];
        }

        for (int i = 0; i < jaarcijfers.length; i++) {
            for (int j = 0; j < jaarcijfers[i].length; j++) {
                jaarcijfers[i][j] = 20;
            }
        }
    }

    private boolean isSchrikkel(int jaartal) {
        boolean schrikkel;
        if (jaartal % 4 == 0 && jaartal % 100 != 0) {
            schrikkel = true;
        } else if (jaartal % 400 == 0) {
            schrikkel = true;
        } else {
            schrikkel = false;
        }
        return schrikkel;
    }

    public String toString() {
        String afdruk = "Jaarcijfers " + jaartal + '\n';
        for (int i = 0; i < jaarcijfers.length; i++) {
            afdruk = afdruk + Arrays.toString(jaarcijfers[i]) + '\n';
        }
        return afdruk;
    }

    private boolean validDayMonth(int dag, int maand) {
        boolean valid;
        int i = maand - 1;
        int j = dag - 1;
        if (j < jaarcijfers[i].length && i < jaarcijfers.length) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    public void verhoogWaardeDagMaand(int dag, int maand, int waarde) {
        int i = maand - 1;
        int j = dag - 1;
        if (validDayMonth(dag, maand)) {
            jaarcijfers[i][j] = jaarcijfers[i][j] + waarde;

        } else {
            System.out.println("Geef een geldige dag en maand in");
        }
    }

    public int getWaardeDagMaand(int dag, int maand) {
        int i = maand - 1;
        int j = dag - 1;
        if (!validDayMonth(dag, maand)) {
            return -1;
        } else {
            return jaarcijfers[i][j];
        }
    }

    public int jaarcijferMaand(int maand) {
        int i = maand - 1;
        int som = 0;
        if (i < jaarcijfers.length) {
            for (int j = 0; j < jaarcijfers[i].length; j++) {
                som = som + jaarcijfers[i][j];
            }
        } else {
            som = -1;
        }
        return som;
    }

    public int jaarcijfer() {
        int som = 0;
        for (int i = 0; i < jaarcijfers.length; i++) {
            for (int j = 0; j < jaarcijfers[i].length; j++) {
                som = som + jaarcijfers[i][j];
            }
        }
        return som;
    }

    public double gemiddeldeMaand(int maand) {
        int i = maand - 1;
        return (double) jaarcijferMaand(maand) / (double) jaarcijfers[i].length;
    }

    public double gemiddeldeJaar() {
        int aantalDagenJaar;
        if (isSchrikkel(jaartal)) {
            aantalDagenJaar = 366;
        } else {
            aantalDagenJaar = 365;
        }
        return (double) jaarcijfer() / (double) aantalDagenJaar;
    }
}
