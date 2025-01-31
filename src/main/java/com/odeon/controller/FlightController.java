package com.odeon.controller;

import com.odeon.dto.FlightDTO;
import com.odeon.model.ApiResponse;
import com.odeon.service.FlightService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FlightDTO>> createFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO created = flightService.createFlight(flightDTO);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Flight created successfully", created));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> searchFlights(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @Parameter(description = "Date in format dd-MM-yyyy", example = "25-12-2024")
            @RequestParam String date) {
        List<FlightDTO> flights = flightService.searchFlights(departureCity, arrivalCity, date);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Flights found", flights));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getFlightsByCity(@PathVariable String city) {
        List<FlightDTO> flights = flightService.getFlightsByDepartureCity(city);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Flights found", flights));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Flights found", flights));
    }
} 
