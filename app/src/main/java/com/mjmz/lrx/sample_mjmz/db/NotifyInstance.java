package com.mjmz.lrx.sample_mjmz.db;

import android.text.Html;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 消息保存
 * @author liurunxiong
 *
 */
@Table("my_notify")
public class NotifyInstance {
	public static final String NOTIFY_CONTENT = "notify_content"; //消息内容
	public static final String NOTIFY_TIME = "notify_time"; //消息获取时间
	public static final String NOTIFY_ACTION_ID = "notify_action_id"; //关联的活动ID
	public static final String NOTIFY_ISREADED = "notify_isreaded"; //消息是否读过
	public static final String NOTIFY_TITLE = "notify_title"; //消息标题
	public static final String NOTIFY_USERID = "notify_user_id"; //用户的消息

	@PrimaryKey(AssignType.AUTO_INCREMENT)
	@Column("id")
	private int id;

	@Column(NOTIFY_ACTION_ID)
	private String a_id = "";//活动ID

	@Column(NOTIFY_CONTENT)
	private String content = "";//消息内容

	@Column(NOTIFY_TIME)
	private String time = "";//消息获取的网络时间

	@Column(NOTIFY_ISREADED)
	private String isReaded = "0";//消息是否读取过   0:未读取过      1:读取过

	@Column(NOTIFY_TITLE)
	private String title = "";//消息标题

	@Column(NOTIFY_USERID)
	private int user_id = -1;//用户ID   
	
	
	public String Get_a_id()
	{
		return Html.fromHtml(a_id).toString();
	}
	
	public void Set_a_id(String ID)
	{
		a_id = ID;
	}
	/*****************************************************/
	public String Get_content()
	{
		return Html.fromHtml(content).toString();
	}
	
	public void Set_content(String ID)
	{
		content = ID;
	}
	/******************************************************/
	public String Get_isReaded()
	{
		return Html.fromHtml(isReaded).toString();
	}
	
	public void Set_isReaded(String ID)
	{
		isReaded = ID;
	}
	/******************************************************/
	public String Get_time()
	{
		return Html.fromHtml(time).toString();
	}
	
	public void Set_time(String ID)
	{
		time = ID;
	}
	/*******************************************************/
	public String Get_title()
	{
		return Html.fromHtml(title).toString();
	}
	
	public void Set_title(String ID)
	{
		title = ID;
	}
	/*******************************************************/
	public int Get_user_id()
	{
		return user_id;
	}
	
	public void Set_user_id(int ID)
	{
		user_id = ID;
	}
}
