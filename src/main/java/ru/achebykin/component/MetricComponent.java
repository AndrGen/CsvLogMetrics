package ru.achebykin.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.achebykin.entity.MetricResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
@Transactional
public class MetricComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    public void addMetricResult(Map<String, String> metricMap) {
        metricMap.keySet().stream().forEach( res ->
        {
            MetricResult metricResult = new MetricResult();
            metricResult.setMetricName(res);
            metricResult.setMetricValue(metricMap.get(res));

            entityManager.persist(metricResult);

            logger.debug("metricName = " + res + "; metricValue = " + metricMap.get(res));
        });
    }
}
