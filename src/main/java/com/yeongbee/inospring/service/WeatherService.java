package com.yeongbee.inospring.service;

import com.yeongbee.inospring.domain.WeatherData;
import com.yeongbee.inospring.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final MongoTemplate template;               //MongoDBTemplate 의존성 주입

    @Transactional
    public void save(WeatherData weatherData) {
        long count = weatherRepository.count();

        if (count != 0) {
            weatherData.setSequence(++count);
        } else {
            weatherData.setSequence(1);
        }

        weatherData.setDate(LocalDate.now());           // 현재 날짜
        weatherData.setTime(LocalTime.now());           // 현재 시간
        weatherData.setHumidity(Math.round(weatherData.getHumidity() * 100) / 100.0);           //소수점 3번째에서 반올림
        weatherData.setTemperature(Math.round(weatherData.getTemperature() * 100) / 100.0);     //소수점 3번째에서 반올림

        weatherRepository.save(weatherData);
    }

    public WeatherData findLatestData() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "date", "time")).limit(1);       // 날짜, 시간을 내림차순으로 정렬, 하나 추출
        return template.findOne(query, WeatherData.class);
    }

    public List<WeatherData> findAll() {
        return weatherRepository.findAll();
    }

    public List<WeatherData> findWeatherDataBetweenDate(LocalDate start, LocalDate end) {
        return weatherRepository.findWeatherDataByDateBetween(start, end);
    }





}
