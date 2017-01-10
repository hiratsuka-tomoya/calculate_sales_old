package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Sales{

	String fileName;
	String branchCode;
	String productCode;
	long amount;

	Sales (String fileName) {
		this.fileName = fileName;
	}

	void setAmount(long amount){
		this.amount = amount;
	}

	void setBranchCode(String code) {
		this.branchCode = code;
	}

	void setProductCode(String code) {
		this.productCode = code;
	}

	void addAmount(long amount){
		this.amount += amount;
	}

	String getBranchCode(){
		return branchCode;
	}

	String getProductCode(){
		return productCode;
	}

	long getAmount() {
		return amount;
	}

}
