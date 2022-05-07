package com.cbarkinozer.onlinebankingrestapi.app.loa.entity;

import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="LOA_LOAN_PAYMENT")
public class LoaLoanPayment extends BaseEntity {

    @Id
    @SequenceGenerator(name="LoaLoanPayment",sequenceName = "LOA_LOAN_PAYMENT_ID_SEQ")
    @GeneratedValue(generator = "LoaLoanPayment")
    private Long id;

    @Column(name="ID_LOA_LOAN",nullable = false)
    private Long loanId;

    @Column(name="PAYMENT_AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal paymentAmount;

    @Column(name="PAYMENT_DATE",nullable = false)
    private LocalDate PaymentDate;
}
