-- Создание таблицы subscriber
CREATE TABLE IF NOT EXISTS subscriber (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          call_type VARCHAR(2),
                                          subscriber_number VARCHAR(20),
                                          contact_number VARCHAR(20),
                                          start_time BIGINT,
                                          end_time BIGINT
);

-- Добавление тестовых данных
INSERT INTO subscriber (phone_number) VALUES
                                          ('1234567890'),
                                          ('2234567890'),
                                          ('3234567890'),
                                          ('4234567890'),
                                          ('5234567890'),
                                          ('6234567890'),
                                          ('7234567890'),
                                          ('8234567890'),
                                          ('9234567890'),
                                          ('0234567890');

CREATE TRIGGER IF NOT EXISTS AFTER_INSERT_TRANSACTION
    AFTER INSERT ON transaction
    FOR EACH ROW
CALL "nexign_project_maven.cdr_service.cdr.ManageCdrTrigger";
