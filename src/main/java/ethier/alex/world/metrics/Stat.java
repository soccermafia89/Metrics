/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import java.math.BigDecimal;

/**

 @author alex
 */
public interface Stat {
    
    public void updateStat(String statName, double amount);
    
    public void updateStat(String statName, long amount);
    
    public void updateStat(String statName, int amount);
    
    public void updateStat(String statName, BigDecimal amount);
    
    public void printAllStatAverages();
    
    public void printStatAverage(String statName);
        
    public void clearStat(String statName);
    
    public void clearStats();
}
