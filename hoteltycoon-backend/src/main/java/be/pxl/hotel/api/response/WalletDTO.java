package be.pxl.hotel.api.response;

public class WalletDTO {
	private double amount;

	public WalletDTO(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
