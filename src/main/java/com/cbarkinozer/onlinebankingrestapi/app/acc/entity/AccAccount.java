package com.cbarkinozer.onlinebankingrestapi.app.acc.entity;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccCurrencyType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="ACC_ACCOUNT")
public class AccAccount extends BaseEntity {

    @Id
    @SequenceGenerator(name="AccAccount",sequenceName = "ACC_ACCOUNT_ID_SEQ")
    @GeneratedValue(generator = "AccAccount")
    private Long id;

    @Column(name="ID_CUS_CUSTOMER",nullable = false)
    private Long customerId;

    @Column(name="IBAN_NO",length = 40,unique = true,nullable = false)
    private String ibanNo;

    @Column(name="CURRENT_BALANCE", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name="CURRENCY_TYPE",length = 30,nullable = false)
    private AccCurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name="ACCOUNT_TYPE",length=30,nullable = false)
    private AccAccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS_TYPE",length=30,nullable = false)
    private GenStatusType statusType;

    @Column(name="CANCEL_DATE")
    private LocalDate cancelDate;
}
