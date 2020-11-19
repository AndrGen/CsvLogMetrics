package ru.achebykin.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.achebykin.component.MetricComponent;
import ru.achebykin.facade.ICalculateMetrics;
import ru.achebykin.helper.CSVHelper;
import ru.achebykin.component.CalculateMetrics;


@RestController
@Api(value = "/api/csv")
@RequestMapping("/api/csv")
/**
 * контроллер для обработки csv файла
 */
public class CSVController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ICalculateMetrics calculateMetrics;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                logger.debug("Csv file got");

                String fileName = CSVHelper.saveCSV(file);
                calculateMetrics.calculate(fileName);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                logger.debug(message);
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                logger.error(message, e);
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }

        message = "Please upload a csv file!";
        logger.warn(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
