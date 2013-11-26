package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.testng.Assert;
import org.testng.annotations.Test;

import zset.MemberScore;
import zset.SortedKeyValue;

public class SortedKeyValueTest {
	
	//test if insertion is in sorted order and the order is maintained once the score is updated.
	
	@Test
	public void testZAdd()
	{
		String key = "key", member1 = "member1", member2 = "Member2";
		int score1 = 60, score2 = 10, score3 = 100;
		SortedKeyValue sortedKValue = new SortedKeyValue();
		sortedKValue.zAdd(key, member1, score1);
		sortedKValue.zAdd(key, member2, score2);
		TreeSet<MemberScore> set = SortedKeyValue.getzMemberMap().get(key);
		Iterator<MemberScore> iter = set.iterator();
		Assert.assertEquals(member2, iter.next().getMember());
		Assert.assertEquals(member1, iter.next().getMember());
		sortedKValue.zAdd(key, member2, score3);
		set = SortedKeyValue.getzMemberMap().get(key);
		iter = set.iterator();
		Assert.assertEquals(member1, iter.next().getMember());
		Assert.assertEquals(member2, iter.next().getMember()); //THe order should be swapped.
	}
	
	//test if zCount works for a given min and max (inclusive)
	@Test
	public void testZCount()
	{
		String key = "key", member1 = "member1", member2 = "Member2", member3 = "Member3";
		int score1 = 60, score2 = 10, score3 = 100;
		SortedKeyValue sortedKValue = new SortedKeyValue();
		sortedKValue.zAdd(key, member1, score1);
		sortedKValue.zAdd(key, member2, score2);
		sortedKValue.zAdd(key, member3, score3);
		Assert.assertEquals(2,sortedKValue.zCount(key, 50, 100));
	}
	
	//Give list of members in a given range
	@Test
	public void testZRange()
	{
		String key = "key", member1 = "member1", member2 = "Member2", member3 = "Member3";
		Integer score1 = 60, score2 = 10, score3 = 100;
		SortedKeyValue sortedKValue = new SortedKeyValue();
		sortedKValue.zAdd(key, member1, score1);
		sortedKValue.zAdd(key, member2, score2);
		sortedKValue.zAdd(key, member3, score3);
		ArrayList<String> result = sortedKValue.zRange(key, 0, -2, false); //without scores
		Assert.assertEquals(result.size(), 2);
		Assert.assertEquals(member2, result.get(0));
		Assert.assertEquals(member1, result.get(1));
		
		result = sortedKValue.zRange(key, 0, -2, true); //with scores
		Assert.assertEquals(result.size(), 4);
		Assert.assertEquals(result.get(0), member2);
		Assert.assertEquals(result.get(1), score2.toString());
	}
}


