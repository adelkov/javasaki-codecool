CREATE TABLE IF NOT EXISTS users
(
  id            SERIAL      NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  name          VARCHAR(30) NOT NULL,
  email         VARCHAR(30),
  hash_password VARCHAR(56) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS users_user_id_uindex
  ON users (id);

CREATE UNIQUE INDEX IF NOT EXISTS users_hash_password_uindex
  ON users (hash_password);

CREATE TABLE IF NOT EXISTS addresses
(
  id       SERIAL      NOT NULL
    CONSTRAINT user_id
    REFERENCES users,
  user_id  INTEGER     NOT NULL,
  city     VARCHAR(14) NOT NULL,
  zip_code INTEGER     NOT NULL,
  address  VARCHAR(60) NOT NULL,
  country  VARCHAR(20)
);

CREATE UNIQUE INDEX IF NOT EXISTS addresses_id_uindex
  ON addresses (id);

CREATE TABLE IF NOT EXISTS orders
(
  id                 SERIAL  NOT NULL
    CONSTRAINT billing_address_id
    REFERENCES addresses (id)
    CONSTRAINT shipping_address_id
    REFERENCES addresses (id)
    CONSTRAINT user_id
    REFERENCES users,
  user_id            INTEGER NOT NULL,
  shipping_adress_id INTEGER NOT NULL,
  billing_address_id INTEGER NOT NULL,
  status             VARCHAR(10)
);

CREATE UNIQUE INDEX IF NOT EXISTS orders_id_uindex
  ON orders (id);

CREATE TABLE IF NOT EXISTS suppliers
(
  id          SERIAL      NOT NULL
    CONSTRAINT suppliers_pkey
    PRIMARY KEY,
  name        VARCHAR(20) NOT NULL,
  description VARCHAR(200)
);

CREATE UNIQUE INDEX IF NOT EXISTS suppliers_id_uindex
  ON suppliers (id);

CREATE TABLE IF NOT EXISTS product_categories
(
  id          SERIAL      NOT NULL
    CONSTRAINT product_categories_pkey
    PRIMARY KEY,
  name        VARCHAR(12) NOT NULL,
  department  VARCHAR(12) NOT NULL,
  description VARCHAR(200)
);

CREATE UNIQUE INDEX IF NOT EXISTS product_categories_id_uindex
  ON product_categories (id);

CREATE TABLE IF NOT EXISTS products
(
  id                  SERIAL           NOT NULL
    CONSTRAINT products_pkey
    PRIMARY KEY
    CONSTRAINT product_category_id
    REFERENCES product_categories
    CONSTRAINT supplier_id
    REFERENCES suppliers,
  name                VARCHAR(12)      NOT NULL,
  default_price       DOUBLE PRECISION NOT NULL,
  default_currency    VARCHAR(3),
  product_category_id INTEGER,
  supplier_id         INTEGER          NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS products_id_uindex
  ON products (id);

CREATE TABLE IF NOT EXISTS cart_items
(
  product_id INTEGER NOT NULL,
  order_id   INTEGER NOT NULL,
  quantity   INTEGER,
  CONSTRAINT id
  PRIMARY KEY (order_id, product_id)
);


