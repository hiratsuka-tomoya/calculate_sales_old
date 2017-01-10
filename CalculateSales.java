package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CalculateSales {

	public static void main(String[] args){

		//コマンドライン引数からフォルダパスを取得
		String folderPath = args[0];

		//引数のパスのディレクトリが存在するか確認
		File dir = new File(folderPath);
		if (dir.exists() == false) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}

		//マップ・コレクション生成
		HashMap<String,? extends DifinitionData> branchMap = new HashMap<String,Branch>();
		HashMap<String,? extends DifinitionData> productMap = new HashMap<String,Product>();
		ArrayList<Sales> salesList = new ArrayList<Sales>();

		//リーダーインスタンス生成
		DifinitionFileReader branchFileReader = new BranchFileReader(folderPath);
		DifinitionFileReader productFileReader = new ProductFileReader(folderPath);
		SalesFileReader salesFileReader = new SalesFileReader(folderPath);

		//ファイルを読み込む
		if((branchMap = branchFileReader.getDifinitionDataMap()) == null){
			return;
		}
		if((productMap = productFileReader.getDifinitionDataMap()) == null){
			return;
		}
		if((salesList = salesFileReader.getSalesList()) == null){
			return;
		}

		//集計
		for(Sales sales: salesList) {
			if(branchMap.containsKey(sales.getBranchCode())){
				if(branchMap.get(sales.getBranchCode()).addAmount(sales.getAmount()) == false){
					//売上の合計額が10桁を超えていれば終了
					return;
				}
			}else {
				//該当の支店が存在しなければ終了
				System.out.println("<" + sales.fileName + ">" + "の支店コードが不正です");
				return;
			}
			if(productMap.containsKey(sales.getProductCode())){
				if(productMap.get(sales.getProductCode()).addAmount(sales.getAmount()) == false){
					//売上の合計額が10桁を超えていれば終了
					return;
				}
			}else {
				//該当の商品が存在しなければ終了
				System.out.println("<" + sales.fileName + ">" + "の商品コードが不正です");
				return;
			}
		}

		//支店別集計ファイル出力
		if (outputCalculateFile(branchMap, folderPath, Constants.FILE_NAME_BRANCH_OUTPUT) == false) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}

		//商品別集計ファイル出力
		if (outputCalculateFile(productMap, folderPath, Constants.FILE_NAME_PRODUCT_OUTPUT) == false) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
	}

	//集計ファイル出力
	//staticにしないとstatic mainから呼び出せないが、これをstaticにするのは不適切？
	public static Boolean outputCalculateFile(HashMap<String,? extends DifinitionData> map, String folderPath, String fileName) {

		ArrayList<DifinitionData> sortList = new ArrayList<DifinitionData>(map.values());	//ソート用リスト
		String filePathOutput = folderPath + File.separator + fileName;
		File fileOutput = new File(filePathOutput);
		FileWriter fw = null;
		BufferedWriter bw = null;

		//売上で降順ソート
		Collections.sort(sortList, new DifinitionDataComparator());

		//出力ファイルが既に存在するなら削除
		if(fileOutput.exists()) {
			fileOutput.delete();
		}

		//出力ファイル作成
		try {
			fileOutput.createNewFile();
		} catch (IOException e) {
		    System.out.println("予期せぬエラーが発生しました");
		}

		try {
			fw = new FileWriter(fileOutput);
			bw = new BufferedWriter(fw);
			for(DifinitionData dd : sortList){
				//リストの内容を一行ずつファイルに書き込む
				bw.write(dd.getCode() + "," + dd.getName() + "," + dd.getAmount());
				bw.newLine();
			}
		}catch (IOException e) {
		    System.out.println("予期せぬエラーが発生しました");
		    return false;
		}finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return false;
			  }
		}
		return true;
	}
}
