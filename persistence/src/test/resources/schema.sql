DROP TABLE IF EXISTS gift_certificates;

CREATE TABLE gift_certificates (
                                  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  description VARCHAR(1000) NOT NULL,
                                  price DECIMAL(10,2) NOT NULL,
                                  duration INT NOT NULL,
                                  create_date DATETIME NOT NULL,
                                  last_update_date DATETIME NOT NULL

);
