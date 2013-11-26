package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import serialization.PersistData;

import helper.CommandParser;



public class test {
	
	public static void main(String atgs[]) throws IOException
	{
		PersistData.loadData();
		
		CommandParser parser = new CommandParser();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		String result = parser.parseCommand(input);
		System.out.println(result);
		while(!input.trim().equalsIgnoreCase("exit"))
		{
			input = br.readLine();
			result = parser.parseCommand(input);
			System.out.println(result);
		}
		
		
	}

}
