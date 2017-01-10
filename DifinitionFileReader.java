package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public abstract class DifinitionFileReader {
//定義ファイルの種類に応じて子クラスでファイル名と生成するオブジェクトを指定して利用

	String filePath;
	String fileName;
	String fileNameJap;		//ファイルの日本語名「◯◯定義ファイル」

	DifinitionFileReader(String folderPath, String fileName, String fileNameJap){
		//ファイル名、ファイルパスを取得
		this.fileName = fileName;
		this.fileNameJap = fileNameJap;
		filePath = folderPath + File.separator + fileName;
	}

	//DifinitionDataか子クラスのオブジェクトを新しく作成して返す
	public abstract DifinitionData returnNewDataObject();

	//定義ファイルを読み込み、内容を登録した定義データオブジェクトをハッシュマップに追加し、ハッシュマップを返す
	//エラーが発生したらnullを返す
	public HashMap<String,DifinitionData> getDifinitionDataMap(){

		HashMap<String,DifinitionData> difDataList = new HashMap<String,DifinitionData>();
		File file = new File(filePath);
		FileReader fr = null;
		BufferedReader br = null;

		//ファイルの存在を確認
		if(file.exists() == false){
			System.out.println(fileNameJap + "が存在しません");
			return null;
		}

		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String strLine;

			//ファイル末尾まで一行ずつ読み込むループ
			while((strLine = br.readLine()) != null) {

				//一行をカンマで分割
				String[] strSplit = strLine.split(",",0);

				//要素数をチェック
				if (strSplit.length != Constants.COLUMN_NUM_DIFINITION_FILE) {
					System.out.println(fileNameJap + "のフォーマットが不正です");
					return null;
				}

				//定義データオブジェクトを作成し、定義ファイル一行分の内容を登録
				DifinitionData difData = returnNewDataObject();
				difData.setCode(strSplit[0]);
				difData.setName(strSplit[1]);

				//作成した定義データオブジェクトをハッシュマップに追加
				difDataList.put(difData.getCode(),difData);

			}
		}catch(FileNotFoundException e){
			  System.out.println("予期せぬエラーが発生しました");
			  return null;
		}catch(IOException e){
			  System.out.println("予期せぬエラーが発生しました");
			  return null;
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
			}
		}

		return difDataList;

	}
}
