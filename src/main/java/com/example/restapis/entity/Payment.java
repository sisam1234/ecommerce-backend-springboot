package com.example.restapis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String paymentMethod;
	private String pgstatus;
	private String pgResponseMessage;
	private String pgName;
	private String pgPaymentId;
	@OneToOne(mappedBy = "payment")
	private Order order;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPgstatus() {
		return pgstatus;
	}
	public void setPgstatus(String pgstatus) {
		this.pgstatus = pgstatus;
	}
	public String getPgResponseMessage() {
		return pgResponseMessage;
	}
	public void setPgResponseMessage(String pgResponseMessage) {
		this.pgResponseMessage = pgResponseMessage;
	}
	public String getPgName() {
		return pgName;
	}
	public void setPgName(String pgName) {
		this.pgName = pgName;
	}
	public String getPgPaymentId() {
		return pgPaymentId;
	}
	public void setPgPaymentId(String pgPaymentId) {
		this.pgPaymentId = pgPaymentId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Payment( String paymentMethod, String pgstatus, String pgResponseMessage, String pgName,
			String pgPaymentId) {
		super();
		
		this.paymentMethod = paymentMethod;
		this.pgstatus = pgstatus;
		this.pgResponseMessage = pgResponseMessage;
		this.pgName = pgName;
		this.pgPaymentId = pgPaymentId;
		
	}

}
