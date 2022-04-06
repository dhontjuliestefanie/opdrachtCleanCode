package org.example.Functions1;

import java.util.*;

public class SpelVoor {

    private final Speler codemaker;
    private final Speler codekraker;
    private int aantalGokken;
    private String[] teKrakenCode;
    private ArrayList<String[]> spelbord;
    private ArrayList<String[]> scorebord;

    private final int AANTALKLEURSLOTS = 4;
    private final int MAXAANTALGOKKEN = 15;

    public SpelVoor(Speler codemaker, Speler codekraker) {
        this.codemaker = codemaker;
        this.codekraker = codekraker;
        aantalGokken = 0;
        spelbord = new ArrayList<>();
        scorebord = new ArrayList<>();
    }

    public Speler getCodemaker() {
        return codemaker;
    }

    public Speler getCodekraker() {
        return codekraker;
    }

    public int getAantalKleurslots() {
        return AANTALKLEURSLOTS;
    }

    public int getAantalGokken() {
        return aantalGokken;
    }

    public String[] getTeKrakenCode() {
        return teKrakenCode;
    }

    public int getMaxAantalGokken() {
        return MAXAANTALGOKKEN;
    }

    public ArrayList<String[]> getSpelbord() {
        return spelbord;
    }

    public ArrayList<String[]> getScorebord() {
        return scorebord;
    }

    public String[] geefKleurCodeIn() {
        Scanner scanner = new Scanner(System.in);
        String stringVanKleuren = scanner.nextLine();
        String[] arrayVanKleuren = stringVanKleuren.split(" ", 4);
        return arrayVanKleuren;
    }

    public void startSpel() {
        System.out.println("Codemaker: Geef je kleurcode in");
        System.out.println("Je hebt de keuze uit: wit, rood, groen, blauw, geel, oranje, paars en roos");
        teKrakenCode = geefKleurCodeIn();
        Boolean nogGokkansenOver = true;
        Boolean codeGeraden = false;

        System.out.println("Codekraker, begin maar te gokken");
        while (nogGokkansenOver && !codeGeraden) {
            plaatsGok();
            codeGeraden = evalueerGok();
            System.out.println(Arrays.toString(scorebord.get(aantalGokken)));
            aantalGokken++;
            System.out.println("Beurt: " + aantalGokken);
            nogGokkansenOver = aantalGokken < MAXAANTALGOKKEN;
        }

        if (!nogGokkansenOver) {
            System.out.println("De code is niet geraden. " + codemaker.getNaam() + ", jij hebt gewonnen!");
        } else {
            System.out.println("De code is geraden. " + codekraker.getNaam() + ", jij hebt gewonnen!");
            System.out.println("Je had hiervoor " + aantalGokken + " beurten nodig.");
        }
    }

    private void plaatsGok() {
        String[] code = new String[4];
        String[] ingegevenCode = geefKleurCodeIn();

        if (ingegevenCode[0].equals("")) {
            ingegevenCode[0] = null;
        }
        System.arraycopy(ingegevenCode, 0, code, 0, ingegevenCode.length);
        for (int i = 0; i < code.length; i++) {
            if (code[i] == null) {
                code[i] = "blanco";
            }
        }
        spelbord.add(code);
    }

    private boolean evalueerGok() {
        String[] evaluatie = new String[AANTALKLEURSLOTS];
        int aantalWittePinnen = 0;
        int aantalRodePinnen = 0;
        String[] ingegevenCode = spelbord.get(aantalGokken);
        ArrayList<String> teKrakenCodeLst = new ArrayList<String>(Arrays.asList(teKrakenCode));
        ArrayList<String> ingegevenCodeLst = new ArrayList<String>(Arrays.asList(ingegevenCode));

        int idx = 0;
        Iterator<String> it = teKrakenCodeLst.iterator();
        while (it.hasNext() && idx < ingegevenCodeLst.size()) {
            String s = it.next();
            if (ingegevenCodeLst.get(idx).equals(s)) {
                aantalRodePinnen++;
                it.remove();
                ingegevenCodeLst.remove(idx);
            } else {
                idx++;
            }
        }

        HashSet<String> uniekeKleurenTeRadenCode = new HashSet<>(teKrakenCodeLst);

        for (String s : ingegevenCodeLst) {
            if (uniekeKleurenTeRadenCode.contains(s)) {
                aantalWittePinnen++;
            }
        }

        for (int i = 0; i < aantalRodePinnen; i++) {
            evaluatie[i] = "R";
        }
        for (int i = aantalRodePinnen; i < aantalRodePinnen + aantalWittePinnen; i++) {
            evaluatie[i] = "W";
        }
        for (int i = aantalRodePinnen + aantalWittePinnen; i < AANTALKLEURSLOTS; i++) {
            evaluatie[i] = "x";
        }
        scorebord.add(aantalGokken, evaluatie);
        return aantalRodePinnen == AANTALKLEURSLOTS;
    }
}
