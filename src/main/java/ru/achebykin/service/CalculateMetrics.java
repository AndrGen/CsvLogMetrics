package ru.achebykin.service;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.achebykin.component.MetricComponent;
import ru.achebykin.model.Helpdesk;
import ru.achebykin.model.MetricValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class CalculateMetrics {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MetricComponent metricComponent;

    public CalculateMetrics(MetricComponent metricComponent)
    {
        this.metricComponent = metricComponent;
        loadFile();
    }

    public void loadFile()
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
            Dataset<Row> df = sqlContext.read().option("delimiter", ";").option("header", true).csv("file.csv");
            df.createOrReplaceTempView("TAB");
            sqlContext.sql("select count(*) as cnt from TAB").show();
        });
    }

    public void saveResult(MetricValue metricValue)
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
