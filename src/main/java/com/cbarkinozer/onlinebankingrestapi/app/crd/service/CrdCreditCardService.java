package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.mapper.CrdCreditCardMapper;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CrdCreditCardService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;
    private final CrdCreditCardValidationService crdCreditCardValidationService;

    public List<CrdCreditCardDto> findAllCreditCards() {

        List<CrdCreditCard> crdCreditCardList = crdCreditCardEntityService.findAllActiveCreditCardList();

        List<CrdCreditCardDto> crdCreditCardResponseDtoList = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardDtoList(crdCreditCardList);

        return crdCreditCardResponseDtoList;
    }

    public CrdCreditCardDto findCreditCardById(Long id) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);

        CrdCreditCardDto result = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardDto(crdCreditCard);

        return result;
    }

    public CrdCreditCardDto saveCreditCard(CrdCreditCardSaveDto crdCreditCardSaveDto) {

        BigDecimal earning = crdCreditCardSaveDto.getEarning();
        String cutoffDayStr = crdCreditCardSaveDto.getCutoffDay();

        BigDecimal limit = earning.multiply(BigDecimal.valueOf(3));
        LocalDate cutoffDate = getCutoffDate(cutoffDayStr);

        LocalDate dueDate = cutoffDate.plusDays(10);

        CrdCreditCard crdCreditCard = createCreditCard(limit, cutoffDate, dueDate);

        CrdCreditCardDto crdCreditCardDto = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardDto(crdCreditCard);

        return crdCreditCardDto;
    }

    private LocalDate getCutoffDate(String cutoffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        Integer cutoffDay = getCutoffDay(cutoffDayStr);
        LocalDate cutoffDateLocal = LocalDate.of(currentYear, nextMonth, cutoffDay);
        return cutoffDateLocal;
    }

    private Integer getCutoffDay(String cutoffDayStr) {
        if (!StringUtils.hasText(cutoffDayStr)){
            cutoffDayStr = "1";
        }

        Integer cutoffDay = Integer.valueOf(cutoffDayStr);
        return cutoffDay;
    }

    private CrdCreditCard createCreditCard(BigDecimal limit, LocalDate cutoffDate, LocalDate dueDate) {

        Long cusCustomerId = crdCreditCardEntityService.getCurrentCustomerId();
        LocalDate expireDate = LocalDate.now().plusYears(3);
        Long cardNo = StringUtil.getRandomNumber(16);
        Long cvvNo = StringUtil.getRandomNumber(3);

        CrdCreditCard crdCreditCard = new CrdCreditCard();
        crdCreditCard.setCusCustomerId(cusCustomerId);
        crdCreditCard.setCutoffDate(cutoffDate);
        crdCreditCard.setDueDate(dueDate);
        crdCreditCard.setExpireDate(expireDate);
        crdCreditCard.setCardNo(cardNo);
        crdCreditCard.setCvvNo(cvvNo);
        crdCreditCard.setTotalLimit(limit);
        crdCreditCard.setAvailableCardLimit(limit);
        crdCreditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        crdCreditCard.setCurrentDebt(BigDecimal.ZERO);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    public void cancelCreditCard(Long cardId) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(cardId);

        crdCreditCard.setStatusType(GenStatusType.PASSIVE);
        crdCreditCard.setCancelDate(LocalDateTime.now());

        crdCreditCardEntityService.save(crdCreditCard);
    }
}
