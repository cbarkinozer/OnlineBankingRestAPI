package com.cbarkinozer.onlinebankingrestapi.app.cdt.entity;

import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="CDT_CREDIT_PAYMENT")
public class CdtCreditPayment extends BaseEntity {

    @Id
    @SequenceGenerator(name="CdtCreditPayment",sequenceName = "CDT_CREDIT_PAYMENT_ID_SEQ")
    @GeneratedValue(generator = "CdtCreditPayment")
    private Long id;

    @Column(name="ID_CDT_CREDIT",nullable = false)
    private Long creditId;

    @Column(name="ID_CDT_CREDIT_INSTALLMENT",nullable = true)
    private Long installmentId;

    @Column(name="AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal amount;

    @Column(name="DATE",nullable = false)
    private LocalDate date;
}
