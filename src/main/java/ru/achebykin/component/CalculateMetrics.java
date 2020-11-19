package ru.achebykin.component;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.achebykin.facade.ICalculateMetrics;
import ru.achebykin.model.MetricValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@Component
public class CalculateMetrics implements ICalculateMetrics {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MetricComponent metricComponent;


    public void calculate(String fileName)
    {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(() ->
        {
            SparkSession spark = SparkSession.builder()
                    .master("local[1]")
                    .appName("SparkByExamples.com")
                    .getOrCreate();
            SQLContext sqlContext = spark.sqlContext();
            //read csv with options
            Dataset<Row> df = sqlContext.read().option("delimiter", ";").option("header", true).csv(fileName);
            df.createOrReplaceTempView("TAB");
            sqlContext.sql("select count(*) as cnt from TAB").show();

            saveResult(new MetricValue("0", "0", "0.0"));
        });
    }

    private void saveResult(MetricValue metricValue)
    {
            Map<String, String> metricMap = new HashMap<>();
            metricMap.put("Event Count", metricValue.getEventCount());
            metricMap.put("Process Count", metricValue.getProcessCount());
            metricMap.put("Avg Time Process", metricValue.getAvgTimeProcess());
            metricComponent.addMetricResult(metricMap);

            logger.debug("Result have been saved");
    }
}
