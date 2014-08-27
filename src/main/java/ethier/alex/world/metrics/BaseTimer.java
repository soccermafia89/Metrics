/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import com.google.common.base.Stopwatch;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**

 @author alex
 */
public class BaseTimer implements Timer {
    
    private static final Logger logger = LogManager.getLogger(MetricFactory.class);
    private Map<String, Stopwatch> timers;
    private Map<String, Long> timerUpdates; // Allow updates of timers.
    
    //TODO: Re-implement stopwatch using system nano time.
    // Add times to multimap, calculate sum on read.
    // Consider a map implementation that is a fast write, read once implementation.
    //   i.e. writes in data, but on a read, it will convert everything to a cached hashmap, then output values.
    //   further writes will put it back into a 'dirty' state.
    
    public BaseTimer() {
        timers = new HashMap<String, Stopwatch>();
        timerUpdates = new HashMap<String, Long>();
    }

    @Override
    public void continueTimer(String timerName) {
        if(!timers.containsKey(timerName)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            timers.put(timerName, stopwatch);
        }
        
        Stopwatch stopwatch = timers.get(timerName);
        if(!stopwatch.isRunning()) {
            stopwatch.start();
        }
    }
    
    @Override
    public void stopTimer(String timerName) {
        Stopwatch stopwatch = timers.get(timerName);
        if(stopwatch.isRunning()) {
            stopwatch.stop();
        }
    }
    
    @Override
    public void printAll() {
        for(String timerName : this.getTimerNames()) {
            this.printTimer(timerName);
        }
    }
    
    @Override
    public void printTimer(String timerName) {
           
        long millis = this.getMillis(timerName);
        if(millis < 1000) {
            logger.info("{} is at: {} milliseconds.", timerName, millis);
        } else if(millis < 60*1000) {
            logger.info("{} is at: {} seconds.", timerName, (millis / 1000));
        } else if(millis < 60*60*1000) {
            logger.info("{} is at: {} minutes.", timerName, (millis / (1000*60)));
        } else {
            logger.info("{} is at: {} hours.", timerName, (millis / (1000*60*60)));
        }
    }
    
    @Override
    public void updateTimer(String timerName, long millis, TimeUnit timeUnit) {
        
        if(timeUnit != TimeUnit.MILLISECONDS) {
            // For now, whenever we read we except millis, so only allow us to write millis.
            // TODO: Support reading in other time units so we can write any time unit.
            throw new RuntimeException("updateTimer for non millisecond units not supported at this time.");
        }
        
        long timerUpdate = 0L;
        if(timerUpdates.containsKey(timerName)) {
            timerUpdate = timerUpdates.get(timerName);
        }
        
        timerUpdates.put(timerName, timerUpdate + millis);
    }
    
    private long getMillis(String timerName) {
        long millis = 0L;
        if(timers.containsKey(timerName)) {
            Stopwatch stopwatch = timers.get(timerName);
            millis += stopwatch.elapsed(TimeUnit.MILLISECONDS);
        }
        
        if(timerUpdates.containsKey(timerName)) {
            millis += timerUpdates.get(timerName);
        }
        
        return millis;
    }
    
    private Set<String> getTimerNames() {
        Set<String> timerNames = new HashSet<String>();
        timerNames.addAll(timers.keySet());
        timerNames.addAll(timerUpdates.keySet());
        return timerNames;
    }
    
    @Override
    public void clearTimers() {
        timers.clear();
        timerUpdates.clear();
    }

    @Override
    public void clearTimer(String timerName) {
        timers.remove(timerName);
        timerUpdates.remove(timerName);
    }
}
