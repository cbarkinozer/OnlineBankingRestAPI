package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdCreditCardActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.crd.mapper.CrdCreditCardMapper;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CrdCreditCardService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;
    private final CrdCreditCardValidationService crdCreditCardValidationService;
    private final CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

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


    public CrdCreditCardDetailsDto getCardDetails(Long id) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);
        LocalDateTime termEndDate = crdCreditCard.getCutoffDate().atStartOfDay();
        Long crdCreditCardId = crdCreditCard.getId();

        LocalDateTime termStartDate = termEndDate.minusMonths(1);

        CrdCreditCardDetailsDto crdCreditCardDetailsDto = crdCreditCardEntityService.getCreditCardDetails(crdCreditCardId);

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId, termStartDate, termEndDate);

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardMapper.INSTANCE
                .convertToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        crdCreditCardDetailsDto.setCrdCreditCardActivityDtoList(crdCreditCardActivityDtoList);

        return crdCreditCardDetailsDto;
    }

    public CrdCreditCardDto saveCreditCard(CrdCreditCardSaveDto crdCreditCardSaveDto) {

        BigDecimal earning = crdCreditCardSaveDto.getEarning();
        Integer cutOffDay = crdCreditCardSaveDto.getCutOffDay();

        crdCreditCardValidationService.controlAreFieldsNull(earning,cutOffDay);
        crdCreditCardValidationService.controlIsEarningNotNegative(earning);

        BigDecimal limit = earning.multiply(BigDecimal.valueOf(3));
        LocalDate cutoffDate = getCutOffDate(cutOffDay);

        CrdCreditCard crdCreditCard = createCreditCard(limit, cutoffDate);

        CrdCreditCardDto crdCreditCardDto = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardDto(crdCreditCard);

        return crdCreditCardDto;
    }

    private LocalDate getCutOffDate(Integer cutOffDay) {

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        crdCreditCardValidationService.isCutOffDayValid(cutOffDay);
        LocalDate cutoffDate = LocalDate.of(currentYear, nextMonth, cutOffDay);
        return cutoffDate;
    }

    private CrdCreditCard createCreditCard(BigDecimal limit, LocalDate cutoffDate) {

        Long cusCustomerId = crdCreditCardEntityService.getCurrentCustomerId();
        LocalDate expireDate = LocalDate.now().plusYears(3);
        LocalDate dueDate = cutoffDate.plusDays(10);
        Long cardNo = StringUtil.getRandomNumber(16);
        Long cvvNo = StringUtil.getRandomNumber(3);

        CrdCreditCard crdCreditCard = new CrdCreditCard();
        crdCreditCard.setCusCustomerId(cusCustomerId);
        crdCreditCard.setDueDate(dueDate);
        crdCreditCard.setExpireDate(expireDate);
        crdCreditCard.setCardNo(cardNo);
        crdCreditCard.setCvvNo(cvvNo);
        crdCreditCard.setTotalLimit(limit);
        crdCreditCard.setAvailableCardLimit(limit);
        crdCreditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        crdCreditCard.setCurrentDebt(BigDecimal.ZERO);
        crdCreditCard.setCutoffDate(cutoffDate);
        crdCreditCard.setStatusType(GenStatusType.ACTIVE);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    public void cancelCreditCard(Long cardId) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(cardId);

        crdCreditCard.setStatusType(GenStatusType.PASSIVE);
        crdCreditCard.setCancelDate(LocalDateTime.now());

        crdCreditCardEntityService.save(crdCreditCard);
    }

    public List<CrdCreditCardActivityDto> findCreditCardActivityBetweenDates(Long creditCardId,
                                                                             LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime   = endDate.atStartOfDay();

        List<CrdCreditCardActivity> crdCreditCardActivityList;

        crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findCreditCardActivityBetweenDates(creditCardId,startDateTime,endDateTime);


        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        return crdCreditCardActivityDtoList;
    }

    public CrdCreditCardActivityDto spendMoney(CrdCreditCardSpendDto crdCreditCardSpendDto) {

        crdCreditCardValidationService.controlAreFieldsNull(crdCreditCardSpendDto);

        BigDecimal amount = crdCreditCardSpendDto.getAmount();
        String description = crdCreditCardSpendDto.getDescription();

        CrdCreditCard crdCreditCard = getCreditCard(crdCreditCardSpendDto);

        crdCreditCardValidationService.controlIsCardCancelled(crdCreditCard.getStatusType());

        crdCreditCardValidationService.validateCreditCard(crdCreditCard);

        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().add(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().subtract(amount);

        crdCreditCardValidationService.validateCardLimit(currentAvailableLimit);

        crdCreditCard = updateCreditCardForSpend(crdCreditCard, currentDebt, currentAvailableLimit);

        CrdCreditCardActivity crdCreditCardActivity = createCreditCardActivityForSpend(amount, description, crdCreditCard);

        CrdCreditCardActivityDto crdCreditCardActivityDto = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardActivityDto(crdCreditCardActivity);

        return crdCreditCardActivityDto;
    }

    private CrdCreditCard getCreditCard(CrdCreditCardSpendDto crdCreditCardSpendDto) {

        Long cardNo = crdCreditCardSpendDto.getCardNo();
        Long cvvNo = crdCreditCardSpendDto.getCvvNo();

        LocalDate expireDate = crdCreditCardSpendDto.getExpireDate();

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.findByCardNoAndCvvNoAndExpireDate(cardNo, cvvNo, expireDate);

        return crdCreditCard;
    }

    private CrdCreditCard updateCreditCardForSpend(CrdCreditCard crdCreditCard, BigDecimal currentDebt, BigDecimal currentAvailableLimit) {

        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);

        return crdCreditCard;
    }

    private CrdCreditCardActivity createCreditCardActivityForSpend(BigDecimal amount, String description, CrdCreditCard crdCreditCard) {

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription(description);
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.SPEND);
        crdCreditCardActivity.setTransactionDate(LocalDateTime.now());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    public CrdCreditCardActivityDto refundMoney(Long activityId) {

        CrdCreditCardActivity oldCrdCreditCardActivity = crdCreditCardActivityEntityService.getByIdWithControl(activityId);

        Long creditCardId = oldCrdCreditCardActivity.getCrdCreditCardId();
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(creditCardId);
        crdCreditCardValidationService.controlIsCardCancelled(crdCreditCard.getStatusType());

        BigDecimal amount = oldCrdCreditCardActivity.getAmount();

        crdCreditCard = updateCreditCardForRefund(oldCrdCreditCardActivity, amount);

        CrdCreditCardActivity crdCreditCardActivity = createCreditCardActivityForRefund(oldCrdCreditCardActivity, amount, crdCreditCard);

        CrdCreditCardActivityDto result = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardActivityDto(crdCreditCardActivity);

        return result;
    }

    private CrdCreditCardActivity createCreditCardActivityForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount, CrdCreditCard crdCreditCard) {

        String description = "REFUND : " + oldCrdCreditCardActivity.getDescription();

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription(description);
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.REFUND);
        crdCreditCardActivity.setTransactionDate(LocalDateTime.now());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    private CrdCreditCard updateCreditCardForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(oldCrdCreditCardActivity.getCrdCreditCardId());

        crdCreditCard = addLimitToCard(crdCreditCard, amount);
        return crdCreditCard;
    }

    private CrdCreditCard addLimitToCard(CrdCreditCard crdCreditCard, BigDecimal amount) {

        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().add(amount);

        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);
        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    public CrdCreditCardActivityDto receivePayment(CrdCreditCardPaymentDto crdCreditCardPaymentDto) {

        Long creditCardId = crdCreditCardPaymentDto.getCrdCreditCardId();
        BigDecimal amount = crdCreditCardPaymentDto.getAmount();

        crdCreditCardValidationService.controlAreFieldsNull(creditCardId,amount);

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(creditCardId);

        crdCreditCardValidationService.controlIsCardCancelled(crdCreditCard.getStatusType());

        addLimitToCard(crdCreditCard, amount);

        CrdCreditCardActivity crdCreditCardActivity = createCreditCardActivityForPayment(creditCardId, amount);

        CrdCreditCardActivityDto crdCreditCardActivityDto = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardActivityDto(crdCreditCardActivity);

        return crdCreditCardActivityDto;
    }

    private CrdCreditCardActivity createCreditCardActivityForPayment(Long crdCreditCardId, BigDecimal amount) {

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCardId);
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription("PAYMENT : ");
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.PAYMENT);
        crdCreditCardActivity.setTransactionDate(LocalDateTime.now());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

}
