package com.example.logistic.demo.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class Mapper {

    @Autowired
    protected ModelMapper mapper;

    public <E, D> E toEntity(D dto, Class<E> entityClass) {
        return Objects.isNull(dto) ? null : mapper.map(dto, entityClass);
    }

    public <E, D> D toDto(E entity, Class<D> dtoClass) {
        return Objects.isNull(entity) ? null : mapper.map(entity, dtoClass);
    }

    public <E, D> void updateEntityFromDto(D dto, E entity) {
        mapper.map(dto, entity);
    }

    public <E, D> List<D> toListDto(List<E> entities, Class<D> dtoClass) {
        return entities.stream().map(e -> this.toDto(e, dtoClass)).collect(Collectors.toList());
    }

    public <E, D> Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFieldsToEntityConverter(source, destination);
            return context.getDestination();
        };
    }

    public <E, D> Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFieldsToDtoConverter(source, destination);
            return context.getDestination();
        };
    }

    public <E, D> void mapSpecificFieldsToDtoConverter(E source, D destination) {
    }

    public <E, D> void mapSpecificFieldsToEntityConverter(D source, E destination) {
    }
}