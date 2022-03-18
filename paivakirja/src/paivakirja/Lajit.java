/**
 * 
 */
package paivakirja;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author lassi
 * @version 14.3.2022
 *
 */
public class Lajit {
    
    private String tiedosto = "";
    private final Collection<Laji> alkiot        = new ArrayList<Laji>();
    
    /**
     * @param tunnusnro Lajin id-numero
     * @return Listan löydetyistä lajeista
     */
    public Laji anna(int tunnusnro) {
        Laji tama = new Laji();
        for (Laji elain : alkiot)
            if (elain.getID() == tunnusnro) return elain;
        return tama;
    }
    
    
    /**
     * Aliohjelma havaintojen lukemiseen tiedostosta
     * @param hakemisto Tiedoston hakemisto
     * @throws TilaException jos epäonnistuu
     */
    public void lueTiedosto(String hakemisto) throws TilaException{
        tiedosto = hakemisto + ".jar";
        throw new TilaException("Ei osata lukea tiedostoa!" + tiedosto);
    }
    
    
    
    /**
     * Aliohjelma havaintojen tallentamiseen tiedostoon
     * @throws TilaException jos epäonnistuu
     */
    public void tallennus() throws TilaException{
        throw new TilaException("Ei osata tallentaa tiedostoa!" + tiedosto);
    }
    
    
    /**
     * Lisätään haluttu laji lajeihin
     * @param lisattava laji joka lisätään
     */
    public void lisaa(Laji lisattava) {
        alkiot.add(lisattava);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lajit elaimet = new Lajit();
        Laji kauris = new Laji();
        Laji peura = new Laji();
        Laji karibu = new Laji();
        
        kauris.vastaa(0);
        peura.vastaa(1);
        karibu.vastaa(2);
        
        elaimet.lisaa(kauris);
        elaimet.lisaa(peura);
        elaimet.lisaa(karibu);
        
        
        Laji otus = elaimet.anna(00001);
        System.out.print(otus.getID() + " ");
        otus.tulosta(System.out);

    
    }

}
