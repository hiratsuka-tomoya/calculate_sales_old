package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.util.Comparator;

public class DifinitionDataComparator implements Comparator<DifinitionData> {
//降順ソート用にオーバーライドしたComparetor

	public int compare(DifinitionData d1, DifinitionData d2){
		return d1.getAmount() > d2.getAmount() ? -1 : 1;
	}

}
