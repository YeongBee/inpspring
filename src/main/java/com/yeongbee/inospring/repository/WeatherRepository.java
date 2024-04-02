package com.yeongbee.inospring.repository;

import com.yeongbee.inospring.domain.WeatherData;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherData, String> {
    List<WeatherData> findWeatherDataByDateBetween(LocalDate start, LocalDate end);
}
