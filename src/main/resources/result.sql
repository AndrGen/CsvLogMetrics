DROP TABLE IF EXISTS metric_result;

CREATE TABLE metric_result (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  metric_name VARCHAR(50) NOT NULL,
  metric_value VARCHAR(10) NOT NULL
);

INSERT INTO metric_result (metric_name, metric_value) VALUES
  ('Event Count', '0'),
  ('Process Count', '0'),
  ('Avg Time Process', '0');