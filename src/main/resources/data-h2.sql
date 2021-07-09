CREATE SCHEMA testdb AUTHORIZATION sa;

-- Insert user
INSERT INTO users (name, password) VALUES ('operador1@mercadolivre.com', '123456');
INSERT INTO users (name, password) VALUES ('operador2@mercadolivre.com', '123456');
INSERT INTO users (name, password) VALUES ('vendedor1@gmail.com', '123456');
INSERT INTO users (name, password) VALUES ('vendedor2@gmail.com', '123456');
INSERT INTO users (name, password) VALUES ('comprador1@gmail.com', '123456');
INSERT INTO users (name, password) VALUES ('comprador2@gmail.com', '123456');

-- Insert operators
INSERT INTO operator (users_user_id) VALUES (1);
INSERT INTO operator (users_user_id) VALUES (2);

-- Insert sellers
INSERT INTO seller (users_user_id) VALUES (3);
INSERT INTO seller (users_user_id) VALUES (4);

-- Insert buyers
INSERT INTO buyer (users_user_id) VALUES (5);
INSERT INTO buyer (users_user_id) VALUES (6);

-- Insert Warehouses
INSERT INTO warehouse (accept_fresh, address, warehouse_name) VALUES (1, 'Avenida Doutor Antonio Joao Abdalla, 3333', 'CAJAMAR01');
INSERT INTO warehouse (accept_fresh, address, warehouse_name) VALUES (1, 'Avenida Doutor Antonio Joao Abdalla, 3333', 'CAJAMAR02');

-- Insert Warehouse Operators
INSERT INTO warehouse_operator (operator_id, warehouse_id) VALUES (1, 1);
INSERT INTO warehouse_operator (operator_id, warehouse_id) VALUES (1, 2);

-- Insert product categories
INSERT INTO product_category (category_name) VALUES ('FS');
INSERT INTO product_category (category_name) VALUES ('RF');
INSERT INTO product_category (category_name) VALUES ('FF');

-- Insert Warehouse Sections
INSERT INTO warehouse_section (capacity, current_availability, section_name, temperature, warehouse_id, category_id) VALUES (100000, 100000, 'CAJAMAR01FRESH', 10.0, 1, 1);
INSERT INTO warehouse_section (capacity, current_availability, section_name, temperature, warehouse_id, category_id) VALUES (80000, 80000, 'CAJAMAR01CHILL', 2.0, 1, 2);
INSERT INTO warehouse_section (capacity, current_availability, section_name, temperature, warehouse_id, category_id) VALUES (50000, 50000, 'CAJAMAR01FROZEN', -1.0, 1, 3);

INSERT INTO warehouse_section (capacity, current_availability, section_name, temperature, warehouse_id, category_id) VALUES (100000, 100000, 'CAJAMAR02FRESH', 10.0, 2, 1);
INSERT INTO warehouse_section (capacity, current_availability, section_name, temperature, warehouse_id, category_id) VALUES (80000, 80000, 'CAJAMAR02CHILL', 2.0, 2, 2);
INSERT INTO warehouse_section (capacity, current_availability, section_name, temperature, warehouse_id, category_id) VALUES (50000, 50000, 'CAJAMAR02FROZEN', -1.0, 2, 3);

-- Insert products seller 1
INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Uma unidade de tomate Caqui',
        13.0,
        8.0,
        'Tomate Caqui unidade',
        2.39,
        1,
        1);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Um cacho com 6 bananas nanicas',
        13.0,
        8.0,
        'Banana nanica 6/un',
        3.10,
        1,
        1);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Um maço de alface crespa higienizado',
        3.0,
        0.0,
        'Alface Crespa Bem Querer 140g',
        3.98,
        2,
        1);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Uma peça de queijo minas frescal sem drenagem',
        7.0,
        1.0,
        'Queijo minas frescal 500g',
        18.9,
        2,
        1);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Torta congelada com recheio de grango com Catupiry(original) cremoso',
        4.0,
        -3.0,
        'Torta de frango Catupiry congelada 700g',
        22.40,
        3,
        1);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Pacote com 6 unidades de pão de alho congelados',
        4.0,
        -3.0,
        'Pão de alho tradicional 300g',
        8.99,
        3,
        1);


