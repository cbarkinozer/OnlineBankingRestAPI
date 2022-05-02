package com.cbarkinozer.onlinebankingrestapi.app.acc.entity;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccMoneyTransferType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="ACC_MONEY_TRANSFER")
public class AccMoneyTransfer extends BaseEntity {

    @Id
    @SequenceGenerator(name="AccMoneyTransfer",sequenceName = "ACC_MONEY_TRANSFER_ID_SEQ")
    @GeneratedValue(generator = "AccMoneyTransfer")
    private Long id;

    @Column(name="ID_ACC_ACCOUNT_FROM",nullable = false)
    private Long accountIdFrom;

    @Column(name="ID_ACC_ACCOUNT_TO",nullable = false)
    private Long accountIdTo;

    @Column(name ="AMOUNT" ,precision = 19,scale = 2,nullable = false)
    private BigDecimal amount;

    @Column(name = "transferDate",nullable = false)
    private LocalDate transferDate;

    @Column(name="DESCRIPTION",length=30,nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="TRANSFER_TYPE",length=30,nullable = false)
    private AccMoneyTransferType transferType;
}
