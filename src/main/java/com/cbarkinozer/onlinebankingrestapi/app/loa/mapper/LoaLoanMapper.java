package com.cbarkinozer.onlinebankingrestapi.app.loa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoaLoanMapper {

    LoaLoanMapper INSTANCE = Mappers.getMapper(LoaLoanMapper.class);
}
