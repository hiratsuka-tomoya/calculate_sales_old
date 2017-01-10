package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Branch extends DifinitionData {

	boolean setCode(String code) {
		if (code.matches("\\d{3}")) {
			this.code = code;
			return true;
		} else {
			System.out.println("支店定義ファイルのフォーマットが不正です");
			return false;
		}
	}
}
