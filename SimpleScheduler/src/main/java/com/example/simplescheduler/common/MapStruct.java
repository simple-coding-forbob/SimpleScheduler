package com.example.simplescheduler.common;


import com.example.simplescheduler.dto.EvDto;
import com.example.simplescheduler.entity.Ev;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE  // null 제외 기능(update 시 사용)
)
public interface MapStruct {

    // TODO: 1) Dept <-> DeptDto
    EvDto toDto(Ev ev);
    Ev toEntity(EvDto evDto);
    // TODO: 수정 시 사용: dirty checking 기능(save() 없이 수정 가능)
    void updateFromDto(EvDto evDto, @MappingTarget Ev ev);
}
