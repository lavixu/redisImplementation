package main.java.impl.zSetImpl;

import java.io.Serializable;
import java.util.Comparator;

public class ZSetComparator implements Comparator<MemberScore>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(MemberScore o1, MemberScore o2) {
		return o1.getScore().compareTo(o2.getScore());
	}

}
