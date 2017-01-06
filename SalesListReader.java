package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SalesListReader {

	String folderPath;
	long firstFileNumber;	//1つ目のファイルの番号
	int fileCnt;	//売上ファイル数

	SalesListReader(String folderPath){
		this.folderPath = folderPath;
		fileCnt = 0;
	}

	//売上ファイルを読み込み、ArrayList<Sales>を返す
	public ArrayList<Sales> getSalesList(){

		ArrayList<Sales> salesList = new ArrayList<Sales>();

		try{
			//ディレクトリ内のファイル一覧を取得
			File dir = new File(folderPath);
			String[] fileNames = dir.list();

			//ファイル名が売上ファイルの形式に一致するものを検索し、
			//内容をsalesListに登録
			for(String fileName: fileNames){
				if(fileName.matches("\\d{8}.rcd")){
					if(fileCnt == 0){
						//1ファイル目なら番号を記憶
						firstFileNumber = Integer.parseInt(fileName.substring(1,8));
						fileCnt++;
					}else {
						//2ファイル目以降ならチェック
						if(Integer.parseInt(fileName.substring(1,8)) != firstFileNumber + fileCnt){
							//連番かどうか
							System.out.println("売上ファイル名が連番になっていません");
							return null;
						}else if(fileCnt >= 3){
							//3ファイル以内かどうか
							System.out.println("予期せぬエラーが発生しました");
							return null;
						}
						fileCnt++;
					}

					String filePath = folderPath + "\\" + fileName;
					FileReader fr = new FileReader(filePath);
					BufferedReader br = new BufferedReader(fr);
					String strLine;
					//ファイル末尾まで、最大三回一行ずつ読み込む
					//4行以上あればエラー出力
					int cnt = 0;
					Sales sales = new Sales();
					while((strLine = br.readLine()) != null) {
						if (cnt >=3){
							System.out.println("<" + fileName + ">のフォーマットが不正です");
							br.close();
							return null;
						}

						switch (cnt){
						case 0:
							sales.setBranchCode(strLine);
							break;
						case 1:
							sales.setProductCode(strLine);
							break;
						case 2:
							sales.setAmount(Integer.parseInt(strLine));
							break;
						}
						cnt++;
					}
					salesList.add(sales);
					br.close();
				}
			}
		}catch(FileNotFoundException e){
			  System.out.println("予期せぬエラーが発生しました");
			  System.out.println(e);
		}catch(IOException e){
			  System.out.println("予期せぬエラーが発生しました");
			  System.out.println(e);
		}

		return salesList;

	}
}
