package com.cbarkinozer.onlinebankingrestapi.app.loa.mapper;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaApplyLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoan;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoaLoanMapper {

    LoaLoanMapper INSTANCE = Mappers.getMapper(LoaLoanMapper.class);

    LoaLoan convertToLoaLoan(LoaApplyLoanDto loaLoanApplyLoanDto);

    LoaLoanDto convertToLoaLoanDto(LoaLoan loaLoan);

}
