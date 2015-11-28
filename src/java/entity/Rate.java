package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rate {
  @Id
  public String currency;
  @Id
  public long date;
  public Double rate;


  public Rate(String c, long d, Double r) {
    currency = c;
    date = d;
    rate = r;
  } 

  public Rate() {
  } 
 
          
}
