package com.yunma.vo.dictionary;

import java.io.Serializable;

public class DictionaryVo implements Serializable{

	private Integer groupId;	//字典组id
	private String groupCode;	//字典组code
	private String groupName;	//字典组名
	private String groupRemake;	//字典组备注
	private Integer itemId;		//字典项id
	private Integer itemSort;	//字典项序号
	private String itemName;	//字典项名
	private String itemRemake;	//字典项备注
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupRemake() {
		return groupRemake;
	}
	public void setGroupRemake(String groupRemake) {
		this.groupRemake = groupRemake;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getItemSort() {
		return itemSort;
	}
	public void setItemSort(Integer itemSort) {
		this.itemSort = itemSort;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemRemake() {
		return itemRemake;
	}
	public void setItemRemake(String itemRemake) {
		this.itemRemake = itemRemake;
	}
	
	
}
