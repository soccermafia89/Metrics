/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**

Singleton used to provide global access to updating and reading counters, timers, and other performance stats.
In order to ensure performance is unaffected during production, the Null instance should be used.
This means all interfaces must always have return type 'void'

 @author alex
 */
public enum MetricFactory {
    INSTANCE;
    
    private static final Logger logger = LogManager.getLogger(MetricFactory.class);
    private Level level = Level.TRACE;
    private BaseTimer baseTimer = new BaseTimer();
    private BaseCounter baseCounter = new BaseCounter();
    private Stat baseStat = new BaseStat();
    private NullInstance nullInstance = new NullInstance();
    
    public Timer getTimer() {
        if(level.compareTo(logger.getLevel()) <= 0) {
            return baseTimer;
        } else {
            return nullInstance;
        }
    }
    
    public Counter getCounter() {
        if(level.compareTo(logger.getLevel()) <= 0) {
            return baseCounter;
        } else {
            return nullInstance;
        }
    }
    
    public Stat getStat() {
        if(level.compareTo(logger.getLevel()) <= 0) {
            return baseStat;
        } else {
            return nullInstance;
        }
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }
    
    public void resetAll() {
        baseTimer.clearTimers();
        baseCounter.clearCounters();
        baseStat.clearStats();
    }
    
    public void printAll() {
        baseCounter.printAll();
        baseStat.printAllStatAverages();
        baseTimer.printAll();
    }
}
