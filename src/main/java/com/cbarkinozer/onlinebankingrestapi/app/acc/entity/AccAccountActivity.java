package com.cbarkinozer.onlinebankingrestapi.app.acc.entity;

import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="ACC_ACCOUNT_ACTIVITY")
public class AccAccountActivity extends BaseEntity {

    @Id
    @SequenceGenerator(name="AccAccountActivity",sequenceName = "ACC_ACCOUNT_ACTIVITY_ID_SEQ")
    @GeneratedValue(generator = "AccAccountActivity")
    private Long id;

    @Column(name="ID_ACC_ACCOUNT",nullable = false)
    private Long accountId;

    @Column(name="AMOUNT",precision=19,scale=2,nullable = false)
    private BigDecimal amount;

    @Column(name="TRANSACTION_DATE",nullable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name="ACCOUNT_ACTIVITY_TYPE",length = 30,nullable = false)
    private AccAccountActivityType accountActivityType;

    @Column(name="CURRENT_BALANCE",precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal currentBalance;
}
