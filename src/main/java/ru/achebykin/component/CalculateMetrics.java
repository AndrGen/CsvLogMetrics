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
import java.util.List;
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
            MetricValue metricValue = new MetricValue();
            SparkSession spark = SparkSession.builder()
                    .master("local[1]")
                    .appName("SparkByExamples.com")
                    .getOrCreate();
            SQLContext sqlContext = spark.sqlContext();
            //read csv with options
            sqlContext.read().option("delimiter", ",").option("header", true).csv(fileName).createOrReplaceTempView("hd");

            Row row = sqlContext.sql(
                     "select " +
                            "   (select count(*) from hd) as EventCount," +
                            "   (select count(GroupCaseId.CaseId) from (" +
                            "                                            select CaseId  " +
                            "                                            from hd " +
                            "                                            group by CaseId" +
                            "                                          ) as GroupCaseId" +
                            "   ) as ProcessCount "
            ).first();
            metricValue.setEventCount(Long.toString(row.getAs("EventCount")));
            metricValue.setProcessCount(Long.toString(row.getAs("ProcessCount")));

            saveResult(metricValue);
        });
    }

    private void saveResult(@org.jetbrains.annotations.NotNull MetricValue metricValue)
    {
            Map<String, String> metricMap = new HashMap<>();
            metricMap.put("Event Count", metricValue.getEventCount());
            metricMap.put("Process Count", metricValue.getProcessCount());
            metricMap.put("Avg Time Process", metricValue.getAvgTimeProcess());
            metricComponent.addMetricResult(metricMap);

            logger.debug("Result have been saved");
    }
}
