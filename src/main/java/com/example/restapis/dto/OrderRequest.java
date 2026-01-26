package com.example.restapis.dto;

public class OrderRequest {
	private Long id;
	private Long addressId;
		private String paymentMethod;
		private String pgstatus;
		private String pgResponseMessage;
		private String pgName;
		private String pgPaymentId;
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
		public Long getAddressId() {
			return addressId;
		}
		public void setAddressId(Long addressId) {
			this.addressId = addressId;
		}
}

