package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Branch extends DifinitionData {

	boolean setCode(String code) {
		if (code.matches("\\d{3}")) {
			System.out.println("check");
			this.code = code;
			return true;
		} else {
			System.out.println("支店定義ファイルのフォーマットが不正です");
			return false;
		}
	}

	boolean setName(String name) {
		if (name.matches("[^,]*")) {
			this.name = name;
			return true;
		} else {
			System.out.println("支店定義ファイルのフォーマットが不正です");
			return false;
		}
	}
}
