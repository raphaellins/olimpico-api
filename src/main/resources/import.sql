-- Modalidades

INSERT INTO modalidade (id,nome) VALUES ('1', 'Futebol');
INSERT INTO modalidade (id,nome) VALUES ('2', 'Atletismo');
INSERT INTO modalidade (id,nome) VALUES ('3', 'Basquetebol');
INSERT INTO modalidade (id,nome) VALUES ('4', 'Boxe');
INSERT INTO modalidade (id,nome) VALUES ('5', 'Canoagem');
INSERT INTO modalidade (id,nome) VALUES ('6', 'Ciclismo');
INSERT INTO modalidade (id,nome) VALUES ('7', 'Golfe');
INSERT INTO modalidade (id,nome) VALUES ('8', 'Hipismo');
INSERT INTO modalidade (id,nome) VALUES ('9', 'Judô');
INSERT INTO modalidade (id,nome) VALUES ('10', 'Natação');
INSERT INTO modalidade (id,nome) VALUES ('11', 'Remo');
INSERT INTO modalidade (id,nome) VALUES ('12', 'Rugby');
INSERT INTO modalidade (id,nome) VALUES ('13', 'Taekwondo');
INSERT INTO modalidade (id,nome) VALUES ('14', 'Tênis');
INSERT INTO modalidade (id,nome) VALUES ('15', 'Triatlo');
INSERT INTO modalidade (id,nome) VALUES ('16', 'Vela');
INSERT INTO modalidade (id,nome) VALUES ('17', 'Voleibol');

--Local
INSERT INTO local (id,nome) VALUES ('1', 'Ginásio Nacional de Yoyogi');
INSERT INTO local (id,nome) VALUES ('2', 'Ginásio Metropolitano de Tóquio');
INSERT INTO local (id,nome) VALUES ('3', 'Nippon Budokan');
INSERT INTO local (id,nome) VALUES ('4', 'Palácio Imperial de Tóquio');
INSERT INTO local (id,nome) VALUES ('5', 'Kokugikan Arena');

--Competidor
INSERT INTO competidor (id,nome) VALUES (1,'Brasil');
INSERT INTO competidor (id,nome) VALUES (2,'Estados Unidos');
INSERT INTO competidor (id,nome) VALUES (3,'França');
INSERT INTO competidor (id,nome) VALUES (4,'Inglaterra');
INSERT INTO competidor (id,nome) VALUES (5,'Alemanha');
INSERT INTO competidor (id,nome) VALUES (6,'Italia');
INSERT INTO competidor (id,nome) VALUES (7,'México');
INSERT INTO competidor (id,nome) VALUES (8,'Suiça');
INSERT INTO competidor (id,nome) VALUES (9,'Argentina');
INSERT INTO competidor (id,nome) VALUES (10,'Espanha');
INSERT INTO competidor (id,nome) VALUES (11,'Canáda');
INSERT INTO competidor (id,nome) VALUES (12,'China');

--Competições
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (1, 9, 4, 1, 2, 'OITAVAS', '2020-07-15 09:30:00', '2020-07-15 11:00:00');
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (2, 1, 2, 7, 9, 'SEMIFINAL', '2020-07-17 10:30:00', '2020-07-17 12:30:00');
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (3, 12, 5, 3, 12, 'OITAVAS', '2020-07-20 11:30:00', '2020-07-20 13:00:00');
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (4, 7, 5, 5, 4, 'QUARTAS', '2020-07-20 15:30:00', '2020-07-19 17:00:00');
