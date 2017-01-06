package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ListReader {

	String filePath;
	String fileName;

	ListReader(String folderPath, String fileName){
		//ファイルパスを取得
		this.fileName = fileName;
		filePath = folderPath + File.separator + fileName;
	}

	//定義ファイルを読み込み、HashMap<String,DifinitionData>を返す
	//エラーが発生したらnullを返す
	public HashMap<String,DifinitionData> getDifinitionDataList(){

		HashMap<String,DifinitionData> difDataList = new HashMap<String,DifinitionData>();

		try{
			File file = new File(filePath);
			//ファイルの存在を確認
			if(file.exists() == false){
				if(fileName == Constants.FILE_NAME_BRANCH){
					System.out.println("支店定義ファイルが存在しません");
				}else if(fileName == Constants.FILE_NAME_PRODUCT){
					System.out.println("商品定義ファイルが存在しません");
				}else {
					System.out.println("予期せぬエラーが発生しました");
				}
				return null;
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String strLine;
			//ファイル末尾まで一行ずつ読み込む
			while((strLine = br.readLine()) != null) {
				//ファイルフォーマットのチェック
				if(fileName == Constants.FILE_NAME_BRANCH){
					if(strLine.matches("\\d{3},[^,]*支店") == false){
						System.out.println("支店定義ファイルのフォーマットが不正です");
						br.close();
						return null;
					}
				}else if(fileName == Constants.FILE_NAME_PRODUCT){
					if(strLine.matches("SFT\\d{5},[^,]*") == false){
						System.out.println("商品定義ファイルのフォーマットが不正です");
						br.close();
						return null;
					}
				}
				//一行をカンマで分割して、コード,名前をdifDataListに登録
				String[] strSplit = strLine.split(",",0);
				DifinitionData difData = new DifinitionData();
				difData.setCode(strSplit[0]);
				difData.setName(strSplit[1]);
				difDataList.put(difData.getCode(),difData);
			}
			br.close();
		}catch(FileNotFoundException e){
			  System.out.println("予期せぬエラーが発生しました");
			  System.out.println(e);
			  return null;
		}catch(IOException e){
			  System.out.println("予期せぬエラーが発生しました");
			  System.out.println(e);
			  return null;
		}

		return difDataList;

	}
}
