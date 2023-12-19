package be.pxl.hotel.api.request;

import be.pxl.hotel.domain.TransactionType;

public class CreateTransactionRequest {
	private double amount;
	private TransactionType transactionType;
	private String description;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
