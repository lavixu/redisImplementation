package main.java.impl.zSetImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
public class SortedKeyValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Two maps, one to store key => member => score memory. This will be used
	 * to delete a member from already existing 
	 *  
	 */
	public static HashMap<String, TreeSet<MemberScore>> zMemberMap = new HashMap<String, TreeSet<MemberScore>>();
	private static HashMap<String, HashMap<String, MemberScore>> MemberScorePointerMap = new HashMap<String, HashMap<String,MemberScore>>();
	
	public static HashMap<String, TreeSet<MemberScore>> getzMemberMap() {
		return zMemberMap;
	}

	
	public Integer getCard(String key)
	{
		if(zMemberMap.containsKey(key))
		{
			return zMemberMap.get(key).size();
		}
		return 0;
	}
	
	
	public static void setzMemberMap(
			HashMap<String, TreeSet<MemberScore>> zMemberMap) {
		SortedKeyValue.zMemberMap = zMemberMap;
	}

	public int zAdd(String key, String member, int score)
	{
		boolean updated = false;
		//check if it already exists, if yes, update else add to the sorted set.
		HashMap<String, MemberScore> memberScorePointer = MemberScorePointerMap.get(key);
		
		//Delete the member from the set if it already exists.
		if (memberScorePointer != null)
		{
			if(memberScorePointer.containsKey(member))
			{
				
				MemberScore existingMember = memberScorePointer.get(member);
				TreeSet<MemberScore> set = zMemberMap.get(key);
				//delete the existing MemberScore class and add the new one.
				set.remove(existingMember);
				memberScorePointer.remove(member);
				updated = true;
			}
		}
		memberScorePointer = new HashMap<String, MemberScore>();
		TreeSet<MemberScore> set =  zMemberMap.get(key);
		//case where the key is added for the first time.
		
		if(set == null)
		{
			Comparator<MemberScore> ZSetComparator = new ZSetComparator();
			set = new TreeSet<MemberScore>(ZSetComparator);
			zMemberMap.put(key, set);
		}
		
		//Adding the new Member with score
		MemberScore memberScore = new MemberScore(member, score);
		set.add(memberScore);
		memberScorePointer.put(member, memberScore);
		MemberScorePointerMap.put(key, memberScorePointer);
		
		int elementAdded = updated ? 0 : 1;
		return elementAdded;
	}
	
	public int zCount(String key, int min, int max)
	{
		if(zMemberMap.containsKey(key))
		{
			TreeSet<MemberScore> set = zMemberMap.get(key);
			//max+1 to make Set subSet function inclusive of max in case of integers.
			int size = set.subSet(new MemberScore("+%", min), new MemberScore("-%",max+1)).size();
			return size ;
		}
		
		return 0;
	}
	
	
	//Currently range is o(n) :(.
	@SuppressWarnings("unchecked")
	public ArrayList<String> zRange(String key, int startIndex, int endIndex, boolean withScores)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		if(zMemberMap.containsKey(key))
		{
			TreeSet<MemberScore> set = zMemberMap.get(key);
			int size = set.size();
			if(startIndex < 0) //calculating the actual index in case of relative negative index.
			{
				startIndex = size - startIndex;
			}
			if(endIndex < 0)
			{
				endIndex = size + endIndex;
			}
			if(startIndex > endIndex)
			{
				result.add("(empty list or set)");
				return result;
			}
			else
			{
				Iterator<MemberScore> iter = set.iterator();
				int count = 0;
				// Reach to the start element in the range.
				while(iter.hasNext())
				{
					if(startIndex == count)
					{
						break;
					}
					iter = (Iterator<MemberScore>) iter.next();
					count++;
				}
				
				
				while(iter.hasNext() && count <= endIndex)
				{
					MemberScore memberScore = iter.next();
					result.add(memberScore.getMember());
					if(withScores)
					{
						result.add(memberScore.getScore().toString());
					}
					count++;
				}
			}
		}
		else
		{
			result.add("(empty list or set)");
		}
		return result;
	}
	
	
	

}
