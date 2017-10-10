package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Color;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ESHOP_PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
		
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    
    
    private Color color;
    
    @Temporal(TemporalType.DATE)
    private Date addedDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(long l) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
                if (obj == null) {
            return false;
        }
        
        if (!(obj instanceof Product)) {
            return false;
        }
        
        Product p = (Product) obj;
        
        if (name != null) {
            if (!name.equals(p.getName())) {
                return false;
            }
        } else {
            if (p.getName() != null) {
                return false;
            }
        }
        
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        final int primeNumber = 17;
        int result = 1;
        
        return result * primeNumber * ((name == null) ? 0 : name.hashCode());
    }
    
    
    
}
