package com.cbarkinozer.onlinebankingrestapi.app.crd.entity;

import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdCreditCardActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CRD_CREDIT_CARD_ACTIVITY")
public class CrdCreditCardActivity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CrdCreditCardActivity", sequenceName = "CRD_CREDIT_CARD_ACTIVTY_ID_SEQ")
    @GeneratedValue(generator = "CrdCreditCardActivity")
    private Long id;

    @Column(name = "ID_CRD_CREDIT_CARD", nullable = false)
    private Long crdCreditCardId;

    @Column(name = "AMOUNT", precision = 19, scale = 2,nullable = false)
    private BigDecimal amount;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String description;

    @Column(name = "CARD_ACTIVITY_TYPE", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private CrdCreditCardActivityType cardActivityType;
}
