/*
 * Copyright 2012 Rui Araújo, Luís Fonseca
 *
 * This file is part of Router Keygen.
 *
 * Router Keygen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Router Keygen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Router Keygen.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.exobel.routerkeygen.thomsonGenerator;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class EntryTable {
	Map<Short , Integer > map;
	int numBytes;
	public EntryTable( int numBytes){
		this.map = new TreeMap<Short, Integer>();
		this.numBytes = numBytes;
	}
	
	public void addEntry( short secondByte , int offset){
		map.put(secondByte, offset);
	}
	
	public void toFile(byte [] outputData ){
		Iterator<Entry<Short, Integer>> it = map.entrySet().iterator();
		Entry<Short, Integer> entry;
		int offset = 0;
		while (it.hasNext()){
			entry = it.next();
			if ( numBytes == 3 )
			{
				outputData[offset + 0] = (byte) (0xFF & entry.getKey());
				outputData[offset + 1] = (byte) ( (0xFF0000 & entry.getValue()) >> 16) ;
				outputData[offset + 2] = (byte) ( (0xFF00 & entry.getValue()) >> 8) ;
				outputData[offset + 3] =(byte) (0xFF & entry.getValue());
				offset += 4;
			}
			else
			{
				outputData[offset + 0] = (byte) (0xFF & entry.getKey());
				outputData[offset + 1] = (byte) ( (0xFF000000 & entry.getValue()) >> 24) ;
				outputData[offset + 2] = (byte) ( (0xFF0000 & entry.getValue()) >> 16) ;
				outputData[offset + 3] = (byte) ( (0xFF00 & entry.getValue()) >> 8) ;
				outputData[offset + 4] =(byte) (0xFF & entry.getValue());
				offset += 5;
			}
		}
	}
	
	public void printAll() throws UnsupportedEncodingException{
		Iterator<Entry<Short, Integer>> it = map.entrySet().iterator();
		Entry<Short, Integer> entry;
		while (it.hasNext()){
			entry = it.next();
			System.out.println(AlphabetCodes.getHexString((byte) (0xFF &entry.getKey())) + 
					": " + entry.getValue());
		}
	}

	public void toWebDic(byte[] outputData) {
		Iterator<Entry<Short, Integer>> it = map.entrySet().iterator();
		Entry<Short, Integer> entry;
		int offset = 0;
		while (it.hasNext()){
			entry = it.next();
			if ( numBytes == 3 )
			{
				outputData[offset + 0] = (byte) ( (0xFF0000 & entry.getValue()) >> 16) ;
				outputData[offset + 1] = (byte) ( (0xFF00 & entry.getValue()) >> 8) ;
				outputData[offset + 2] =(byte) (0xFF & entry.getValue());
				offset += 3;
			}
			else
			{
				outputData[offset + 0] = (byte) ( (0xFF000000 & entry.getValue()) >> 24) ;
				outputData[offset + 1] = (byte) ( (0xFF0000 & entry.getValue()) >> 16) ;
				outputData[offset + 2] = (byte) ( (0xFF00 & entry.getValue()) >> 8) ;
				outputData[offset + 3] =(byte) (0xFF & entry.getValue());
				offset += 4;
			}
		}		
	}
}

