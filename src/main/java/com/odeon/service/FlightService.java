package com.odeon.service;

import com.odeon.dto.FlightDTO;
import com.odeon.entity.Flight;
import com.odeon.exception.FlightTimeConflictException;
import com.odeon.mapper.FlightMapper;
import com.odeon.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    @Transactional
    public FlightDTO createFlight(FlightDTO flightDTO) {
        validateFlightTimes(flightDTO.getDepartureCity(), flightDTO.getArrivalCity(),
                flightDTO.getDepartureTime(), flightDTO.getArrivalTime());

        Flight flight = flightMapper.toEntity(flightDTO);
        flight = flightRepository.save(flight);
        return flightMapper.toDTO(flight);
    }

    public List<FlightDTO> searchFlights(String departureCity, String arrivalCity, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return flightRepository
                .findByDepartureCityAndArrivalCityAndDepartureTimeBetween(
                        departureCity, arrivalCity, startOfDay, endOfDay)
                .stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> getFlightsByDepartureCity(String city) {
        return flightRepository.findByDepartureCity(city)
                .stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateFlightTimes(String departureCity, String arrivalCity,
                                     LocalDateTime departureTime, LocalDateTime arrivalTime) {
        if (departureTime.isAfter(arrivalTime)) {
            throw new FlightTimeConflictException("Kalkış zamanı varış zamanından sonra olamaz");
        }

        // Kalkış şehrindeki çakışmaları kontrol et
        LocalDate departureDate = departureTime.toLocalDate();
        List<Flight> departureConflicts = flightRepository.findConflictingFlights(
                departureCity,
                departureDate.atStartOfDay(),
                departureDate.atTime(23, 59, 59)
        );

        // Aynı gün içindeki 30 dakikalık çakışmaların kontrolü
        for (Flight conflict : departureConflicts) {
            if (isTimeConflict(departureTime, conflict.getDepartureTime()) ||
                    isTimeConflict(departureTime, conflict.getArrivalTime())) {
                throw new FlightTimeConflictException(
                        String.format("Kalkış şehrinde (%s) çakışan uçuş bulunmaktadır", departureCity)
                );
            }
        }

        // Varış şehrindeki çakışmaların kontrolü
        LocalDate arrivalDate = arrivalTime.toLocalDate();
        List<Flight> arrivalConflicts = flightRepository.findConflictingFlights(
                arrivalCity,
                arrivalDate.atStartOfDay(),
                arrivalDate.atTime(23, 59, 59)
        );

        // Aynı gün içindeki 30 dakikalık çakışmaların kontrolü
        for (Flight conflict : arrivalConflicts) {
            if (isTimeConflict(arrivalTime, conflict.getDepartureTime()) ||
                    isTimeConflict(arrivalTime, conflict.getArrivalTime())) {
                throw new FlightTimeConflictException(
                        String.format("Varış şehrinde (%s) çakışan uçuş bulunmaktadır", arrivalCity)
                );
            }
        }
    }

    private boolean isTimeConflict(LocalDateTime time1, LocalDateTime time2) {
        long minutesDifference = Math.abs(ChronoUnit.MINUTES.between(time1, time2));
        return minutesDifference < 30;
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }
}
