/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.ueditor.define;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.comtop.ueditor.Encoder;

/**
 * BaseState
 * @author yangsai
 *
 */
public class BaseState implements State {
	/** state */
	private boolean state = false;
	/** info */
	private String info = null;
	
	/** infoMap */
	private Map<String, String> infoMap = new HashMap<String, String>();

	/**
	 * 构造方法
	 */
	public BaseState () {
		this.state = true;
	}
	
	/**
	 * 构造方法
	 * @param state state
	 */
	public BaseState ( boolean state ) {
		this.setState( state );
	}
	
	/**
	 * 构造方法
	 * @param state state
	 * @param info info
	 */
	public BaseState ( boolean state, String info ) {
		this.setState( state );
		this.info = info;
	}
	
	/**
	 * 构造方法
	 * @param state state
	 * @param infoCode infocode
	 */
	public BaseState ( boolean state, int infoCode ) {
		this.setState( state );
		this.info = AppInfo.getStateInfo( infoCode );
	}
	
	/**
	 * isSuccess
	 */
	public boolean isSuccess () {
		return this.state;
	}
	
	/**
	 * set state
	 * @param state state
	 */
	public void setState ( boolean state ) {
		this.state = state;
	}
	
	/**
	 * set info
	 * @param info info
	 */
	public void setInfo ( String info ) {
		this.info = info;
	}
	
	/**
	 * set info by info code
	 * @param infoCode infocode
	 */
	public void setInfo ( int infoCode ) {
		this.info = AppInfo.getStateInfo( infoCode );
	}
	
	@Override
	public String toJSONString() {
		return this.toString();
	}
	
	@Override
	public String toString () {
		
		String key = null;
		String stateVal = this.isSuccess() ? AppInfo.getStateInfo( AppInfo.SUCCESS ) : this.info;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append( "{\"state\": \"" + stateVal + "\"" );
		
		Iterator<String> iterator = this.infoMap.keySet().iterator();
		
		while ( iterator.hasNext() ) {
			
			key = iterator.next();
			
			builder.append( ",\"" + key + "\": \"" + this.infoMap.get(key) + "\"" );
			
		}
		
		builder.append( "}" );

		return Encoder.toUnicode( builder.toString() );

	}

	@Override
	public void putInfo(String name, String val) {
		this.infoMap.put(name, val);
	}

	@Override
	public void putInfo(String name, long val) {
		this.putInfo(name, val+"");
	}

}
