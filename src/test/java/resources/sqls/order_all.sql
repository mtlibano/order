insert into city(id, description, uf) values (1, 'Tubarão', 'SC');
insert into city(id, description, uf) values (2, 'Gravatal', 'SC');
insert into city(id, description, uf) values (3, 'Jaguaruna', 'SC');
insert into city(id, description, uf) values (4, 'Laguna', 'SC');
insert into city(id, description, uf) values (5, 'Torres', 'RS');

insert into rating(id, grade, comment) values (1, 1, 'Comment1');
insert into rating(id, grade, comment) values (2, 3, 'Comment2');
insert into rating(id, grade, comment) values (3, 2, 'Comment3');
insert into rating(id, grade, comment) values (4, 3, 'Comment4');
insert into rating(id, grade, comment) values (5, 1, 'Comment5');
insert into rating(id, grade, comment) values (6, 2, 'Comment6');

insert into payment values (1, 'Pix');
insert into payment values (2, 'Boleto');
insert into payment values (3, 'Avista');
insert into payment values (4, 'Cartão Débito');
insert into payment values (5, 'Cartão Crédito');

insert into client(id, birth_date, cep, cpf, district, email, name, number, street, city_id) values (1, '1992-10-24 00:00:00.000-03:00', '88708600','08124991901', 'Bairro1', 'max@trier.com', 'Max', '1', 'Rua1', 1);
insert into client(id, birth_date, cep, cpf, district, email, name, number, street, city_id) values (2, '1997-10-24 00:00:00.000-03:00', '12345678','12345678910', 'Bairro2', 'niki@trier.com', 'Niki', '2', 'Rua2', 1);
insert into client(id, birth_date, cep, cpf, district, email, name, number, street, city_id) values (3, '2000-10-24 00:00:00.000-03:00', '12345678','12345678911', 'Bairro3', 'mari@trier.com', 'Mari', '3', 'Rua3', 2);

insert into phone_number(id, phone_number, client_id) values (1, '984762611', 1);
insert into phone_number(id, phone_number, client_id) values (2, '999998888', 1);
insert into phone_number(id, phone_number, client_id) values (3, '988889999', 2);

insert into product(id, description, price, barcode) values (1, 'Martelo', 10, '1234567891012');
insert into product(id, description, price, barcode) values (2, 'Alicate', 4.66, '1234567891013');
insert into product(id, description, price, barcode) values (3, 'Prego', 1, '1234567891014');
insert into product(id, description, price, barcode) values (4, 'Trena', 3.50, '1234567891015');
insert into product(id, description, price, barcode) values (5, 'Serrote', 12.8, '1234567891016');
insert into product(id, description, price, barcode) values (6, 'Serra', 20, '1234567891017');

insert into orders(id, date, client_id, payment_id, rating_id) values (1, '2023-06-01 00:00:00.000-03:00', 1, 1, 1);
insert into orders(id, date, client_id, payment_id, rating_id) values (2, '2023-06-01 00:00:00.000-03:00', 2, 2, 2);
insert into orders(id, date, client_id, payment_id, rating_id) values (3, '2023-06-02 00:00:00.000-03:00', 1, 2, 3);
insert into orders(id, date, client_id, payment_id, rating_id) values (4, '2023-06-02 00:00:00.000-03:00', 2, 2, 4);
insert into orders(id, date, client_id, payment_id, rating_id) values (5, '2023-06-03 00:00:00.000-03:00', 2, 1, 5);

insert into product_order(id, quantity, order_id, product_id) values (1, 4, 1, 1);
insert into product_order(id, quantity, order_id, product_id) values (2, 2, 2, 2);
insert into product_order(id, quantity, order_id, product_id) values (3, 6, 2, 3);
insert into product_order(id, quantity, order_id, product_id) values (4, 2, 2, 4);
insert into product_order(id, quantity, order_id, product_id) values (5, 1, 3, 1);
insert into product_order(id, quantity, order_id, product_id) values (6, 2, 3, 2);
insert into product_order(id, quantity, order_id, product_id) values (7, 4, 4, 1);
insert into product_order(id, quantity, order_id, product_id) values (8, 2, 4, 2);