/**
 * 
 */
package paivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Havainnot-luokka
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Havainnot                           | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Havainto        | 
 * | - osaa lisätä ja poistaa havaintoja                |                   |
 * | - pitää yllä varsinaista rekisteriä havannoista    |                   |
 * | - lukee ja kirjoittaa havainnot tiedostoon         |                   | 
 * | - osaa myös etsiä ja lajitella havaintoja          |                   |
 * |   halutulla tavalla                                |                   | 
 * |                                                    |                   |
 * |                                                    |                   | 
 * |                                                    |                   | 
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author lassi
 * @version 27.2.2022
 *
 */
public class Havainnot {
    
    private static final int maxmaara = 50;
    private int lkm = 0;
    private boolean muutettu = false;
    
    private Havainto alkiot[] = new Havainto[maxmaara];
    
    
    
    /**
     * Oletusmuodostaja
     */
    public Havainnot() {
        // Alustettu attribuuteissa
    }

    
    /**
     * Lisää uuden havainnon
     * @param havainto Lisättävän viites
     * @example
     * <pre name="test">
     * Havainnot havainnot = new Havainnot();
     * Havainto panda = new Havainto();
     * Havainto tiikeri = new Havainto();
     * havainnot.getLkm() === 0;
     * havainnot.lisaa(panda);
     * havainnot.lisaa(tiikeri);
     * havainnot.lisaa(tiikeri);
     * havainnot.anna(0) === panda;
     * havainnot.anna(1) === tiikeri;
     * havainnot.anna(2) === tiikeri;
     * havainnot.anna(1) == panda === false;
     * havainnot.anna(1) == tiikeri === true;
     * havainnot.lisaa(panda);
     * havainnot.lisaa(tiikeri);
     * </pre>
     */
    public void lisaa(Havainto havainto){
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = havainto;
        lkm++;
    }
    
    
    /**
     * Palauttaa halutun viitteen jäseneen i
     * @param i Jäsen, jonka viite halutaan
     * @return Halutun jäsenen viite
     * @throws IndexOutOfBoundsException jos i ei käy
     */
    public Havainto anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <=i) throw new IndexOutOfBoundsException("Laiton indeksi " + i);
        return alkiot[i];
    }
    
    
    /**
     * Palauttaa halutun viitteen jäseneen i
     * @param i Jäsen, jonka viite halutaan
     * @return Halutun jäsenen viite
     * @throws IndexOutOfBoundsException jos i ei käy
     */
    public Havainto anna(String i) throws IndexOutOfBoundsException {
        for (int j = 0; j < lkm; j++) {
            if (alkiot[j].getLaji().equals(i)) return alkiot[j];
        }
        return alkiot[0];
    }
    
    
    /**
     * @param hakemisto Tiedoston hakemisto
     * @throws TilaException Jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS TilaException 
     * #import java.io.File;
     *  Havainnot h = new Havainnot();
     *  Havainto kauris = new Havainto(); kauris.vastaa();
     *  Havainto kauris2 = new Havainto(); kauris2.vastaa();
     *  Havainto kauris3 = new Havainto(); kauris3.vastaa();
     *  String nimi = "testi";
     *  File file = new File(nimi + "/havainnot.dat");
     *  file.delete();
     *  h.lueTiedosto(nimi); #THROWS TilaException
     *  h.lisaa(kauris);
     *  h.lisaa(kauris2);
     *  h.lisaa(kauris3);
     *  h.tallenna(nimi); 
     * </pre>
     */
    public void lueTiedosto(String hakemisto) throws TilaException {
        String nimi = hakemisto + "/havainnot.dat";
        File file = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(file))){
            while (fi.hasNext()) {
                String n = fi.nextLine();
                if (n.equals("") || n.charAt(0) == ';') continue;
                Havainto hav = new Havainto();
                hav.parse(n);
                lisaa(hav);
            }
        } catch (FileNotFoundException e) {
            throw new TilaException("Tiedostoa " + nimi + " ei saada luettua");
        } 
    }
    

    /**
     * Palauttaa havaintojen määrän
     * @return havaintojen määrä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Etsitään samalla ID-numerolla oleva havainto, jos :
     * löytyy: korvataan havainto
     * ei löydy: lisätään uutena havaintona
     * @param h lisättävän havainnon viite
     * @throws TilaException jos tietorakenne on jo täysi
     */
    public void korvaaOrLisaa(Havainto h) throws TilaException {
        int id = h.getID();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getID() == id) {
                alkiot[i] = h;
                muutettu = true;
                return;
            }
        }
        lisaa(h);
        
    }
    
    
    /**
     * Etsitään halutun havainnon ID
     * @param id ID, jonka avulla etsitään
     * @return löytyneen havainnon ID
     * @example
     * <pre name="test">
     * Havainnot ht = new Havainnot();
     * Havainto h = new Havainto(); h.rekisterointi(); ht.lisaa(h);
     * Havainto h2 = new Havainto(); h2.rekisterointi(); ht.lisaa(h2);
     * Havainto h3 = new Havainto(); h3.rekisterointi(); ht.lisaa(h3);
     * ht.etsiID(24) === -1;
     * ht.etsiID(111) === -1;
     * </pre>
     */
    public int etsiID(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == alkiot[i].getID()) return i;
        }
        return -1;
    }
    
    
    /**
     * Poistetaan haluttu havainto
     * @param id poistettavan havainnon ID
     * @return 1 jos poisto onnistuu, muutoin 0
     * @example
     * <pre name="test">
     * Havainnot ht = new Havainnot();
     * Havainto h = new Havainto(); h.rekisterointi(); ht.lisaa(h);
     * Havainto h2 = new Havainto(); h2.rekisterointi(); ht.lisaa(h2);
     * Havainto h3 = new Havainto(); h3.rekisterointi(); ht.lisaa(h3);
     * int id = 2;
     * ht.poista(id) === 1;
     * ht.poista(id+3) === 0;
     * ht.getLkm() === 2;
     * </pre>
     */
    public int poista(int id) {
        int k = etsiID(id);
        if (k < 0) return 0;
        lkm--;
        for (int i = k; i < lkm; i++) {
            alkiot[i] = alkiot[i+1];
        }
        alkiot[lkm] = null;
        return 1;
    }

    
    /**
     * @param hakemisto Hakemisto, johon tallennetaan tiedosto
     * @throws TilaException jos epäonnistuu
     */
    public void tallenna(String hakemisto) throws TilaException {
        if (!muutettu) return; 
        File file = new File(hakemisto + "/havainnot.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(file, false))){
            for (int i = 0; i <getLkm(); i++) {
                Havainto hav = anna(i);
                fo.println(hav.toString());
            }

        } catch (FileNotFoundException e) {
            throw new TilaException("Tiedosto " + file.getAbsolutePath() + " ei aukea");
        }
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Havainnot havainnot = new Havainnot();
        
        try {
            havainnot.lueTiedosto("paivakirja");
        } catch (TilaException e) {
           System.err.println(e.getMessage());
        }
        
        Havainto hirvi = new Havainto();
        Havainto kauris = new Havainto();
        hirvi.rekisterointi();
        kauris.rekisterointi();
        hirvi.vastaa();
        kauris.vastaa();
        
        havainnot.lisaa(hirvi);
        havainnot.lisaa(kauris);


        
        try {
            havainnot.tallenna("paivakirja");
        } catch (TilaException e) {
            System.err.println(e.getMessage());
        }
        
    
    }




}
