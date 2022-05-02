package com.cbarkinozer.onlinebankingrestapi.app.cdt.entity;

import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="CDT_CREDIT")
public class CdtCredit extends BaseEntity {

    @Id
    @SequenceGenerator(name="CdtCredit",sequenceName = "CDT_CREDIT_ID_SEQ")
    @GeneratedValue(generator = "CdtCredit")
    private Long id;

    @Column(name="ID_CUS_CUSTOMER",nullable = false)
    private Long customerId;

    @Column(name="ID_CUS_CUSTOMER",nullable = false)
    private Long installmentCount;

    @Column(name="CREDIT_AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal creditAmount;

    @Column(name="MONTHLY_INSTALLMENT_AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal monthlyInstallmentAmount;

}
