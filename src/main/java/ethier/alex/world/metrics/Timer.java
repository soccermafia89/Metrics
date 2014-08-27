/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import java.util.concurrent.TimeUnit;

/**

 @author alex
 */
public interface Timer {
        
    public void continueTimer(String timerName);
    
    public void stopTimer(String timerName);
    
    public void printAll();
    
    public void printTimer(String timerName);
    
    public void updateTimer(String timerName, long millis, TimeUnit timeUnit);
    
    public void clearTimer(String timerName);
        
    public void clearTimers();
}
