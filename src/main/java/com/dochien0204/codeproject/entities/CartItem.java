package com.dochien0204.codeproject.entities;

import com.dochien0204.codeproject.entities.base.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_item_id")
  private Integer cartItemId;

  private Integer quantity;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  @JsonIgnore
  private Cart cart;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private Book book;

  public CartItem(Book book, Integer quantity) {
    this.book = book;
    this.quantity = quantity;
  }

}
