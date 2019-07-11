//将NFA转变成确定化DFA
package cn.edu.xaut.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import com.alibaba.fastjson.JSONArray;

import cn.edu.xaut.entity.Edge;

class Pair {
	public int v;
	public char ch;

	public Pair(int v, char ch) {
		super();
		this.v = v;
		this.ch = ch;
	}

}

@SuppressWarnings("serial")
class MyHashSet extends HashSet<Integer> {// 重写 set 集合的 hashcode()和equals()方法
	private int state;

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	@Override
	public boolean equals(Object arg0) {
		MyHashSet tmp = (MyHashSet) arg0;
		if (tmp.size() != this.size())
			return false;
		Iterator<Integer> it = this.iterator();
		while (it.hasNext()) {
			if (!tmp.contains(it.next()))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int sum = 0;
		Iterator<Integer> it = this.iterator();
		while (it.hasNext())
			sum += (((java.lang.Integer) it.next()).intValue());
		return sum;
	}
}

class DefinedNFA {
	private int dfaNode = 0;// defined DFA节点的个数
	private boolean[] finalState = null;// 表示NFA中哪一个节点是终态
	private boolean[] newFinalState = new boolean[NFA.MAX_NODE];
	private Vector<Pair>[] g = null;// NFA 图
	private Set<Edge> edgeSet = new HashSet<Edge>(); // 标记图中的边是否被访问过
	private MyHashSet st = null; // 集合,表示每一个子集状态
	private Queue<MyHashSet> queue = new LinkedList<MyHashSet>();// 存放要执行的子集状态
	private Set<MyHashSet> sst = new HashSet<MyHashSet>();
	private Set<Character> characterSet = null;// 正规式中的字符的集合
	private ArrayList<Edge> nodeAl = new ArrayList<Edge>();// NFA边的集合

	public DefinedNFA(Vector<Pair>[] g, Set<Character> characterSet, boolean[] finalState) {
		super();
		this.g = g;
		this.characterSet = characterSet;
		this.finalState = finalState;
	}

	public Set<Character> getCharacterSet() {
		return characterSet;
	}

	public int getDfaNode() {
		return dfaNode;
	}

	public boolean[] getNewFinalState() {
		return newFinalState;
	}

	public ArrayList<Edge> getNodeAl() {
		return nodeAl;
	}

	private void dfs(int u, char ch) {
		if (g[u] == null)
			return;
		int len = g[u].size();
		for (int i = 0; i < len; ++i) {
			Pair pair = g[u].elementAt(i);
			Edge edge = new Edge(u, pair.v, pair.ch);
			if (!edgeSet.contains(edge) && pair.ch == ch) {
				edgeSet.add(edge);
				st.add(pair.v);
				dfs(pair.v, '$');
			}
		}

	}

	public void checkIsFinalState(Set<Integer> st, int state) {
		Iterator<Integer> it = st.iterator();
		while (it.hasNext()) {
			int val = it.next();
			if (finalState[val])
				newFinalState[state] = true;
		}
	}

	private void initFirstSet() {
		edgeSet.clear();
		st = new MyHashSet();
		st.add(1);
		st.setState(++dfaNode);
		dfs(1, '$');
		checkIsFinalState(st, dfaNode);
		sst.add(st);
		queue.add(st);
	}

	private void addEdge(int u, int v, char ch) {
		nodeAl.add(new Edge(u, v, ch));
	}

	public void ToStateMatrix() {
		initFirstSet();
		while (!queue.isEmpty()) {
			MyHashSet myset = queue.poll();
			for (Character ch : characterSet) {
				st = new MyHashSet();
				for (Integer i : myset) {
					edgeSet.clear();
					dfs(i, ch);
				}
				if (st.size() > 0) {
					if (!sst.contains(st)) {
						sst.add(st);
						queue.add(st);
						st.setState(++dfaNode);
						checkIsFinalState(st, dfaNode);
					} else {
						Iterator<MyHashSet> it = sst.iterator();
						while (it.hasNext()) {
							MyHashSet tmp = it.next();
							if (tmp.equals(st)) {
								st = tmp;
								break;
							}
						}
					}
					addEdge(myset.getState(), st.getState(), ch);
				}
			}
		}
	}

	public String outputDFA() {
		ToStateMatrix();// 有状态转换矩阵得到defined NFA
		for (Edge e : nodeAl)
			System.out.println(e);
		String jsonString = JSONArray.toJSONString(nodeAl);
		System.out.println(jsonString);
		return jsonString;
	}

}

public class RegexToDefinedDFA {

	public String getResult(String formal_ceremony) {
//        String formal_ceremony = "((10)|(01)*|(0|1))";
//        String formal_ceremony = "(0|1|6)|(2|3)|(4|5)";
//        String formal_ceremony = "1(0|1)*101";
//        String formal_ceremony = "0*(100*)*0*";
//       "0*(0|10)*0*";
		NFA nfa = new NFA(formal_ceremony);
		DefinedNFA definedDFA = new DefinedNFA(nfa.getNFAGraphics(), nfa.getCharacterSet(), nfa.getFinalState());
		return definedDFA.outputDFA();
	}
}