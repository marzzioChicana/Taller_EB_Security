package com.upc.taller_eb_sec.service.impl;

import com.upc.taller_eb_sec.dto.ScoreDto;
import com.upc.taller_eb_sec.exception.ResourceNotFoundException;
import com.upc.taller_eb_sec.model.Score;
import com.upc.taller_eb_sec.repository.ScoreRepository;
import com.upc.taller_eb_sec.service.ScoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, ModelMapper modelMapper) {
        this.scoreRepository = scoreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ScoreDto createScore(ScoreDto scoreDto){
        Score score = DtoToEntity(scoreDto);
        return EntityToDto(scoreRepository.save(score));
    }

    @Override
    public List<ScoreDto> getScoresByDriverId(Long driverId){
        List<Score> scores = scoreRepository.findByDriverId(driverId);
        return scores.stream().map(this::EntityToDto).toList();
    }

    private ScoreDto EntityToDto(Score score) { return modelMapper.map(score, ScoreDto.class); }

    private Score DtoToEntity(ScoreDto scoreDto) { return modelMapper.map(scoreDto, Score.class); }

    //Obtener el maximo puntaje de un conductor
    @Override
    public Float getMaxScoreByDriverId(Long driverId) {
        return (float) scoreRepository.findByDriverId(driverId)
                .stream()
                .mapToDouble(score -> score.getValue())
                .max()
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron puntajes para el conductor"));
    }

    //Obtener el promedio de puntaje de un conductor
    @Override
    public Float getAverageScoreByDriverId(Long driverId) {
        return (float) scoreRepository.findByDriverId(driverId)
                .stream()
                .mapToDouble(score -> score.getValue())
                .average()
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron puntajes para el conductor"));
    }

}