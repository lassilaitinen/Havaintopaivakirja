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
     * @throws TilaException Virheilmoitus, jos tietorakenne täysi
     * @example
     * <pre name="test">
     * #THROWS TilaException 
     * Havainnot havainnot = new Havainnot();
     * Havainto panda = new Havainto();
     * Havainto tiikeri = new Havainto();
     * havainnot.getLkm() === 0;
     * havainnot.lisaa(panda); jasenet.getLkm() === 1;
     * havainnot.lisaa(tiikeri); jasenet.getLkm() === 2;
     * havainnot.lisaa(tiikeri); jasenet.getLkm() === 3;
     * havainnot.anna(0) === panda;
     * havainnot.anna(1) === tiikeri;
     * havainnot.anna(2) === tiikeri;
     * havainnot.anna(1) == panda === false;
     * havainnot.anna(1) == tiikeri === true;
     * havainnot.anna(3) === panda; #THROWS IndexOutOfBoundsException 
     * havainnot.lisaa(panda); jasenet.getLkm() === 4;
     * havainnot.lisaa(tiikeri); jasenet.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Havainto havainto) throws TilaException{
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
     *  File file = new File(tiedNimi + "/havainnot.dat");
     *  file.delete();
     *  h.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  h.lisaa(kauris);
     *  h.lisaa(kauris2);
     *  h.lisaa(kauris3);
     *  h.tallenna(nimi);
     *  h = new Havainnot();
     *  h.lueTiedostosta(nimi);
     *  h.lisaa(kauris);
     *  h.tallenna(nimi);
     *  file.delete() === true;
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
     * @param hakemisto Hakemisto, johon tallennetaan tiedosto
     * @throws TilaException jos epäonnistuu
     */
    public void tallenna(String hakemisto) throws TilaException {
        //if (!muutettu) return; //TODO: ei tarvitse tallentaa jos ei ole muutettu
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
        
        try {
            havainnot.lisaa(hirvi);
            havainnot.lisaa(kauris);
        } catch (TilaException viesti) {
            System.out.println(viesti.getMessage());
        }

        
        try {
            havainnot.tallenna("paivakirja");
        } catch (TilaException e) {
            System.err.println(e.getMessage());
        }
        
    
    }

}
