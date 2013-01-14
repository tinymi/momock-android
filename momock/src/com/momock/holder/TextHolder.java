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
package com.momock.holder;

import android.content.res.Resources;

import com.momock.util.Logger;

public abstract class TextHolder implements IHolder{

	static Resources theResources = null;
	public static void initialize(Resources resources){
		theResources = resources;
	}
	
	public abstract String getText();

	@Override
	public String toString() {
		return getText();
	}
	
	public static TextHolder get(final String text)
	{
		return new TextHolder()
		{
			public String getText()
			{
				return text;
			}
		};
	}
	public static TextHolder get(final int resourceId)
	{
		return new TextHolder()
		{
			String text = null;
			public String getText()
			{
				if (text == null){
					Logger.check(theResources != null, "The Resources must not be null!");
					text = theResources.getString(resourceId);
				}
				return text;
			}
		};
	}

	public static TextHolder get(final String format, final Object... args)
	{
		return new TextHolder()
		{
			public String getText()
			{
				return String.format(format, args);
			}
		};
	}
	public static TextHolder get(final int resourceId, final Object... args)
	{
		return new TextHolder()
		{
			String text = null;
			public String getText()
			{
				if (text == null){
					Logger.check(theResources != null, "The Resources must not be null!");
					text = theResources.getString(resourceId);
				}
				return String.format(text, args);
			}
		};
	}
}
