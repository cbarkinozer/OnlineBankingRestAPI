package com.cbarkinozer.onlinebankingrestapi.app.loa.entity;

import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import com.cbarkinozer.onlinebankingrestapi.app.loa.enums.LoaLoanStatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="LOA_LOAN")
public class LoaLoan extends BaseEntity {

    @Id
    @SequenceGenerator(name="LoaLoan",sequenceName = "LOA_LOAN_ID_SEQ")
    @GeneratedValue(generator = "LoaLoan")
    private Long id;

    @Column(name="ID_CUS_CUSTOMER",nullable = false)
    private Long customerId;

    @Column(name="INSTALLMENT_COUNT",nullable = false)
    private Integer installmentCount;

    @Column(name="PRINCIPAL_LOAN_AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal principalLoanAmount;

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

    @Enumerated(EnumType.STRING)
    @Column(name ="LOAN_STATUS_TYPE", length=30,nullable = false)
    private LoaLoanStatusType loanStatusType;

}
