DROP TABLE IF EXISTS records;

CREATE TABLE records (
    id INT NOT NULL,
    type VARCHAR(2) NOT NULL,
    company VARCHAR(30) NOT NULL,
    content VARCHAR(100) NOT NULL,
    processed CHAR(1) DEFAULT 'N' NOT NULL
);

