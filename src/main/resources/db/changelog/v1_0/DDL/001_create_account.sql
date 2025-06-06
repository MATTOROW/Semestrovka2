
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL
);
CREATE TABLE account_info (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
  description TEXT,
  image_url VARCHAR(255),
  CONSTRAINT fk_account_info_account FOREIGN KEY (account_id)
      REFERENCES account(id)
      ON UPDATE CASCADE
      ON DELETE CASCADE
);
