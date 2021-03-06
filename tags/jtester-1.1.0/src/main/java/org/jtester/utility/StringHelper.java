package org.jtester.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class StringHelper {
	 public static final String EMPTY = "";
	/**
	 * 判断string是否为null或空字符串
	 * 
	 * @param in
	 * @return
	 */
	public static boolean isBlankOrNull(String in) {
		if (in == null) {
			return true;
		} else if (in.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static String trim(String source) {
		if (source == null) {
			return null;
		} else {
			return source.trim();
		}
	}

	/**
	 * 返回驼峰命名处理结果字符串
	 * 
	 * @param name
	 * @return
	 */
	public static String camel(String name) {
		if (StringHelper.isBlankOrNull(name)) {
			return "";
		}
		StringBuffer b = new StringBuffer(name.length());
		StringTokenizer t = new StringTokenizer(name);
		b.append(t.nextToken());
		while (t.hasMoreTokens()) {
			String token = t.nextToken();
			b.append(token.substring(0, 1).toUpperCase());
			// replace spaces with camelCase
			b.append(token.substring(1));
		}
		return b.toString();
	}

	/**
	 * 返回驼峰命名处理结果字符串
	 * 
	 * @param name
	 * @param strings
	 * @return
	 */
	public static String camel(String name, String... strings) {
		StringBuffer b = new StringBuffer(name);
		for (String s : strings) {
			b.append(" ");
			b.append(s);
		}
		return camel(b.toString());
	}

	/**
	 * 把exception的trace信息写到string中
	 * 
	 * @param e
	 * @return 返回异常信息的序列化字符串
	 */
	public static String exceptionTrace(Throwable e) {
		StringWriter w = new StringWriter();
		e.printStackTrace(new PrintWriter(w));
		return w.toString();
	}

	/**
	 * 把exception的trace信息写到string中,同时过滤掉filters中指定的信息
	 * 
	 * @param e
	 * @param filters
	 * @return 返回异常信息的序列化字符串
	 */
	public static String exceptionTrace(Throwable e, List<String> filters) {
		StringWriter w = new StringWriter();
		e.printStackTrace(new PrintWriter(w));
		String tracer = w.toString();
		for (String regex : filters) {
			tracer = tracer.replaceAll(regex, "");
		}
		return tracer;
	}

	/**
	 * 默认的Exception信息过滤规则
	 */
	public final static List<String> DEFAULT_EXCEPTION_FILTER = new ArrayList<String>() {
		private static final long serialVersionUID = -5015223773312948340L;

		{
			add("\\s*at\\s*org\\.jtester\\.fit\\.FitRunner\\.[^\\s]+");
			add("\\s*at\\s*org\\.testng\\.[^\\s]+");
			add("\\s*at\\s*sun\\.reflect\\.[^\\s]+");
			add("\\s*at\\s*java\\.lang\\.reflect\\.[^\\s]+");
			add("\\s*at\\s*fitlibrary\\.[^\\s]+");
		}
	};

	private static SimpleDateFormat simpleDateTimeFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat simpleTimeFormate = new SimpleDateFormat("mm:ss");

	public static String simpleDateStr(Timestamp time) {
		return simpleTimeFormate.format(time);
	}

	public static String simpleDateTimeStr(long time) {
		if (time > 0) {
			return simpleDateTimeFormate.format(new Timestamp(time));
		} else {
			return "--:--";
		}
	}

	public static String simpleTimeStr(long time) {
		if (time > 0) {
			return simpleTimeFormate.format(new Timestamp(time));
		} else {
			return "--";
		}
	}

	public static int parseInt(String str, int _default) {
		try {
			return Integer.parseInt(str);
		} catch (Throwable e) {
			return _default;
		}
	}

	/**
	 * 根据splitStr分割字符串string，并且过滤掉空串
	 * 
	 * @param string
	 * @param splitStr
	 * @return
	 */
	public static String[] splits(String string, String splitStr) {
		if (StringHelper.isBlankOrNull(string)) {
			return new String[0];
		}
		String temp = string.replaceAll("\\s", "");
		String[] splits = temp.split(splitStr);
		List<String> clazzes = new ArrayList<String>();
		for (String split : splits) {
			if (StringHelper.isBlankOrNull(split)) {
				continue;
			}
			clazzes.add(split);
		}
		return clazzes.toArray(new String[0]);
	}

	/**
	 * 合并字符串数组
	 * 
	 * @param seperator
	 * @param strings
	 * @return
	 */
	public static String join(String seperator, String[] strings) {

		if (strings == null || strings.length == 0) {
			return "";
		}
		boolean first = true;
		StringBuffer buff = new StringBuffer();
		for (String str : strings) {
			if (first == false) {
				buff.append(seperator);
			} else {
				first = false;
			}
			buff.append(str);
		}
		return buff.toString();
	}

	/**
	 * 把文本转义成正确格式的Html 比如:"<"转换成"&lt"等等
	 * 
	 * @param html
	 * @return format后的文本
	 */
	public static String formatHtml(String html) {
		String result = html;

		result = result.replaceAll("\"", "&quot;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll(" ", "&nbsp;");
		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("\n", "<br/>");
		return result;
	}

	/**
	 * 将对象转化为字符串{ddd,ddd}
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		StringBuilder buff = new StringBuilder("{");
		boolean first = true;
		if (o == null) {
			return null;
		} else if (o instanceof Collection<?>) {
			Collection<?> oc = (Collection<?>) o;
			for (Object o1 : oc) {
				if (first == false) {
					buff.append(",");
				} else {
					first = false;
				}
				buff.append(toString(o1));
			}
			buff.append("}");
			return buff.toString();
		} else if (o instanceof Object[]) {
			Object[] oa = (Object[]) o;
			for (Object o2 : oa) {
				if (first == false) {
					buff.append(",");
				} else {
					first = false;
				}
				buff.append(toString(o2));
			}
			buff.append("}");
			return buff.toString();
		} else {
			return o.toString();
		}
	}

	/**
	 * 将原始字符串转义为ascII字符串<br>
	 * 例如，原始字符串为：我是中文 <br>
	 * 转义后就是:\u6211\u662f\u4e2d\u6587
	 * 
	 * @param nativeStr
	 * @return
	 */
	public static String native2ascii(final String nativeStr) {
		StringBuffer ret = new StringBuffer();
		if (nativeStr == null) {
			return null;
		}
		int maxLoop = nativeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			char character = nativeStr.charAt(i);
			final int n127 = 127;
			final int n4 = 4;
			if (character <= n127) {
				ret.append(character);
			} else {
				ret.append("\\u");
				String hexStr = Integer.toHexString(character);
				int zeroCount = n4 - hexStr.length();
				for (int j = 0; j < zeroCount; j++) {
					ret.append('0');
				}
				ret.append(hexStr);
			}
		}
		return ret.toString();
	}

	/**
	 * 将转义后的ascII字符串恢复成原始的是字符串<br>
	 * 例如，转义后就是:\u6211\u662f\u4e2d\u6587<br>
	 * 原始字符串为：我是中文 <br>
	 * 
	 * 
	 * @param asciiStr
	 * @return
	 */
	public static String ascii2native(final String asciiStr) {
		if (asciiStr == null) {
			return null;
		}

		StringBuffer retBuf = new StringBuffer();
		int maxLoop = asciiStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (asciiStr.charAt(i) == '\\') {
				final int n5 = 5;
				final int n6 = 6;
				final int n16 = 16;
				if (i < maxLoop - n5 && (asciiStr.charAt(i + 1) == 'u' || asciiStr.charAt(i + 1) == 'U')) {
					try {
						retBuf.append((char) Integer.parseInt(asciiStr.substring(i + 2, i + n6), n16));
						i += n5;
					} catch (NumberFormatException e) {
						retBuf.append(asciiStr.charAt(i));
					}
				} else {
					retBuf.append(asciiStr.charAt(i));
				}
			} else {
				retBuf.append(asciiStr.charAt(i));
			}
		}

		return retBuf.toString();
	}

	/**
	 * 将文件中属性转义，并且去除注释行
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String native2asciiWithoutComment(final String fileName) throws IOException {
		StringBuffer buf = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		boolean continueFlg = false;
		String line = null;
		while ((line = reader.readLine()) != null) {
			if ((line.trim().startsWith("#") || line.trim().startsWith("!")) && !continueFlg) {
				buf.append(line);
			} else {
				if (line.endsWith("\\")) {
					continueFlg = true;
				} else {
					continueFlg = false;
				}
				buf.append(native2ascii(line));
			}
			buf.append("\n");
		}
		if (!fileName.endsWith("\n")) {
			buf.deleteCharAt(buf.length() - 1);
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Deletes all whitespaces from a String as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.deleteWhitespace(null)         = null
	 * StringUtils.deleteWhitespace("")           = ""
	 * StringUtils.deleteWhitespace("abc")        = "abc"
	 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to delete whitespace from, may be null
	 * @return the String without whitespaces, <code>null</code> if null String
	 *         input
	 */
	public static String deleteWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * <p>
	 * Gets the substring after the last occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. An empty or
	 * <code>null</code> separator will return the empty string if the input
	 * string is not <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.substringAfterLast(null, *)      = null
	 * StringUtils.substringAfterLast("", *)        = ""
	 * StringUtils.substringAfterLast(*, "")        = ""
	 * StringUtils.substringAfterLast(*, null)      = ""
	 * StringUtils.substringAfterLast("abc", "a")   = "bc"
	 * StringUtils.substringAfterLast("abcba", "b") = "a"
	 * StringUtils.substringAfterLast("abc", "c")   = ""
	 * StringUtils.substringAfterLast("a", "a")     = ""
	 * StringUtils.substringAfterLast("a", "z")     = ""
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring after the last occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}
}
