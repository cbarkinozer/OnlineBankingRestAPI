package com.cbarkinozer.onlinebankingrestapi.app.log.entity;

import com.cbarkinozer.onlinebankingrestapi.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "LOG_DETAIL")
public class LogDetail extends BaseEntity {

    @GeneratedValue(generator = "LogDetail")
    @SequenceGenerator(name = "LogDetail" , sequenceName = "LOG_DETAIL_ID_SEQ")
    @Id
    private Long id;
    private String message;
    private String details;
    private LocalDateTime dateTime;

}
