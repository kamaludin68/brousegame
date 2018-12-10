/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 
 *
 * @author Emerio-PC
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AbstractAuditingEntity {
    @CreatedBy
//    @Setter(onMethod = @__( @JsonIgnore))
    private String createdBy = "admin";
    @CreatedDate
    private LocalDateTime createdDate= LocalDateTime.now();
    @LastModifiedBy
    private String lastModifiedBy = "admin";
    @LastModifiedDate
    private LocalDateTime lastModifiedDate= LocalDateTime.now();
}

