
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


Ao iniciar a API, é feita uma carga inicial de dados que encontra-se no arquivo <b>import.sql</b> <br>

import.sql

Exemplo de POST para <b>"/competicoes"</b>:
<br>

{<br>
  "modalidade":{"id":9},<br>
  "local":{"id":4},<br>
  "dataInicio":"2020-07-15 10:00",<br>
  "dataTermino":"2020-07-15 11:00",<br>
  "competidorA":{"id":1},<br>
  "competidorB":{"id":5},<br>
  "etapa":"ELIMINATORIAS"<br>
}<br>
