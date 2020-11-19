package ru.achebykin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.achebykin.component.MetricComponent;
import ru.achebykin.controller.CSVController;
import ru.achebykin.entity.MetricResult;
import ru.achebykin.facade.ICalculateMetrics;

import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvLogMetricsApplicationTests {
    private String url;
	private String fileName;

	@BeforeEach
	public void runBeforeAllTestMethods() throws FileNotFoundException {
		url  = "http://localhost:8080/api/csv/upload";
		fileName = "src/test/java/resources/Helpdesk.csv";
	}

	@Autowired
	private CSVController controller;

	@Autowired
	ICalculateMetrics calculateMetrics;

	@Autowired
	MetricComponent metricComponent;

	@Test
	@DisplayName("load controller")
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	@DisplayName("calculate metrics from helpdesk.csv")
	public void calculateMetrics() throws InterruptedException {
		calculateMetrics.calculate(fileName);

		Thread.sleep(40 * 1000);

		List<MetricResult> resultList = metricComponent.getAllMetricResult();

		assertAll("MetricsIsNotEmpty",
				() -> assertFalse(resultList.isEmpty()),
				() -> assertTrue(resultList.size() == 3),
				() -> assertEquals("21348", resultList.get(0).getMetricValue(), "Event count"));
	}

	@Test
	@DisplayName("file not found")
	public void wrongFileName() throws InterruptedException{
		calculateMetrics.calculate("");

		Thread.sleep(40 * 1000);
		List<MetricResult> resultList = metricComponent.getAllMetricResult();

		assertTrue(resultList.isEmpty());
	}

}
