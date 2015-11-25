/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.ueditor.define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.comtop.ueditor.Encoder;

/**
 * 多状态集合状态
 * 其包含了多个状态的集合, 其本身自己也是一个状态
 * @author hancong03@baidu.com
 *
 */
public class MultiState implements State {
	
	/** state */
	private boolean state = false;
	/** info */
	private String info = null;
	/** intMap */
	private Map<String, Long> intMap = new HashMap<String, Long>();
	/** infoMap */
	private Map<String, String> infoMap = new HashMap<String, String>();
	/** stateList */
	private List<String> stateList = new ArrayList<String>();
	
	/**
	 * multiState
	 * @param state state
	 */
	public MultiState ( boolean state ) {
		this.state = state;
	}
	
	/**
	 * multiState
	 * @param state state
	 * @param info info
	 */
	public MultiState ( boolean state, String info ) {
		this.state = state;
		this.info = info;
	}
	
	/**
	 * MultiState
	 * @param state state
	 * @param infoKey infokey
	 */
	public MultiState ( boolean state, int infoKey ) {
		this.state = state;
		this.info = AppInfo.getStateInfo( infoKey );
	}
	
	@Override
	public boolean isSuccess() {
		return this.state;
	}
	
	/**
	 * add state
	 * @param state1 state
	 */
	public void addState ( State state1 ) {
		stateList.add( state1.toJSONString() );
	}

	/**
	 * 该方法调用无效果
	 */
	@Override
	public void putInfo(String name, String val) {
		this.infoMap.put(name, val);
	}

	@Override
	public String toJSONString() {
		
		String stateVal = this.isSuccess() ? AppInfo.getStateInfo( AppInfo.SUCCESS ) : this.info;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append( "{\"state\": \"" + stateVal + "\"" );
		
		// 数字转换
		Iterator<String> iterator = this.intMap.keySet().iterator();
		
		while ( iterator.hasNext() ) {
			
			stateVal = iterator.next();
			
			builder.append( ",\""+ stateVal +"\": " + this.intMap.get( stateVal ) );
			
		}
		
		iterator = this.infoMap.keySet().iterator();
		
		while ( iterator.hasNext() ) {
			
			stateVal = iterator.next();
			
			builder.append( ",\""+ stateVal +"\": \"" + this.infoMap.get( stateVal ) + "\"" );
			
		}
		
		builder.append( ", list: [" );
		
		
		iterator = this.stateList.iterator();
		
		while ( iterator.hasNext() ) {
			
			builder.append( iterator.next() + "," );
			
		}
		
		if ( this.stateList.size() > 0 ) {
			builder.deleteCharAt( builder.length() - 1 );
		}
		
		builder.append( " ]}" );

		return Encoder.toUnicode( builder.toString() );

	}

	@Override
	public void putInfo(String name, long val) {
		this.intMap.put( name, val );
	}

}
