/*
 * Copyright ?2007-2011 Blue Bamboo Ltd. 
 * 
 */
package com.hp.mss.print.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;


/**
 * The class ia a collection of tools for parsing String ,getting card type or
 * dealing with time and date
 * 
 * @author Nelson
 */
public class Util
{
	/**
	 * Returns the current HH:MM
	 * 
	 * @param time - time string
	 * @return format time string
	 */

	public static final byte[] UNICODE_TEXT = new byte[] {0x23, 0x23, 0x23,
			0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,
			0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,0x23, 0x23, 0x23,
			0x23, 0x23, 0x23};

	private static String hexStr = "0123456789ABCDEF";
	private static String[] binaryArray = { "0000", "0001", "0010", "0011",
			"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };

	public static String getHHMM(String time)
	{
		String finalTime = "00/00 AM/PM";
		String hh = time.substring(0, 2);
		String mm = time.substring(3, 5);

		int newHH = Integer.parseInt(hh);
		int newMM = Integer.parseInt(mm);

		newMM = newMM % 60;

		if (newHH == 0)
		{
			finalTime = "12:" + newMM + " PM";
		} else
		{

			if (newHH > 12)
			{
				newHH = newHH % 12;
				finalTime = newHH + ":" + newMM + " PM";
			} else
				finalTime = newHH + ":" + newMM + " AM";
		}

		String HH = finalTime.substring(0, finalTime.indexOf(":"));
		String MM = finalTime.substring(finalTime.indexOf(":") + 1, finalTime
				.indexOf(" "));
		String AMPM = finalTime.substring(finalTime.indexOf(" "), finalTime
				.length());

		if (MM.length() == 1)
			MM = "0" + MM;

		finalTime = HH + ":" + MM /*+ " " */+ AMPM;

		return (finalTime);
	}

	/**
	 * This method returns the numeric value in string so that further it can be
	 * concanated with other value strings like day,hours,min,seconds at the
	 * time of getting eraseTime at savingTransaction.
	 * 
	 * @param month -
	 *            String values like "Jan","Feb"....etc.
	 * @return String
	 */
	private static String[] monthNames =
	{ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };

	public static String getMonthDigit(String month)
	{
		if("123456789101112".indexOf(month)!=-1)
		{
			return month;
		}
		String mon = "-1";
		for (int i = 0; i < 12; i++)
		{
			if (equalsIgnoreCase(month, monthNames[i]))
			{
				mon = "" + (i + 1);
				break;
			}
		}
		return mon;
	}

	public static String getMonthName(int index)
	{
		if(index >= 1 && index <= 12)
		{
			return monthNames[index-1];
		}
		return "";
	}
	
	public static boolean equalsIgnoreCase(String str1, String str2)
	   {
		   if(str1 == null && str2 == null)
		   {
			   return true;
		   }
		   if((str1 == null && str2 != null)
				   || (str1 != null && str2 == null))
		   {
			   return false;
		   }
		   if(str1.toLowerCase().equals(str2.toLowerCase()))
		   {
			   return true;
		   }
		   else
		   {
			   return false;
		   }
	   }
	
	/**
    * trim the input string's space
    * 
    * @param oldString - input string
    * @return trimmed string
    */
	public static String trimSpace(String oldString)
	{
		if (null == oldString)
			return null;
		if (0 == oldString.length())
			return "";
		StringBuffer sbuf = new StringBuffer();
		int oldLen = oldString.length();
		for (int i = 0; i < oldLen; i++)
		{
			if (' ' != oldString.charAt(i))
				sbuf.append(oldString.charAt(i));
		}
		String returnString = sbuf.toString();
		sbuf = null;
		return returnString;
	}

	/**
	 * Convert hex string to byte array
	 * 
	 * @param s - input String
	 * @param offset - start position
	 * @param len - byte length
	 * @return byte array
	 */
	public static byte[] hex2byte(String s, int offset, int len)
	{
		byte[] d = new byte[len];
		int byteLen = len * 2;
		for (int i = 0; i < byteLen; i++)
		{
			int shift = (i % 2 == 1) ? 0 : 4;
			d[i >> 1] |= Character.digit(s.charAt(offset + i), 16) << shift;
		}
		return d;
	}

	/**
	 * Convert hex string to byte array
	 * 
	 * @param s - input String
	 * @return byte array
	 */
	public static byte[] hexString2bytes(String s)
	{
		if (null == s)
			return null;
		s = trimSpace(s);
		return hex2byte(s, 0, s.length() >> 1);
	}









	/**
	 * Seperate String with str token
	 * 
	 * @param str - the string which will be cut
	 * @return cut string array
	 */
	@SuppressWarnings("unused")
	private static String[] seperateStr(String str)
	{
		boolean doubleSpace = false;
		int wordCount = 0;
		StringBuffer sb = new StringBuffer();
		if (str == null || str.length() == 0)
		{
			return null;
		}
		for (int j = 0; j < str.length(); j++)
		{
			if (str.charAt(j) == ' ')
			{
				if (!doubleSpace)
					wordCount++;

				doubleSpace = true;
				continue;
			}
			doubleSpace = false;
		}
		String st[] = new String[wordCount + 1];
		int i = 0;

		doubleSpace = false;
		String ch = "";
		for (int j = 0; j < str.length(); j++)
		{
			if (str.charAt(j) == ' ')
			{
				if (!doubleSpace)
				{
					st[i] = sb.toString();
					sb.delete(0, sb.length());
					i++;
				}
				doubleSpace = true;
				continue;
			} else
			{
				sb.append(str.charAt(j));
			}
			doubleSpace = false;
		}

		st[i] = sb.toString();
		;
		return st;
	}

	/**
	 * Fit the original string to the page
	 * 
	 * @param matter - input data
	 * @param lineSize - display width
	 * @param isBeginWithSpace - true if begin with space- 
	 * @return display string
	 */
	public static String fitToThePage(String matter,int lineSize,boolean isBeginWithSpace)  
	  {
	      if(matter.equals(""))
	          return ""; 
	      
	      String space=" ";
	      String bSpace="";
	      if(isBeginWithSpace)
	          bSpace =" ";
	      
	       boolean doubleSpace = false;
	      
	        int j=0;       
	        int word=1;    
	        
	        // This loop will find that how much words are present in the string
	        for(j=0;j<matter.length();j++)
	        {
	            if(matter.charAt(j)==' ')
	            {
	                if(!doubleSpace)
	                    word++;

	                doubleSpace = true;
	                continue;
	            }
	            doubleSpace = false;
	        }
	        String st[] = new String[word];
	        String ch ="";
	        int i=0;  
	        
	        doubleSpace = false;
	        //This loop will store words in the String array st[]
	        for(j=0;j<matter.length();j++)
	        {
	            if(matter.charAt(j)==' ')
	            {
	                if(!doubleSpace)
	                {
	                    st[i] = ch;
	                    ch="";   
	                    i++;     
	                }
	                doubleSpace = true;
	                continue;
	            }
	            else
	            {
	                ch = ch + matter.charAt(j);
	            }
	          doubleSpace = false;
	        }
	        st[i]=ch;
	        
	        ch = "";
	        String newString="";
	        @SuppressWarnings("unused")
			int len = st.length;
	        
	        for(i=0 ; i<word ; i++)
	        {
	                ch = ch + " "+ st[i];
	                
	                if(!isBeginWithSpace)
	                    ch = ch.trim();
	                if( ch.length() > lineSize )
	                {
	                        newString = newString +"\n" +bSpace + st[i];
	                        ch = "";
	                        ch = bSpace + st[i];
	                }else
	                {
	                        newString = newString + space + st[i];
	                        if(!isBeginWithSpace)
	                            newString = newString.trim();
	                }
	        }
	        return newString;
	  }

	

	
	/** 
	 * This method splits date in the String array and return it .
	 * @param date - date in the string format
	 * @return String[]
	 */
	public static String[] filterDate(String date)
	{
		return seperateStr(date);
	}

	/**
	 * This method align two string left & right respactively in given char
	 * size.
	 * 
	 * @param param1
	 *            string-1
	 * @param param2
	 *            string-2
	 * @param cpl
	 *            number of characters
	 * @return formated Strings
	 */
	public static String nameLeftValueRightJustify(String param1, String param2,
			int cpl) {
		 if(param1 == null)
         	param1 = "";
         if(param2 == null)
         	param2 = "";
		int len = param1.length();
		return param1.trim()+rightJustify(param2, (cpl - len));
	}
	
	/**
	 * Right align to the string in given number of chars
	 * 
	 * @param item
	 *            String to be aligned
	 * @param digits
	 *            number of characters
	 * @return formated strings
	 */
	public static String rightJustify(String item, int digits) {
		StringBuffer buf = null;
		if(digits < 0)
		{
			buf = new StringBuffer();
		}
		else
		{
			buf = new StringBuffer(digits);
		}
		for (int i = 0; i < digits - item.length(); i++) {
			buf.append(" ");
		}
		buf.append(item);
		return buf.toString();
	}
	/**
	 * this method gives only last 4 digit of card number preceding with *.
	 * 
	 * @param ccNum
	 *            card number
	 * @return string
	 */
	public static String getAcountAsterixData(String ccNum) 
	{
		if(ccNum.length() < 4)
			return ccNum;
		int len = ccNum.length();
		String temp = "";
		for (int i = 0; i < (len - 4); i++) {
			temp = temp + "*";
		}
		return (temp + ccNum.substring((len - 4), (len)));
	}
	
	
	/**
	 * Center allign the string in specified digits
	 * 
	 * @param item
	 *            String to be allign
	 * @param digits
	 *            number of characters
	 * @return formated string
	 */
	public static String center(String item, int digits) {
		StringBuffer buf = null;
		if(digits < 0)
		{
			buf = new StringBuffer();
		}
		else
		{
			buf = new StringBuffer(digits);
		}
		int len = item.length();
		for (int i = 0; i < (digits - len) / 2; i++) {
			buf.append(" ");
		}
		buf.append(item);
		for (int i = 0; i < (digits - len) / 2; i++) {
			buf.append(" ");
		}
		return buf.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector processWrappedText(String text, int width)
	{
		Vector v = new Vector();
		if (text == null)
			return v;
		int cursor = 0;

		
		// needed to be modified  (xsx) 
		while (cursor < text.length())
		{
			int remainderLength = text.length() - cursor;
			int increment = 1;
			while ( (increment < remainderLength) &&
					(increment <= width))
			{
				increment++;
			}
			
			String subString = text.substring(cursor, cursor + increment);
			if ((subString.charAt(increment - 1) == ' ') ||
				(cursor + increment == text.length()) ||
				(text.charAt(cursor + increment) == ' '))
			{
				// no need to find last space
			}
			else
			{
				// need to backtrack to last space
				int lastSpaceIndex = subString.lastIndexOf(' ');
				if (lastSpaceIndex > 0)
				{
					increment = lastSpaceIndex;
				}
			}
			
			v.addElement(text.substring(cursor, cursor + increment).trim());
			
			cursor += increment;
		}
		
		return v;
	}
	
	public static int checkDeviceType(String deviceName)
	{
		if(deviceName == null || deviceName.length() == 0)
		{
			return DataConstants.DEVICE_NONE;
		}
		if(deviceName.indexOf("P25") != -1)
		{
			return DataConstants.DEVICE_P25M;
		}
		else if (deviceName.indexOf("H50") != -1)
		{
			return DataConstants.DEVICE_H50;
		}
		else //other devices maybe PC, we will think them as DEVICE_P25M for debugging
		{
			return DataConstants.DEVICE_P25M;
		}
	}

	public static byte[] decodeBitmap(Bitmap bmp){
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();

		List<String> list = new ArrayList<String>(); //binaryString list
		StringBuffer sb;


		int bitLen = bmpWidth / 8;
		int zeroCount = bmpWidth % 8;

		String zeroStr = "";
		if (zeroCount > 0) {
			bitLen = bmpWidth / 8 + 1;
			for (int i = 0; i < (8 - zeroCount); i++) {
				zeroStr = zeroStr + "0";
			}
		}

		for (int i = 0; i < bmpHeight; i++) {
			sb = new StringBuffer();
			for (int j = 0; j < bmpWidth; j++) {
				int color = bmp.getPixel(j, i);

				int r = (color >> 16) & 0xff;
				int g = (color >> 8) & 0xff;
				int b = color & 0xff;

				// if color close to white，bit='0', else bit='1'
				if (r > 160 && g > 160 && b > 160)
					sb.append("0");
				else
					sb.append("1");
			}
			if (zeroCount > 0) {
				sb.append(zeroStr);
			}
			list.add(sb.toString());
		}

		List<String> bmpHexList = binaryListToHexStringList(list);
		String commandHexString = "1D763000";
		String widthHexString = Integer
				.toHexString(bmpWidth % 8 == 0 ? bmpWidth / 8
						: (bmpWidth / 8 + 1));
		if (widthHexString.length() > 2) {
			Log.e("decodeBitmap error", " width is too large");
			return null;
		} else if (widthHexString.length() == 1) {
			widthHexString = "0" + widthHexString;
		}
		widthHexString = widthHexString + "00";

		String heightHexString = Integer.toHexString(bmpHeight);
		if (heightHexString.length() > 2) {
			Log.e("decodeBitmap error", " height is too large");
			return null;
		} else if (heightHexString.length() == 1) {
			heightHexString = "0" + heightHexString;
		}
		heightHexString = heightHexString + "00";

		List<String> commandList = new ArrayList<String>();
		commandList.add(commandHexString+widthHexString+heightHexString);
		commandList.addAll(bmpHexList);

		return hexList2Byte(commandList);
	}

	public static List<String> binaryListToHexStringList(List<String> list) {
		List<String> hexList = new ArrayList<String>();
		for (String binaryStr : list) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < binaryStr.length(); i += 8) {
				String str = binaryStr.substring(i, i + 8);

				String hexString = myBinaryStrToHexString(str);
				sb.append(hexString);
			}
			hexList.add(sb.toString());
		}
		return hexList;

	}

