/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

/**

 @author alex
 */
public interface Counter {
    
    public void incrementCounter(String counterName);

    public void incrementCounter(String counterName, Long amount);

    public void printAll();

    public void printCount(String counterName);
    
    public void clearCounter(String counterName);

    public void clearCounters();
}
