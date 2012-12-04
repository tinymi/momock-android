/*******************************************************************************
 * Copyright 2012 momock.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.momock.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.momock.data.IDataList;
import com.momock.event.Event;
import com.momock.event.IEvent;
import com.momock.event.IEventHandler;
import com.momock.event.ItemEventArgs;
import com.momock.holder.ViewHolder;
import com.momock.util.Logger;

public class AdapterViewBinder<T extends AdapterView<?>> {
	IEvent<ItemEventArgs> itemClickedEvent = new Event<ItemEventArgs>();
	IEvent<ItemEventArgs> itemSelectedEvent = new Event<ItemEventArgs>();

	public IEvent<ItemEventArgs> getItemClickedEvent() {
		return itemClickedEvent;
	}

	public IEvent<ItemEventArgs> getItemSelectedEvent() {
		return itemSelectedEvent;
	}

	public AdapterViewBinder<T> addItemClickedEventHandler(
			IEventHandler<ItemEventArgs> handler) {
		itemClickedEvent.addEventHandler(handler);
		return this;
	}

	protected ItemViewBinder binder;

	public AdapterViewBinder(ItemViewBinder binder) {
		this.binder = binder;
	}

	@SuppressWarnings("unchecked")
	public void bind(ViewHolder view, IDataList<?> list) {
		bind((T) view.getView(), list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void bind(T view, final IDataList<?> list) {
		if (view != null) {
			view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ItemEventArgs args = new ItemEventArgs(parent, position,
							list.getItem(position));
					itemClickedEvent.fireEvent(view, args);
				}
			});
			view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					ItemEventArgs args = new ItemEventArgs(parent, position,
							list.getItem(position));
					itemSelectedEvent.fireEvent(view, args);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					Logger.debug("onNothingSelected");
				}
			});
			final BaseAdapter adapter = new BaseAdapter() {

				@Override
				public int getCount() {
					return list.getItemCount();
				}

				@Override
				public Object getItem(int position) {
					return list.getItem(position);
				}

				@Override
				public long getItemId(int position) {
					return position;
				}

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					return binder.onCreateItemView(convertView, position,
							getItem(position), parent);
				}

			};
			((AdapterView) view).setAdapter(adapter);
		}
	}
}
