package com.yunma.entity.sankeydiagram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SankeyDiagram implements Serializable{
	
	private Integer rootProCount;
	
	private List<Node> nodes=new ArrayList<Node>();
	
	
	private List<Link> links=new ArrayList<Link>();


	
	public Integer getRootProCount() {
		return rootProCount;
	}


	public void setRootProCount(Integer rootProCount) {
		this.rootProCount = rootProCount;
	}


	public List<Node> getNodes() {
		return nodes;
	}


	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}


	public List<Link> getLinks() {
		return links;
	}


	public void setLinks(List<Link> links) {
		this.links = links;
	}
	

}
