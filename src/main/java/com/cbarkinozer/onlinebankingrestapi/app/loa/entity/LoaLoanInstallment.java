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
@Table(name="LOA_LOAN_INSTALLMENT")
public class LoaLoanInstallment extends BaseEntity {

    @Id
    @SequenceGenerator(name="LoaLoanInstallment",sequenceName = "LOA_LOAN_INSTALLMENT_ID_SEQ")
    @GeneratedValue(generator = "LoaLoanInstallment")
    private Long id;

    @Column(name="ID_LOA_LOAN", nullable = false)
    private Long loanId;

    @Column(name="ORDER_NO",nullable = false)
    private Long orderNo;

    @Column(name="MONTHLY_INSTALLMENT_AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal monthlyInstallmentAmount;

    @Column(name="INTEREST_TO_BE_PAID", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal interestToBePaid;

    @Column(name="PRINCIPAL_TO_BE_PAID", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal principalToBePaid;

    @Column(name="REMAINING_PRINCIPAL", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal remainingPrincipal;

    @Column(name="DUE_DATE",nullable = false)
    private LocalDate dueDate;

}
