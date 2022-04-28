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
 * @author lassi
 * @version 14.3.2022
 *
 */
public class Lajit {
    
    //private String tiedosto = "";
    private static final int maxmaara = 50;
    private static Laji[] alkiot = new Laji[maxmaara];
    private static int lkm = 0;
    
    
    /**
     * @param nimi Lajin nimi
     * @return Listan löydetyistä lajeista
     */
    public Laji anna(String nimi) {
        Laji tama = new Laji(nimi);
        for (Laji elain : alkiot)
            if (elain != null && elain.getLaji().equals(nimi)) return elain;
        
        
        tama.rekisterointi();
        return tama;
    }
    
    
    /**
     * Palautetaan lajit taulukossa
     * @return Lajit-taulukko
     */
    public static Laji[] getLajit() {
        return alkiot;
    }
    
    
    /**
     * @param hakemisto Tiedoston hakemisto
     * @throws TilaException Jos lukeminen epäonnistuu
     * <pre name="test">
     * #THROWS TilaException 
     * #import java.io.File;
     *  Lajit h = new Lajit();
     *  Laji kauris = new Laji(); kauris.vastaa(1);
     *  Laji kauris2 = new Laji(); kauris2.vastaa(2);
     *  Laji kauris3 = new Laji(); kauris3.vastaa(3);
     *  String nimi = "testi";
     *  File file = new File(nimi + "/lajit.dat");
     *  file.delete();
     *  h.lueTiedosto(nimi); #THROWS TilaException
     *  h.lisaa(kauris);
     *  h.lisaa(kauris2);
     *  h.lisaa(kauris3);
     *  h.tallenna(nimi); #THROWS TilaException
     * </pre>
     */
    public void lueTiedosto(String hakemisto) throws TilaException {
        String nimi = hakemisto + "/lajit.dat";
        File file = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(file))){
            while (fi.hasNext()) {
                String n = fi.nextLine();
                if (n.equals("") || n.charAt(0) == ';') continue;
                Laji elio = new Laji();
                elio.parse(n);
                lisaa(elio);
            }
        } catch (FileNotFoundException e) {
            throw new TilaException("Tiedostoa " + nimi + " ei saada luettua");
        }
    }
    
      
    /**
     * @param hakemisto Hakemisto, johon tallennetaan tiedosto
     * @throws TilaException jos epäonnistuu
     */
    public void tallenna(String hakemisto) throws TilaException {
        
        File file = new File(hakemisto + "/lajit.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(file, false))){
            for (int i = 0; i < lkm; i++) 
                 fo.println(alkiot[i].toString());
              
        } catch (FileNotFoundException e) {
            throw new TilaException("Tiedosto " + file.getAbsolutePath() + " ei aukea");
        }
    }
    
    
    /**
     * Lisätään haluttu laji lajeihin
     * @param lisattava laji joka lisätään
     */
    public void lisaa(Laji lisattava) {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = lisattava;
        lkm++;
    }   
    
    
    /**
     * Etsitään samalla ID-numerolla oleva havainto, jos :
     * löytyy: korvataan havainto
     * ei löydy: lisätään uutena havaintona
     * @param l lisättävän havainnon viite
     * @throws TilaException jos tietorakenne on jo täysi
     */
    public void korvaaOrLisaa(Laji l) throws TilaException {
        int id = l.getID();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getID() == id) {
                alkiot[i] = l;
                //muutettu = true;
                return;
            }
        }
        lisaa(l); 
    }
    
    
    /**
     * Etsitään haluttu laji
     * @param nimi Lajin nimi
     * @return Haluttu laji
     */
    public static Laji annaLaji(String nimi) {
        Laji tama = new Laji();
        for (Laji elain : alkiot)
            if (elain != null && elain.getLaji().equals(nimi)) return elain;
        return tama;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lajit elaimet = new Lajit();
        
        try {
            elaimet.lueTiedosto("paivakirja");
        } catch (TilaException e) {
            System.err.println(e.getMessage());
        }
        
        Laji kauris = new Laji();
        Laji peura = new Laji();
        Laji karibu = new Laji();
        
        kauris.vastaa(0);
        peura.vastaa(1);
        karibu.vastaa(2);
        
        elaimet.lisaa(kauris);
        elaimet.lisaa(peura);
        elaimet.lisaa(karibu);
        
        Laji otus = new Laji();
        String nimi = otus.getLaji();
        otus.setLaji(nimi);
        otus = elaimet.anna(nimi);
        
        
        //System.out.print(otus.getID() + nimi);
        otus.tulosta(System.out);
        
        
        try {
            elaimet.tallenna("paivakirja");
        } catch (TilaException e) {
            e.printStackTrace();
        }
    
    }

}
