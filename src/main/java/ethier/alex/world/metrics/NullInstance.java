/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**

Performance class used to immediately return calls to metrics that have been turned off.

 @author alex
 */
public class NullInstance implements Timer, Counter, Stat {
    
    public NullInstance() {
        
    }

    @Override
    public void continueTimer(String timerName) {
        // Do nothing
    }

    @Override
    public void stopTimer(String timerName) {
        // Do nothing
    }

    @Override
    public void printAll() {
        // Do nothing
    }

    @Override
    public void printTimer(String timerName) {
        // Do nothing
    }

    @Override
    public void updateTimer(String timerName, long millis, TimeUnit timeUnit) {
        // Do nothing
    }

    @Override
    public void clearTimers() {
        // Do nothing
    }
    
    @Override
    public void clearTimer(String timerName) {
        // Do nothing
    }

    @Override
    public void incrementCounter(String counterName) {
        // Do nothing
    }

    @Override
    public void incrementCounter(String counterName, Long amount) {
        // Do nothing
    }

    @Override
    public void printCount(String counterName) {
        // Do nothing
    }

    @Override
    public void clearCounter(String counterName) {
        // Do nothing
    }

    @Override
    public void clearCounters() {
        // Do nothing
    }

    @Override
    public void updateStat(String statName, BigDecimal amount) {
        // Do nothing
    }

    @Override
    public void printAllStatAverages() {
        // Do nothing
    }

    @Override
    public void printStatAverage(String statName) {
        // Do nothing
    }

    @Override
    public void clearStat(String statName) {
        // Do nothing
    }

    @Override
    public void clearStats() {
        // Do nothing
    }

    @Override
    public void updateStat(String statName, long amount) {
        // Do nothing
    }

    @Override
    public void updateStat(String statName, int amount) {
        // Do nothing
    }

    @Override
    public void updateStat(String statName, double amount) {
        // Do nothing
    }
}
