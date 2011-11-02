package openmessenger

import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringUtils;

class OpenMessengerUtil {
	static String convertToUnicode(String msg){
		StringBuffer str = new StringBuffer()
		msg.toCharArray().each {
			str.append( CharUtils.unicodeEscaped(it) )
		}
		StringUtils.remove(str.toString(), "\\u")
	}
	
	static int getConcatinationSize(String msg, int size){
		msg.size().div(size).plus(msg.size()%size?1:0).intValue()
	}
}
