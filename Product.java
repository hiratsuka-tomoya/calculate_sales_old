package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Product extends DifinitionData {

	boolean setCode(String code) {
		if (code.matches("\\w{8}")) {
			this.code = code;
			return true;
		} else {
			System.out.println("商品定義ファイルのフォーマットが不正です");
			return false;
		}
	}
}
