package zset;


import java.util.Comparator;

public class ZSetComparator implements Comparator<MemberScore>{
	
	
	    @Override
	    public int compare(MemberScore o1, MemberScore o2) {
	        return o1.getScore().compareTo(o2.getScore());
	    }
	
	   
}
