/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@NoArgsConstructor
@Setter @Getter
@ToString @EqualsAndHashCode
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "id generate by system when create")
    private Long id;
    @ApiModelProperty(notes = "ticketNo refer to convertation history")
    private Long ticketNo;
    @ApiModelProperty(notes = "format date YYYY-MM-DD",example = "2018-12-20")
    private LocalDate dealDate;
    @ApiModelProperty(notes = "base on convertation history",example = "RINO - Bank Rino")
    private String counterparty;
    @ApiModelProperty(notes = "base on convertation history",example = "Regi")
    private String maker;
    @ApiModelProperty(notes = "manual input",example = "USD")
    private String currency;
    @ApiModelProperty(notes = "manual input BUY / SELL",example = "BUY")
    private String transactionType;
    @ApiModelProperty(notes = "format date YYYY-MM-DD",example = "2018-12-20")
    private LocalDate valueDate;
    @ApiModelProperty(notes = "manual input",example = "12000")
    private Integer rate;
    @ApiModelProperty(notes = "base on convertation history",example = "10")
    private Long amount;
    @ApiModelProperty(notes = "convert to IDR base on rate",example = "120000")
    private Long countraAmount;
    

}
