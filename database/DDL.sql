DROP TABLE IF EXISTS extension;

CREATE TABLE extension(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    extension_name VARCHAR(255) NOT NULL,
    default_check TINYINT(1) NOT NULL,
    active_check TINYINT(1) NOT NULL
);
