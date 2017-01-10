package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class BranchFileReader extends DifinitionFileReader {

	BranchFileReader(String folderPath) {
		super(folderPath, Constants.FILE_NAME_BRANCH, Constants.FILE_NAME_BRANCH_JAP);
	}

	public DifinitionData returnNewDataObject() {
		return new Branch();
	}
}
