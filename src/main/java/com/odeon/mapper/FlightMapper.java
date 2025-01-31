package com.odeon.mapper;

import com.odeon.dto.FlightDTO;
import com.odeon.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    
    public FlightDTO toDTO(Flight flight) {
        return new FlightDTO(
            flight.getId(),
            flight.getDepartureCity(),
            flight.getArrivalCity(),
            flight.getDepartureTime(),
            flight.getArrivalTime(),
            flight.getFlightNumber(),
            flight.getCapacity(),
            flight.getPrice()
        );
    }

    public Flight toEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setDepartureCity(dto.getDepartureCity());
        flight.setArrivalCity(dto.getArrivalCity());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setCapacity(dto.getCapacity());
        flight.setPrice(dto.getPrice());
        return flight;
    }
} 