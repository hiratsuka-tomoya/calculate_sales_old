package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class DifinitionData {
//定義ファイルデータ

	String code;
	String name;
	long amount;

	boolean setCode(String code) {
		this.code = code;
		return true;
	}

	boolean setName(String name){
		this.name = name;
		return true;
	}

	boolean addAmount(long amount){
		this.amount += amount;
		if(this.amount >= 1000000000) {
			System.out.println("合計金額が10桁を超えました");
			return false;
		}
		return true;
	}

	String getCode(){
		return code;
	}

	String getName(){
		return name;
	}

	long getAmount() {
		return amount;
	}
}