	public static String myBinaryStrToHexString(String binaryStr) {
		String hex = "";
		String f4 = binaryStr.substring(0, 4);
		String b4 = binaryStr.substring(4, 8);
		for (int i = 0; i < binaryArray.length; i++) {
			if (f4.equals(binaryArray[i]))
				hex += hexStr.substring(i, i + 1);
		}
		for (int i = 0; i < binaryArray.length; i++) {
			if (b4.equals(binaryArray[i]))
				hex += hexStr.substring(i, i + 1);
		}

		return hex;
	}

	public static byte[] hexList2Byte(List<String> list) {
		List<byte[]> commandList = new ArrayList<byte[]>();

		for (String hexStr : list) {
			commandList.add(hexStringToBytes(hexStr));
		}
		byte[] bytes = sysCopy(commandList);
		return bytes;
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static byte[] sysCopy(List<byte[]> srcArrays) {
		int len = 0;
		for (byte[] srcArray : srcArrays) {
			len += srcArray.length;
		}
		byte[] destArray = new byte[len];
		int destLen = 0;
		for (byte[] srcArray : srcArrays) {
			System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
			destLen += srcArray.length;
		}
		return destArray;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}


	public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
		Paint paint = new Paint(ANTI_ALIAS_FLAG);
		paint.setTextSize(textSize);
		paint.setColor(textColor);
		paint.setTextAlign(Paint.Align.LEFT);
		float baseline = -paint.ascent(); // ascent() is negative
//		int width = (int) (paint.measureText(getLineMaxWidth(text)) + 0.5f); // round
		int width = 383;
		int height = (text.split("\n").length) * (int) (baseline + paint.descent() + 0.5f);
		Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawColor(Color.WHITE);
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++) {
			canvas.drawText(lines[i], 0, baseline * (i+1), paint);
		}

		return image;
	}

	public static String getLineMaxWidth(String text){
		int position = 0;
		int max = 0;

		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++)
			if(max < lines[i].length()) {
				max = lines[i].length();
				position = i;
			}

		return lines[position];
	}

	private void SaveImage(Bitmap finalBitmap) {

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images");
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Image-"+ n +".jpg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete ();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
