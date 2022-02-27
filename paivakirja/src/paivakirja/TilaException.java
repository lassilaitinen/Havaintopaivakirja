/**
 * 
 */
package paivakirja;

/**
 * @author lassi
 * @version 27.2.2022
 *
 */
public class TilaException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    /**
     * Poikkeuksen muodostaja
     * @param viesti Poikkeuksen viesti
     */
    public TilaException(String viesti) {
        super(viesti);
    }
}
