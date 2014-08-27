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

    public BaseTimer() {
        timers = new HashMap<String, Stopwatch>();
        timerUpdates = new HashMap<String, Long>();
    }

    @Override
    public void continueTimer(String timerName) {
        if (!timers.containsKey(timerName)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            timers.put(timerName, stopwatch);
        }

        Stopwatch stopwatch = timers.get(timerName);
        if (!stopwatch.isRunning()) {
            stopwatch.start();
        }
    }

    @Override
    public void stopTimer(String timerName) {
        Stopwatch stopwatch = timers.get(timerName);
        if (stopwatch.isRunning()) {
            stopwatch.stop();
        }
    }

    @Override
    public void printAll() {
        for (String timerName : this.getTimerNames()) {
            this.printTimer(timerName);
        }
    }

    @Override
    public void printTimer(String timerName) {

        long nanos = this.getNanos(timerName);
        if (nanos < 1000) {
            logger.info("{} is at: {} nanoseconds.", timerName, nanos);
        } else if (nanos < 1000 * 1000) {
            logger.info("{} is at: {} microseconds.", timerName, nanos / (1000));
        } else if (nanos < 1000 * 1000 * 1000) {
            logger.info("{} is at: {} milliseconds.", timerName, nanos / (1000 * 1000));
        } else if (nanos < 60 * 1000) {
            logger.info("{} is at: {} seconds.", timerName, (nanos / (1000 * 1000 * 1000)));
        } else if (nanos < 60 * 60 * 1000) {
            logger.info("{} is at: {} minutes.", timerName, (nanos / (1000 * 60 * 1000 * 1000)));
        } else {
            logger.info("{} is at: {} hours.", timerName, (nanos / (1000 * 60 * 60 * 1000 * 1000)));
        }
    }

    @Override
    public void updateTimer(String timerName, long nanos, TimeUnit timeUnit) {

        if (timeUnit != TimeUnit.NANOSECONDS) {
            // For now, whenever we read we expect nanos, so only allow us to write nanos.
            // TODO: Support reading in other time units so we can write any time unit.
            throw new RuntimeException("Currently only nano second updates supported for timers.");
        }

        long timerUpdate = 0L;
        if (timerUpdates.containsKey(timerName)) {
            timerUpdate = timerUpdates.get(timerName);
        }

        timerUpdates.put(timerName, timerUpdate + nanos);
    }

    private long getNanos(String timerName) {
        long nanos = 0L;
        if (timers.containsKey(timerName)) {
            Stopwatch stopwatch = timers.get(timerName);
            nanos += stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        if (timerUpdates.containsKey(timerName)) {
            nanos += timerUpdates.get(timerName);
        }

        return nanos;
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
