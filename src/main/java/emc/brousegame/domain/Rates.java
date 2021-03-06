/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author kamal
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Rates extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "code")
    private Currency ccyFrom;
    @ManyToOne
    @JoinColumn(referencedColumnName = "code")
    private Currency ccyTo;
    private Integer buy;
    private Integer sell;
    private Integer timeRate;
    private Integer minRate;
    private Integer maxRate;
//    @CreatedDate
//    @Setter(onMethod = @__( @JsonIgnore ))
//    private Date createdDate = new Date();
    
}
