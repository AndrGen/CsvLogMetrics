package ru.achebykin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.achebykin.component.MetricComponent;
import ru.achebykin.model.MetricValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class CalculateMetrics {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MetricComponent metricComponent;

    public CalculateMetrics(MetricComponent metricComponent)
    {
        this.metricComponent = metricComponent;
        SaveResult(new MetricValue());
    }

    private void SaveResult(MetricValue metricValue)
    {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(() ->
        {
            Map<String, String> metricMap = new HashMap<>();
            metricMap.putIfAbsent("Event Count", metricValue.getEventCount());
            metricMap.putIfAbsent("Process Count", metricValue.getProcessCount());
            metricMap.putIfAbsent("Avg Time Process", metricValue.getAvgTimeProcess());
            metricComponent.addMetricResult(metricMap);

            logger.debug("Result have been saved");
        });
    }
}
