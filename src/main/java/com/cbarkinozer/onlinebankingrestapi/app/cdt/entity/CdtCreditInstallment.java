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
@Table(name="CDT_CREDIT_INSTALLMENT")
public class CdtCreditInstallment extends BaseEntity {

    @Id
    @SequenceGenerator(name="CdtCreditInstallment",sequenceName = "CDT_CREDIT_INSTALLMENT_ID_SEQ")
    @GeneratedValue(generator = "CdtCreditInstallment")
    private Long id;

    @Column(name="ID_CDT_CREDIT", nullable = false)
    private Long creditId;

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
