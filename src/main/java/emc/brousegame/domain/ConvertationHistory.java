/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Emerio-PC
 */
@Entity
@Getter @Setter
@ToString @EqualsAndHashCode
@NoArgsConstructor
public class ConvertationHistory{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "generated by system when data create")
    private Long ticketNo;
    @ApiModelProperty(notes = "counter party base on chat",example = "RINO - Bank Rino")
    private String counterparty;
    @ApiModelProperty(notes = "currency ammount",example = "10")
    private Long amount;
    @ApiModelProperty(notes = "biller name/ username as biller",example = "Regi")
    private String maker;
    @ApiModelProperty(notes = "format date YYYY-MM-DDThh:mm:ss.SSSZ",example = "2018-12-26T09:06:19.225Z")
    private LocalDateTime startTime;
    @ApiModelProperty(notes = "format date YYYY-MM-DDThh:mm:ss.SSSZ",example = "2018-12-26T09:06:19.225Z")
    private LocalDateTime endTime;
    @JsonIgnore
    private Boolean transactional=Boolean.TRUE;

}
