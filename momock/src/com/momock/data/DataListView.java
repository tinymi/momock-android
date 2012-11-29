package com.momock.data;

import java.util.Arrays;
import java.util.Comparator;

public class DataListView<T> extends DataViewBase<T> {
	IDataList<T> source;
	public DataListView(){
		
	}
	public DataListView(IDataList<T> source){		
		this.source = source;
		refresh();
	}
	public DataListView(IDataList<T> source, IFilter<T> filter) {
		this.filter = filter;
		this.source = source;
		refresh();
	}

	public DataListView(IDataList<T> source, IFilter<T> filter, IOrder<T> order) {
		this.filter = filter;
		this.source = source;
		this.order = order;
		refresh();
	}
	@SuppressWarnings("unchecked")
	@Override
	public void refresh() {		
		store.removeAllItems();
		if (source == null) return;
		for (int i = 0; i < source.getItemCount(); i++) {
			T obj = source.getItem(i);
			if (filter == null || filter.check(obj)) {
				store.addItem(obj);
			}
		}
		if (store.getItemCount() > 0 && order != null) {
			int count = store.getItemCount();
			Object[] objs = new Object[count];
			for (int i = 0; i < count; i++) {
				objs[i] = store.getItem(i);
			}
			Arrays.sort(objs, new Comparator<Object>() {
				@Override
				public int compare(Object lhs, Object rhs) {
					return order.compare((T)lhs, (T)rhs);
				}
			});

			if (limit > 0){
				store.removeAllItems();
				for (int i = 0; i < limit; i++) {	
					if (offset + i < count)
						store.addItem((T)objs[offset + i]);
					else 
						break;
				}
			} else {
				for (int i = 0; i < count; i++) {
					store.setItem(i, (T)objs[i]);
				}
			}
		}
	}
	public IDataList<T> getSource() {
		return source;
	}
	public void setSource(IDataList<T> source) {
		this.source = source;
		refresh();
	}

}