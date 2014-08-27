/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**

 @author alex
 */
public class BaseCounter implements Counter {

    private static final Logger logger = LogManager.getLogger(MetricFactory.class);
    private Map<String, Long> counters;

    public BaseCounter() {
        counters = new HashMap<String, Long>();
    }

    @Override
    public void incrementCounter(String counterName) {
        this.incrementCounter(counterName, 1L);
    }

    @Override
    public void incrementCounter(String counterName, Long amount) {
        Long count;
        if (!counters.containsKey(counterName)) {
            count = 0L;
        } else {
            count = counters.get(counterName);
        }

        count += amount;
        counters.put(counterName, count);
    }

    @Override
    public void printAll() {
        for (String timerName : this.getCounterNames()) {
            this.printCount(timerName);
        }
    }

    @Override
    public void printCount(String counterName) {
        
        if(counters.containsKey(counterName)) {
            logger.info("{} is at: {}", counterName, counters.get(counterName));
        } else {
            logger.info("{} is at: {}", counterName, 0);
        }
    }

    private Set<String> getCounterNames() {
        Set<String> timerNames = new HashSet<String>();
        timerNames.addAll(counters.keySet());
        return timerNames;
    }
    
    @Override
    public void clearCounter(String counterName) {
        counters.remove(counterName);
    }

    @Override
    public void clearCounters() {
        counters.clear();
    }
}
