
<b>Olimpico API</b>
<br><br>
API Desenvolvida com Spring Boot por ser um framework que auxilia no desenvolvimento rápido com o Spring, facilitando toda a configuraçao que é necessaria para utilizar o Spring
<br><br>
<b>Nesse projeto utilizei recursos como</b>:<br>
<br>
Um uso simples com Listeners e Events<br>
Bean validation<br>
Melhor tratamento de exceções com uso do ResponseEntityExceptionHandler e MessageResource<br>
HQSQL como banco embarcado<br>
MockMvc para teste unitário de requisições HTTP REST<br>


<b>Ao iniciar a API, é feita uma carga inicial de dados</b> <br>

-- Modalidades<br>

INSERT INTO modalidade (id,nome) VALUES ('1', 'Futebol');<br>
INSERT INTO modalidade (id,nome) VALUES ('2', 'Atletismo');<br>
INSERT INTO modalidade (id,nome) VALUES ('3', 'Basquetebol');<br>
INSERT INTO modalidade (id,nome) VALUES ('4', 'Boxe');<br>
INSERT INTO modalidade (id,nome) VALUES ('5', 'Canoagem');<br>
INSERT INTO modalidade (id,nome) VALUES ('6', 'Ciclismo');<br>
INSERT INTO modalidade (id,nome) VALUES ('7', 'Golfe');<br>
INSERT INTO modalidade (id,nome) VALUES ('8', 'Hipismo');<br>
INSERT INTO modalidade (id,nome) VALUES ('9', 'Judô');<br>
INSERT INTO modalidade (id,nome) VALUES ('10', 'Natação');<br>
INSERT INTO modalidade (id,nome) VALUES ('11', 'Remo');<br>
INSERT INTO modalidade (id,nome) VALUES ('12', 'Rugby');<br>
INSERT INTO modalidade (id,nome) VALUES ('13', 'Taekwondo');<br>
INSERT INTO modalidade (id,nome) VALUES ('14', 'Tênis');<br>
INSERT INTO modalidade (id,nome) VALUES ('15', 'Triatlo');<br>
INSERT INTO modalidade (id,nome) VALUES ('16', 'Vela');<br>
INSERT INTO modalidade (id,nome) VALUES ('17', 'Voleibol');<br>


--Local<br>
INSERT INTO local (id,nome) VALUES ('1', 'Ginásio Nacional de Yoyogi');<br>
INSERT INTO local (id,nome) VALUES ('2', 'Ginásio Metropolitano de Tóquio');<br>
INSERT INTO local (id,nome) VALUES ('3', 'Nippon Budokan');<br>
INSERT INTO local (id,nome) VALUES ('4', 'Palácio Imperial de Tóquio');<br>
INSERT INTO local (id,nome) VALUES ('5', 'Kokugikan Arena');<br>

--Competidor<br>
INSERT INTO competidor (id,nome) VALUES (1,'Brasil');<br>
INSERT INTO competidor (id,nome) VALUES (2,'Estados Unidos');<br>
INSERT INTO competidor (id,nome) VALUES (3,'França');<br>
INSERT INTO competidor (id,nome) VALUES (4,'Inglaterra');<br>
INSERT INTO competidor (id,nome) VALUES (5,'Alemanha');<br>
INSERT INTO competidor (id,nome) VALUES (6,'Italia');<br>
INSERT INTO competidor (id,nome) VALUES (7,'México');<br>
INSERT INTO competidor (id,nome) VALUES (8,'Suiça');<br>
INSERT INTO competidor (id,nome) VALUES (9,'Argentina');<br>
INSERT INTO competidor (id,nome) VALUES (10,'Espanha');<br>
INSERT INTO competidor (id,nome) VALUES (11,'Canáda');<br>
INSERT INTO competidor (id,nome) VALUES (12,'China');<br>

--Competições<br>
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (1, 9, 4, 1, 2, 'OITAVAS', '2020-07-15 09:30:00', '2020-07-15 11:00:00');<br>
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (2, 1, 2, 7, 9, 'SEMIFINAL', '2020-07-17 10:30:00', '2020-07-17 12:30:00');<br>
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (3, 12, 5, 3, 12, 'OITAVAS', '2020-07-20 11:30:00', '2020-07-20 13:00:00');<br>
INSERT INTO competicao (id, modalidade_id, local_id, competidor_1_id, competidor_2_id, etapa, data_inicio, data_termino) VALUES (4, 7, 5, 5, 4, 'QUARTAS', '2020-07-20 15:30:00', '2020-07-19 17:00:00');<br>

Exemplo de POST para <b>"/competicoes"</b>:
<br>

{<br>
  "modalidade":{"id":9},<br>
  "local":{"id":4},<br>
  "dataInicio":"2020-07-15 10:00",<br>
  "dataTermino":"2020-07-15 11:00",v
  "competidorA":{"id":1},<br>
  "competidorB":{"id":5},v
  "etapa":"ELIMINATORIAS"<br>
}<br>
