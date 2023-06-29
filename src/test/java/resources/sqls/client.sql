insert into city(id, description, uf) values (1, 'Tubarão', 'SC');
insert into city(id, description, uf) values (2, 'Gravatal', 'SC');
insert into city(id, description, uf) values (3, 'Jaguaruna', 'SC');
insert into city(id, description, uf) values (4, 'Laguna', 'SC');
insert into city(id, description, uf) values (5, 'Torres', 'RS');

insert into client(id, birth_date, cep, cpf, district, email, name, number, street, city_id) values (1, '1992-10-24 00:00:00.000-03:00', '88708600','08124991901', 'Bairro1', 'max@trier.com', 'Max', '1', 'Rua1', 1);
insert into client(id, birth_date, cep, cpf, district, email, name, number, street, city_id) values (2, '1997-10-24 00:00:00.000-03:00', '12345678','12345678910', 'Bairro2', 'niki@trier.com', 'Niki', '2', 'Rua2', 1);
insert into client(id, birth_date, cep, cpf, district, email, name, number, street, city_id) values (3, '2000-10-24 00:00:00.000-03:00', '12345678','12345678911', 'Bairro3', 'mari@trier.com', 'Mari', '3', 'Rua3', 2);

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