-- Insert products seller 2
INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Uma unidade de cenoura tipo Extra',
        15.0,
        8.0,
        'Cenoura Extra unidade 150g',
        0.75,
        1,
        2);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Pacote com 500g de abobrinha brasileira',
        13.0,
        7.0,
        'Abobrinha Brasileira 500g',
        2.99,
        1,
        2);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Pacote com 160g de folhas de rúcula higienizadas',
        4.0,
        0.0,
        'Rúcula Hidropônica 160g',
        4.00,
        2,
        2);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Uma peça de queijo parmesão de primeira qualidade',
        8.0,
        0.0,
        'Queijo parmesão faixa azul 200g',
        22.9,
        2,
        2);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Açaí Frooty tradicional',
        0.0,
        -3.0,
        'Açaí Frooty 1.5L',
        24.99,
        3,
        2);

INSERT INTO product (product_description,
                     maximum_temperature,
                     minimum_temperature,
                     product_name,
                     price,
                     category_id,
                     seller_id)
VALUES ('Bandeja com 1kg de filé de peito de frango',
        -1.0,
        -6.0,
        'Peito de frango 1kg',
        20.99,
        3,
        2);

-- INSERT INTO inbound_order (order_datetime, operator_id) VALUES (2021-09-09 14:13:19, 1);
-- INSERT INTO inbound_order (order_datetime, operator_id) VALUES (2021-08-09 14:13:19, 1);
-- INSERT INTO inbound_order (order_datetime, operator_id) VALUES (2021-07-09 14:13:19, 1);
-- INSERT INTO inbound_order (order_datetime, operator_id) VALUES (2021-07-15 16:13:19, 2);
-- INSERT INTO inbound_order (order_datetime, operator_id) VALUES (2021-07-09 14:13:19, 2);
-- INSERT INTO inbound_order (order_datetime, operator_id) VALUES (2021-07-27 12:13:19, 2);
--
-- INSERT INTO batch (quantity,
--                    current_temperature,
--                    manufacturing_date,
--                    manufacturing_time,
--                    due_date,
--                    product_id,
--                    section_id,
--                    inbound_order_id,
--                    batch_number)
-- VALUES (12,
--         10,
--         2021-06-27,
--         12:13:19,
--         2021-12-27,
--         1,
--         1,
--         1,
--            'TESTE');
--
-- INSERT INTO batch (quantity,
--                    current_temperature,
--                    manufacturing_date,
--                    manufacturing_time,
--                    due_date,
--                    product_id,
--                    section_id,
--                    inbound_order_id,
--                    batch_number)
-- VALUES (17,
--         2,
--         2021-06-27,
--         12:13:19,
--         2021-10-02,
--         1,
--         1,
--         2,
--            'TESTE2');
--
-- INSERT INTO batch (quantity,
--                    current_temperature,
--                    manufacturing_date,
--                    manufacturing_time,
--                    due_date,
--                    product_id,
--                    section_id,
--                    inbound_order_id,
--                    batch_number)
-- VALUES (13,
--         2,
--         2021-06-27,
--         12:13:19,
--         2021-08-02,
--         1,
--         2,
--         2,
--            'TESTE3');
--
-- INSERT INTO batch (quantity,
--                    current_temperature,
--                    manufacturing_date,
--                    manufacturing_time,
--                    due_date,
--                    product_id,
--                    section_id,
--                    inbound_order_id,
--                    batch_number)
-- VALUES (23,
--         -1,
--         2021-06-27,
--         12:13:19,
--         2021-07-12,
--         2,
--         2,
--         2,
--            'TESTE4');
--
-- INSERT INTO purchase_order (order_datetime, status, buyer_id) VALUES (2021-07-08 12:13:19, 'OPEN', 1);
-- INSERT INTO purchase_order (order_datetime, status, buyer_id) VALUES (2021-07-07 12:13:19, 'OPEN', 1);
-- INSERT INTO purchase_order (order_datetime, status, buyer_id) VALUES (2021-07-06 12:13:19, 'OPEN', 2);
--
-- INSERT INTO purchase_order_item (quantity, total_price, batch_id, order_id)
-- VALUES (2, 4,78, 1, 1);
--
-- INSERT INTO purchase_order_item (quantity, total_price, batch_id, order_id)
-- VALUES (2, 7.96, 2, 1);
--
-- INSERT INTO purchase_order_item (quantity, total_price, batch_id, order_id)
-- VALUES (2, 4,78, 1, 2);
--
-- INSERT INTO purchase_order_item (quantity, total_price, batch_id, order_id)
-- VALUES (2, 7.96, 2, 2);
--
-- INSERT INTO purchase_order_item (quantity, total_price, batch_id, order_id)
-- VALUES (2, 4,78, 1, 3);
--
-- INSERT INTO purchase_order_item (quantity, total_price, batch_id, order_id)
-- VALUES (2, 6.2, 3, 3);