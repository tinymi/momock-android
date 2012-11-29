package com.momock.util;

import com.momock.data.DataList;
import com.momock.data.DataMap;
import com.momock.data.IDataMutableList;
import com.momock.data.IDataNode;
import com.momock.data.IDataView;

public class DataHelper {
	public static <T extends DataMap<String, Object>> IDataMutableList<T> fromDataNodes(IDataView<IDataNode> nodes, Class<T> cls){
		DataList<T> dl = new DataList<T>();
		for(int i = 0; i < nodes.getItemCount(); i++){
			try {
				T obj = cls.newInstance();
				obj.copyPropertiesFrom(nodes.getItem(i));
				dl.addItem(obj);
			} catch (Exception e) {
				Logger.error(e.getMessage());
			}
		}
		return dl;
	}
}