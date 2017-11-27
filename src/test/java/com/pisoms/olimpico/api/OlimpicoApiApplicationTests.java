package com.pisoms.olimpico.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisoms.olimpico.api.model.Competidor;
import com.pisoms.olimpico.api.model.Local;
import com.pisoms.olimpico.api.model.Modalidade;
import com.pisoms.olimpico.api.type.EtapaType;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
@ActiveProfiles("scratch")
public class OlimpicoApiApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void retorneOrdenadoPorDateAsc() throws Exception {
		this.mockMvc.perform(get("/competicoes/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", equalTo(1))).andExpect(jsonPath("$[1].id", equalTo(2)))
				.andExpect(jsonPath("$[2].id", equalTo(3))).andExpect(jsonPath("$[3].id", equalTo(4)));
	}

	@Test
	public void retorneUmaCompeticaoComDeterminadaModalidade() throws Exception {
		this.mockMvc.perform(get("/competicoes/modalidade/12").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id", equalTo(3)));
	}

	@Test
	public void retorneNaoEncontradoParaPesquisaDeDeterminadaModalidade() throws Exception {
		this.mockMvc.perform(get("/competicoes/modalidade/21").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$[0].mensagemUsuario", is("Recurso não encontrado")));
	}

	@Test
	public void retorneNoContentAoDeletarUmaCompeticao() throws Exception {
		this.mockMvc.perform(delete("/competicoes/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void retorneNotFoundAoBuscarUmaCompeticaoNaoCadastrada() throws Exception {
		this.mockMvc.perform(get("/competicoes/33").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void salveUmaCompeticaoComSucesso() throws Exception {

		LocalDateTime ldt = LocalDateTime.of(2020, 9, 10, 2, 0);
		Date initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 9, 10, 2, 30);
		Date finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ObjectMapper mapper = new ObjectMapper();
		CompeticaoRequest competicaoRequest = new CompeticaoRequest();
		competicaoRequest.setModalidade(new Modalidade(1));
		competicaoRequest.setLocal(new Local(1));
		competicaoRequest.setCompetidorA(new Competidor(1));
		competicaoRequest.setCompetidorB(new Competidor(5));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		competicaoRequest.setEtapa(EtapaType.ELIMINATORIAS);

		String content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", equalTo(5)));
	}

	@Test
	public void retorneBadRequestParaConflitoDeCompeticaoEmMesmoLocalModalidadeEHora() throws Exception {

		LocalDateTime ldt = LocalDateTime.of(2020, 7, 15, 10, 0);
		Date initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 7, 15, 11, 0);
		Date finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ObjectMapper mapper = new ObjectMapper();

		CompeticaoRequest competicaoRequest = new CompeticaoRequest();
		competicaoRequest.setModalidade(new Modalidade(9));
		competicaoRequest.setLocal(new Local(4));
		competicaoRequest.setCompetidorA(new Competidor(1));
		competicaoRequest.setCompetidorB(new Competidor(5));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		competicaoRequest.setEtapa(EtapaType.ELIMINATORIAS);

		String content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].mensagemUsuario",
						is("Competição não pode ser adicionada pois conflita com outra no periodo proposto")));
	}

	@Test
	public void retorneBadRequestQuandoUmaCompeticaoForMenorQue30Minutos() throws Exception {
		LocalDateTime ldt = LocalDateTime.of(2020, 7, 15, 10, 0);
		Date initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 7, 15, 10, 25);
		Date finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ObjectMapper mapper = new ObjectMapper();

		CompeticaoRequest competicaoRequest = new CompeticaoRequest();
		competicaoRequest.setModalidade(new Modalidade(4));
		competicaoRequest.setLocal(new Local(2));
		competicaoRequest.setCompetidorA(new Competidor(1));
		competicaoRequest.setCompetidorB(new Competidor(5));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		competicaoRequest.setEtapa(EtapaType.ELIMINATORIAS);

		String content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].mensagemUsuario",
						is("Uma Competição deve ter no minimo 30 minutos de duração")));
	}

	@Test
	public void shouldReturnBadRequestOnlyCompetitionBetweenSameCountryIsPermittedSemifinalsFinals() throws Exception {
		LocalDateTime ldt = LocalDateTime.of(2020, 7, 15, 10, 0);
		Date initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 7, 15, 10, 45);
		Date finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ObjectMapper mapper = new ObjectMapper();

		CompeticaoRequest competicaoRequest = new CompeticaoRequest();
		competicaoRequest.setModalidade(new Modalidade(4));
		competicaoRequest.setLocal(new Local(2));
		competicaoRequest.setCompetidorA(new Competidor(5));
		competicaoRequest.setCompetidorB(new Competidor(5));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		competicaoRequest.setEtapa(EtapaType.ELIMINATORIAS);

		String content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].mensagemUsuario", is(
						"Não é possivel Competidores iguais para uma a mesma competição que não seja final ou semifinal.")));
	}

	@Test
	public void retorneBadRequestCasoTenteCadastrarMaisDe4CompeticoesNoMesmoLocalNoMesmoDia() throws Exception {
		LocalDateTime ldt = LocalDateTime.of(2020, 7, 20, 19, 0);
		Date initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 7, 20, 19, 45);
		Date finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ObjectMapper mapper = new ObjectMapper();
		
		CompeticaoRequest competicaoRequest = new CompeticaoRequest();
		competicaoRequest.setModalidade(new Modalidade(4));
		competicaoRequest.setLocal(new Local(5));
		competicaoRequest.setCompetidorA(new Competidor(5));
		competicaoRequest.setCompetidorB(new Competidor(4));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		competicaoRequest.setEtapa(EtapaType.ELIMINATORIAS);

		String content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content));

		ldt = LocalDateTime.of(2020, 7, 20, 21, 0);
		initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 7, 20, 21, 45);
		finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		competicaoRequest.setModalidade(new Modalidade(4));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content));

		ldt = LocalDateTime.of(2020, 7, 20, 22, 0);
		initialDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		ldt = LocalDateTime.of(2020, 7, 20, 23, 0);
		finishDate = Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());

		competicaoRequest.setModalidade(new Modalidade(4));
		competicaoRequest.setDataInicio(initialDate);
		competicaoRequest.setDataTermino(finishDate);
		content = mapper.writeValueAsString(competicaoRequest);

		this.mockMvc.perform(post("/competicoes/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$[0].mensagemUsuario",
						is("Somente permitido 4 competição no mesmo dia e no mesmo local")));
	}
}
