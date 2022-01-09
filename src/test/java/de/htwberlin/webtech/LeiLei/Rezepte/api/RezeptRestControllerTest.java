package de.htwberlin.webtech.LeiLei.Rezepte.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwberlin.webtech.LeiLei.persistence.RezeptEntity;
import de.htwberlin.webtech.LeiLei.persistence.RezeptRepository;
import de.htwberlin.webtech.LeiLei.service.RezeptService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RezeptRestController.class)
class RezeptRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper     mapper;
    @MockBean
    RezeptService    service;
    @MockBean
    RezeptRepository repository;

    Rezept       REZEPT_1           = new Rezept(1L, "Rezept1", "bild1.jpg", "Zutaten1", 0, 15L);
    Rezept       REZEPT_2           = new Rezept(2L, "Rezept2", "bild2.jpg", "Zutaten2", 0, 30L);
    Rezept       REZEPT_2_UPDATED   = new Rezept(2L, "Rezept2", "bild2.jpg", "Zutaten2, Zutat xy", 1, 30L);
    Rezept       REZEPT_POST        = new Rezept(null, "Rezept3", null, "Zutaten3", 2, 45L);
    RezeptEntity REZEPT_POST_ENTITY = new RezeptEntity("Rezept3", null, "Zutaten3", 2, 45L);
    Rezept       REZEPT_POST_SAVED  = new Rezept(3L, "Rezept3", null, "Zutaten3", 2, 45L);

    @Test
    @DisplayName("Fetch all Rezepte")
    void fetchRezepte() throws Exception {
        List<Rezept> rezepte = Arrays.asList(REZEPT_1, REZEPT_2);

        when(service.findAll()).thenReturn(rezepte);

        MvcResult result = mockMvc.perform(get("/api/v1/rezepte").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].ingredient").value("Zutaten2"))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Fetch existing Rezept by id and get 200")
    void fetchRezeptById_200() throws Exception {
        when(service.findById(REZEPT_1.getId())).thenReturn(REZEPT_1);

        MvcResult result = mockMvc
                .perform(get("/api/v1/rezepte/" + REZEPT_1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(REZEPT_1.getId()))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Fetch not existing Rezept by id and get 404")
    void fetchRezeptById_404() throws Exception {
        when(service.findById(any())).thenReturn(null);
        long notExistingId = 123L;

        MvcResult result = mockMvc
                .perform(get("/api/v1/rezepte/" + notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Create new Rezept and get 201")
    void createRezept_201() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "DUMMY_IMAGE".getBytes()
        );
        Mockito.when(repository.save(any())).thenReturn(REZEPT_POST_ENTITY);
        Mockito.when(service.create(any(), any())).thenReturn(REZEPT_POST_SAVED);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .multipart("/api/v1/rezepte")
                .file(file)
                .param("name", REZEPT_POST.getName())
                .param("ingredient", REZEPT_POST.getIngredient())
                .param("difficulty", String.valueOf(REZEPT_POST.getDifficulty()))
                .param("time", String.valueOf(REZEPT_POST.getTime()));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(REZEPT_POST_SAVED.getId()))
                .andExpect(jsonPath("$.name").value(REZEPT_POST_SAVED.getName()))
                .andExpect(jsonPath("$.ingredient").value(REZEPT_POST_SAVED.getIngredient()))
                .andExpect(jsonPath("$.difficulty").value(REZEPT_POST_SAVED.getDifficulty()))
                .andExpect(jsonPath("$.time").value(REZEPT_POST_SAVED.getTime()))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Update existing Rezept and get 200")
    void updateRezept_200() throws Exception {
        Mockito.when(service.update(eq(REZEPT_2.getId()), any())).thenReturn(REZEPT_2_UPDATED);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/v1/rezepte/" + REZEPT_2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(REZEPT_2_UPDATED));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(REZEPT_2_UPDATED.getId()))
                .andExpect(jsonPath("$.name").value(REZEPT_2_UPDATED.getName()))
                .andExpect(jsonPath("$.ingredient").value(REZEPT_2_UPDATED.getIngredient()))
                .andExpect(jsonPath("$.difficulty").value(REZEPT_2_UPDATED.getDifficulty()))
                .andExpect(jsonPath("$.time").value(REZEPT_2_UPDATED.getTime()))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Try to update not existing Rezept and get 404")
    void updateRezept_404() throws Exception {
        Mockito.when(service.update(any(), any())).thenReturn(null);

        long notExistingId = 123L;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/v1/rezepte/" + notExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(REZEPT_2_UPDATED));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Delete existing Rezept and get 200")
    void deleteRezept_200() throws Exception {
        Mockito.when(service.deleteById(eq(REZEPT_1.getId()))).thenReturn(REZEPT_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/v1/rezepte/" + REZEPT_1.getId());

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(REZEPT_1.getId()))
                .andExpect(jsonPath("$.name").value(REZEPT_1.getName()))
                .andExpect(jsonPath("$.ingredient").value(REZEPT_1.getIngredient()))
                .andExpect(jsonPath("$.difficulty").value(REZEPT_1.getDifficulty()))
                .andExpect(jsonPath("$.time").value(REZEPT_1.getTime()))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Try to delete not existing Rezept and get 404")
    void deleteRezept_404() throws Exception {
        Mockito.when(service.deleteById(any())).thenReturn(null);

        long notExistingId = 123L;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/v1/rezepte/" + notExistingId);

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }
}