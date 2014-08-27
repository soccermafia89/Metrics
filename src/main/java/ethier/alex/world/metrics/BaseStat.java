/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ethier.alex.world.metrics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**

 @author alex
 */
public class BaseStat implements Stat {

    private static final Logger logger = LogManager.getLogger(MetricFactory.class);
    private Map<String, List<BigDecimal>> stats;

    public BaseStat() {
        stats = new HashMap<String, List<BigDecimal>>();
    }
    
    @Override
    public void updateStat(String statName, double amount) {
        this.updateStat(statName, new BigDecimal(amount));
    }
    
    @Override
    public void updateStat(String statName, long amount) {
        this.updateStat(statName, new BigDecimal(amount));
    }

    @Override
    public void updateStat(String statName, int amount) {
        this.updateStat(statName, new BigDecimal(amount));
    }

    @Override
    public void updateStat(String statName, BigDecimal amount) {

        List<BigDecimal> statList;
        if (stats.containsKey(statName)) {
            statList = stats.get(statName);
        } else {
            statList = new ArrayList<BigDecimal>();
        }

        statList.add(amount);
        stats.put(statName, statList);
    }

    @Override
    public void printAllStatAverages() {
        for (String statName : this.getStatNames()) {
            this.printStatAverage(statName);
        }
    }

    @Override
    public void printStatAverage(String statName) {
        if (stats.containsKey(statName)) {
            BigDecimal total = new BigDecimal(0L);
            List<BigDecimal> statList = stats.get(statName);
            int size = statList.size();

            for (int i = 0; i < size; i++) {
                BigDecimal stat = statList.get(i);
                total.add(stat);
            }

            BigDecimal avg = total.divide(new BigDecimal(size), 5, RoundingMode.UP);
            logger.info("{} has average value: {}", statName, avg.doubleValue());

        } else {
            logger.info("{} has average value: {}", statName, 0);
        }
    }

    public Set<String> getStatNames() {
        Set<String> statNames = new HashSet<String>();
        statNames.addAll(stats.keySet());
        return statNames;
    }

    @Override
    public void clearStat(String statName) {
        stats.remove(statName);
    }

    @Override
    public void clearStats() {
        stats.clear();
    }
}
