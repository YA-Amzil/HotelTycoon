package be.pxl.hotel.api.response;

import be.pxl.hotel.domain.Transaction;

import java.util.List;

public class TransactionsOverviewDTO {
	private List<Transaction> payments;
	private List<Transaction> receipts;
	private double totalPayments;
	private double averagePayments;
	private double totalReceipts;
	private double averageReceipts;

	public List<Transaction> getPayments() {
		return payments;
	}

	public void setPayments(List<Transaction> payments) {
		this.payments = payments;
	}

	public List<Transaction> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<Transaction> receipts) {
		this.receipts = receipts;
	}

	public double getTotalPayments() {
		return totalPayments;
	}

	public void setTotalPayments(double totalPayments) {
		this.totalPayments = totalPayments;
	}

	public double getAveragePayments() {
		return averagePayments;
	}

	public void setAveragePayments(double averagePayments) {
		this.averagePayments = averagePayments;
	}

	public double getTotalReceipts() {
		return totalReceipts;
	}

	public void setTotalReceipts(double totalReceipts) {
		this.totalReceipts = totalReceipts;
	}

	public double getAverageReceipts() {
		return averageReceipts;
	}

	public void setAverageReceipts(double averageReceipts) {
		this.averageReceipts = averageReceipts;
	}
}
