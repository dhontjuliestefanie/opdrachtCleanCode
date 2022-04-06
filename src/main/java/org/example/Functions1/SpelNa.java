package org.example.Functions1;

import java.util.*;

public class SpelNa {

    private final Speler codemaker;
    private final Speler codekraker;
    private int aantalGokken;
    private String[] teKrakenCode;
    private ArrayList<String[]> spelbord;
    private ArrayList<String[]> scorebord;

    private final int AANTALKLEURSLOTS = 4;
    private final int MAXAANTALGOKKEN = 15;

    public SpelNa(Speler codemaker, Speler codekraker) {
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
        geefTeKrakenCodeIn();
        startMetGokken();
        spelVoorbij();
    }

    private void geefTeKrakenCodeIn() {
        System.out.println("Codemaker: Geef je kleurcode in");
        System.out.println("Je hebt de keuze uit: wit, rood, groen, blauw, geel, oranje, paars en roos");
        teKrakenCode = geefKleurCodeIn();
    }

    private void startMetGokken() {
        System.out.println("Codekraker, begin maar te gokken");

        boolean nogGokkansenOver = true;
        boolean codeGeraden = false;

        while (nogGokkansenOver && !codeGeraden) {
            plaatsGok();
            codeGeraden = evalueerGok();
            printBeurtVoorbij();
            aantalGokken++;
            nogGokkansenOver = aantalGokken < MAXAANTALGOKKEN;
        }
    }

    private void printBeurtVoorbij() {
        System.out.println(Arrays.toString(scorebord.get(aantalGokken)));
        System.out.println("Beurt: " + aantalGokken);
    }

    private void spelVoorbij() {
        if (aantalGokken == MAXAANTALGOKKEN) {
            System.out.println("De code is niet geraden. " + codemaker.getNaam() + ", jij hebt gewonnen!");
        } else {
            System.out.println("De code is geraden. " + codekraker.getNaam() + ", jij hebt gewonnen!");
            System.out.println("Je had hiervoor " + aantalGokken + " beurten nodig.");
        }
    }

    private void plaatsGok() {
        String[] codeVoorOpSpelbord = new String[4];
        String[] ingegevenCode = geefKleurCodeIn();
        boolean volledigeGokIngegeven = ingegevenCode.length == AANTALKLEURSLOTS;

        if (volledigeGokIngegeven) {
            codeVoorOpSpelbord = ingegevenCode;
        } else {
            codeVoorOpSpelbord = vulBlancoAanInCode(ingegevenCode);
        }
        spelbord.add(codeVoorOpSpelbord);
    }

    private String[] vulBlancoAanInCode(String[] ingegevenCode) {
        String[] codeVoorOpSpelbord = new String[4];
        if (ingegevenCode[0].equals("")) {
            codeVoorOpSpelbord = geefBlancoGokIn();
        } else {
            for (int i = 0; i < ingegevenCode.length; i++) {
                codeVoorOpSpelbord[i] = ingegevenCode[i];
            }
            for (int i = 0; i < codeVoorOpSpelbord.length; i++) {
                if (codeVoorOpSpelbord[i] == null) {
                    codeVoorOpSpelbord[i] = "blanco";
                }
            }
        }
        return codeVoorOpSpelbord;
    }

    private String[] geefBlancoGokIn() {
        return new String[]{"blanco", "blanco", "blanco", "blanco"};
    }

    private boolean evalueerGok() {
        int aantalRodePinnen = getAantalRodePinnen();

        String[] ingegevenCode = spelbord.get(aantalGokken);
        ArrayList<String> teKrakenCodeLst = new ArrayList<String>(Arrays.asList(teKrakenCode));
        ArrayList<String> ingegevenCodeLst = new ArrayList<String>(Arrays.asList(ingegevenCode));

        haalPinnenOpJuistePlaatsEruit(teKrakenCodeLst, ingegevenCodeLst);

        int aantalWittePinnen = getAantalWittePinnen(teKrakenCodeLst, ingegevenCodeLst);

        plaatsEvaluatieOpScorebord(aantalRodePinnen, aantalWittePinnen);

        return aantalRodePinnen == AANTALKLEURSLOTS;
    }

    private void haalPinnenOpJuistePlaatsEruit(ArrayList<String> teKrakenCodeLst, ArrayList<String> ingegevenCodeLst) {
        int idx = 0;
        Iterator<String> it = teKrakenCodeLst.iterator();
        while (it.hasNext() && idx < ingegevenCodeLst.size()) {
            String s = it.next();
            if (ingegevenCodeLst.get(idx).equals(s)) {
                it.remove();
                ingegevenCodeLst.remove(idx);
            } else {
                idx++;
            }
        }
    }

    private int getAantalRodePinnen() {
        int aantalRodePinnen = 0;
        String[] ingegevenCode = spelbord.get(aantalGokken);
        for (int i = 0; i < AANTALKLEURSLOTS; i++) {
            if (ingegevenCode[i].equals(teKrakenCode[i])) {
                aantalRodePinnen++;
            }
        }
        return aantalRodePinnen;
    }

    private int getAantalWittePinnen(ArrayList<String> teKrakenCodeLst, ArrayList<String> ingegevenCodeLst) {
        int aantalWittePinnen = 0;
        HashSet<String> uniekeKleurenTeRadenCode = new HashSet<>(teKrakenCodeLst);

        for (String s : ingegevenCodeLst) {
            if (uniekeKleurenTeRadenCode.contains(s)) {
                aantalWittePinnen++;
            }
        }
        return aantalWittePinnen;
    }

    private void plaatsEvaluatieOpScorebord(int aantalRodePinnen, int aantalWittePinnen) {
        String[] evaluatie = new String[AANTALKLEURSLOTS];

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
    }
}