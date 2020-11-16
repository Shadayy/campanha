package com.victor.campanha.controller;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.campanha.dto.CampanhaDTO;
import com.victor.campanha.generator.CampanhaDTOGenerator;
import com.victor.campanha.service.CampanhaService;

@WebMvcTest(controllers = CampanhaController.class)
class CampanhaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private CampanhaService campanhaService;
	
	@Test
	void when_post_campanha_without_error_then_returnCreated() {
		CampanhaDTO campanhaDTO = new CampanhaDTOGenerator().generateValidCampanhaDTO();
		
		performAndExpect(
				post("/campanha"),
				status().isCreated(), 
				campanhaDTO
		);
	}
	
	@Test
	void when_post_campanha_with_error_then_returnBadRequest() throws Exception {
		performAndExpect(
				post("/campanha"),
				status().isBadRequest(), 
				new CampanhaDTOGenerator().getInvalidListCampanhaDTO()
		);
	}
	
	@Test
	void when_put_campanha_without_error_then_returnOk() throws Exception {
		CampanhaDTO campanhaDTO = new CampanhaDTOGenerator().generateValidCampanhaDTO();
		Long idCampanha = 1L;
		
		performAndExpect(
				put("/campanha/{id}", idCampanha),
				status().isOk(), 
				campanhaDTO
		);
	}
	
	@Test
	void when_put_campanha_with_error_then_returnBadRequest() throws Exception {
		performAndExpect(
				post("/campanha"),
				status().isBadRequest(), 
				new CampanhaDTOGenerator().getInvalidListCampanhaDTO()
		);
	}
	
	@Test
	void when_get_campanha_id_without_error_then_returnOk() throws Exception {
		Long idCampanha = 1L;
		
		performAndExpect(
				get("/campanha/{id}", idCampanha),
				status().isOk()
		);
	}
	
	@Test
	void when_get_campanha_id_without_error_then_returnBadRequest() throws Exception {
		Long idCampanha = null;
		
		performAndExpect(
				get("/campanha/{id}", idCampanha),
				status().isBadRequest()
		);
	}
	
	@Test
	void when_get_listar_campanha_then_returnOk() throws Exception {
		performAndExpect(
				get("/campanha/listar"),
				status().isOk()
		);
	}
	
	@Test
	void when_get_listar_with_valid_id_then_returnOk() throws Exception {
		Long idTimeCoracao = 1L;
		
		performAndExpect(
				get("/campanha/listar/{idTimeCoracao}", idTimeCoracao),
				status().isOk()
		);
	}
	
	@Test
	void when_delete_campanha_with_error_then_returnBadRequest() throws Exception {
		Long idCampanha = null;
		
		performAndExpect(
				get("/campanha/{id}", idCampanha),
				status().isBadRequest()
		);
	}
	
	@Test
	void when_delete_campanha_without_error_then_returnOk() throws Exception {
		Long idCampanha = 1L;
		
		performAndExpect(
				get("/campanha/{id}", idCampanha),
				status().isOk()
		);
	}
	
	private void performAndExpect(MockHttpServletRequestBuilder mockRequestBuilder, ResultMatcher resultMatcher) {
		try {
			this.mockMvc.perform(
					mockRequestBuilder
					.contentType("application/json")
				)
				.andExpect(
						resultMatcher
				)
				.andReturn()
				;
		} catch (Exception e) {
			fail("Exception in performPostAndExpectBadRequest");
			throw new RuntimeException(e);
		}
	}
	
	private void performAndExpect(MockHttpServletRequestBuilder mockRequestBuilder, ResultMatcher resultMatcher, Object object) {
		try {
			this.mockMvc.perform(
					mockRequestBuilder
					.contentType("application/json")
					.content(this.objectMapper.writeValueAsString(object))
				)
				.andExpect(
						resultMatcher
				)
				.andReturn()
				;
		} catch (Exception e) {
			fail("Exception in performPostAndExpectBadRequest");
			throw new RuntimeException(e);
		}
	}
}
