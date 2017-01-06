package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {

	public static void main(String[] args){

		//コマンドライン引数からフォルダパスを取得
		String folderPath = args[0];

		//マップ・コレクション生成
		HashMap<String,DifinitionData> branchList = new HashMap<String,DifinitionData>();
		HashMap<String,DifinitionData> productList = new HashMap<String,DifinitionData>();
		ArrayList<Sales> salesList = new ArrayList<Sales>();

		//リーダーインスタンス生成
		ListReader branchListReader = new ListReader(folderPath, Constants.FILE_NAME_BRANCH);
		ListReader productListReader = new ListReader(folderPath, Constants.FILE_NAME_PRODUCT);
		SalesListReader salesListReader = new SalesListReader(folderPath);

		//ファイルを読み込む
		if((branchList = branchListReader.getDifinitionDataList()) == null){
			return;
		}
		if((productList = productListReader.getDifinitionDataList()) == null){
			return;
		}
		if((salesList = salesListReader.getSalesList()) == null){
			return;
		}

		//集計
		for(Sales sales: salesList) {
			if(branchList.containsKey(sales.getBranchCode())){
				if(branchList.get(sales.getBranchCode()).addAmount(sales.getAmount()) == false){
					//売上の合計額が10桁を超えていれば終了
					return;
				}
			}else {
				//該当の支店が存在しなければ終了
				System.out.println("<" + Constants.FILE_NAME_BRANCH + ">" + "の支店コードが不正です");
				return;
			}
			if(productList.containsKey(sales.getProductCode())){
				if(productList.get(sales.getProductCode()).addAmount(sales.getAmount()) == false){
					//売上の合計額が10桁を超えていれば終了
					return;
				}
			}else {
				//該当の商品が存在しなければ終了
				System.out.println("<" + Constants.FILE_NAME_PRODUCT + ">" + "の商品コードが不正です");
				return;
			}
		}

		//ソート用リスト　要素にインスタンスを持つHashMapでの簡単なソートのやり方がわからなかった！！！
		ArrayList<DifinitionData> branchList_sort = new ArrayList<DifinitionData>(branchList.values());
		ArrayList<DifinitionData> productList_sort = new ArrayList<DifinitionData>(productList.values());

		Collections.sort(branchList_sort, new DifinitionDataComparator());
		Collections.sort(productList_sort, new DifinitionDataComparator());

		//ファイル出力
		try {
			String filePathBranch_output = folderPath + File.separator + Constants.FILE_NAME_BRANCH_OUTPUT;
			String filePathProduct_output = folderPath + File.separator + Constants.FILE_NAME_PRODUCT_OUTPUT;
			File newFileBranch_output = new File(filePathBranch_output);
			File newFileProduct_output = new File(filePathProduct_output);
			//既存なら削除
			if(newFileBranch_output.exists()) {
				newFileBranch_output.delete();
			}
			if(newFileProduct_output.exists()) {
				newFileProduct_output.delete();
			}
			//ファイル作成
			newFileBranch_output.createNewFile();
			newFileProduct_output.createNewFile();

			//支店別集計ファイルに書き込み
			FileWriter fileWriter_branch = new FileWriter(newFileBranch_output);
			BufferedWriter bw_branch = new BufferedWriter(fileWriter_branch);
			for(DifinitionData br : branchList_sort){
				bw_branch.write(br.getCode() + "," + br.getName() + "," + br.getAmount());
				bw_branch.newLine();
			}
			bw_branch.close();

			//商品別集計ファイルに書き込み
			FileWriter fileWriter_product = new FileWriter(newFileProduct_output);
			BufferedWriter bw_product = new BufferedWriter(fileWriter_product);
			for(DifinitionData pr : productList_sort){
				bw_product.write(pr.getCode() + "," + pr.getName() + "," + pr.getAmount());
				bw_product.newLine();
			}
			bw_product.close();

		}catch(IOException e){
		    System.out.println("予期せぬエラーが発生しました");
		    System.out.println(e);
		}

	}
}
