package com.odeon.repository;

import com.odeon.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    List<Flight> findByDepartureCity(String city);
    
    List<Flight> findByDepartureCityAndArrivalCityAndDepartureTimeBetween(
        String departureCity, 
        String arrivalCity, 
        LocalDateTime startOfDay,
        LocalDateTime endOfDay
    );

    @Query("""
        SELECT f FROM Flight f 
        WHERE (f.departureCity = :city OR f.arrivalCity = :city) 
        AND (
            (f.departureTime BETWEEN :start AND :end) 
            OR (f.arrivalTime BETWEEN :start AND :end)
            OR (:start BETWEEN f.departureTime AND f.arrivalTime)
            OR (:end BETWEEN f.departureTime AND f.arrivalTime)
        )
        """)
    List<Flight> findConflictingFlights(
        String city, 
        LocalDateTime start, 
        LocalDateTime end
    );
} 
