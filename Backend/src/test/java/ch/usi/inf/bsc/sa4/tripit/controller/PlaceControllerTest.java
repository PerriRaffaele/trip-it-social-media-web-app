package ch.usi.inf.bsc.sa4.tripit.controller;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AirportDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.AttractionDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.StationDTO;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.service.LocationService;
import ch.usi.inf.bsc.sa4.tripit.service.PlaceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Place Controller Tests")
public class PlaceControllerTest {
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean PlaceService placeService;
  @MockBean LocationService locationService;

  private static Attraction attraction;
  private static Station station;
  private static Airport airport;
  private static Location location;
  private static List<Place> places;
  private static LocationDTO locationDTO;
  private static double a, b;

  @BeforeAll
  public static void dataSetUp() {
    location = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    attraction = new Attraction("peking", location, "city");
    station = new Station("luganoStation", location);
    airport = new Airport("LuganoAirport", location, "LUG111");
    places = new ArrayList<Place>();
    places.add(attraction);
    a = 1.11;
    b = 2.22;
    locationDTO = location.toLocationDTO();
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    given(placeService.getAll()).willReturn(places);
    given(placeService.searchByNameStartingWith("pe")).willReturn(places);
    given(placeService.getById("wp1")).willReturn(Optional.empty());
    given(placeService.getById("p1")).willReturn(Optional.of(attraction));
    given(placeService.saveAttraction(any(), any())).willReturn(attraction);
    given(placeService.saveStation(any(), any())).willReturn(station);
    given(placeService.saveAirport(any(), any())).willReturn(airport);
    given(locationService.saveLocation(any())).willReturn(location);
  }

  @Test
  public void testGetAll() throws Exception {
    MvcResult result = this.mockMvc.perform(get("/places")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    List<Attraction> output =
        objectMapper.readValue(response, new TypeReference<List<Attraction>>() {});
    assertEquals(output.size(), 1);
  }

  @Test
  public void testSearch() throws Exception {
    String search = "pe";
    MvcResult result =
        this.mockMvc
            .perform(get("/places/search").param("partialName", search))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    List<Attraction> output =
        objectMapper.readValue(response, new TypeReference<List<Attraction>>() {});
    assertEquals(output.size(), 1);
  }

  @Test
  public void testGetById() throws Exception {
    this.mockMvc.perform(get("/places/wp1")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc.perform(get("/places/p1")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    Attraction output = objectMapper.readValue(response, Attraction.class);
    assertNotNull(output);
  }

  @Test
  public void testAddAttraction() throws Exception {
    AttractionDTO attractionDTO =
        new AttractionDTO("Somewhere", locationDTO, "link", "attraction", "att1");
    String correct = objectMapper.writeValueAsString(attractionDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/places/attraction").contentType(MediaType.APPLICATION_JSON).content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    AttractionDTO output = objectMapper.readValue(response, AttractionDTO.class);
    assertNotNull(output);
  }

  @Test
  public void testAddStation() throws Exception {
    StationDTO stationDTO = new StationDTO("Somewhere", locationDTO, "attraction", "att1");
    String correct = objectMapper.writeValueAsString(stationDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/places/station").contentType(MediaType.APPLICATION_JSON).content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    StationDTO output = objectMapper.readValue(response, StationDTO.class);
    assertNotNull(output);
  }

  @Test
  public void testAddAirport() throws Exception {
    AirportDTO airportDTO =
        new AirportDTO("Somewhere", locationDTO, "LUG111", "attraction", "att1");
    String correct = objectMapper.writeValueAsString(airportDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/places/airport").contentType(MediaType.APPLICATION_JSON).content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    AirportDTO output = objectMapper.readValue(response, AirportDTO.class);
    assertNotNull(output);
  }
}
