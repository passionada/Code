package cn.edu.xaut.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class Edge implements Serializable{
	private static final long serialVersionUID = 1L;
	@JSONField(name = "from")
	public int u;
	@JSONField(name = "to")
	public int v;
	@JSONField(name = "text")
	public char key;

	public Edge(int u, int v, char key) {
		super();
		this.u = u;
		this.v = v;
		this.key = key;
	}

	public int getU() {
		return u;
	}

	public void setU(int u) {
		this.u = u;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public char getKey() {
		return key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return u + "->" + v + " " + key;
	}

	@Override
	public boolean equals(Object arg0) {
		Edge tmp = (Edge) arg0;
		return tmp.u == this.u && tmp.v == this.v && tmp.key == this.key;
	}

	@Override
	public int hashCode() {
		return u + v + key;
	}
}
