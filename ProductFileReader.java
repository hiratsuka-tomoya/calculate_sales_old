package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class ProductFileReader extends DifinitionFileReader {

	ProductFileReader(String folderPath) {
		super(folderPath, Constants.FILE_NAME_PRODUCT, Constants.FILE_NAME_PRODUCT_JAP);
	}

	public DifinitionData returnNewDataObject() {
		return new Product();
	}
}
