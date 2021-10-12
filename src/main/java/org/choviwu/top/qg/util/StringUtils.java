package org.choviwu.top.qg.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * 
 * @author : junshang
 * @createTime : 2014-8-16 上午11:40:14
 * @version : 1.0
 * @description : String Utils
 *
 */
public class StringUtils {



	/**
	 * 基于32进制对整数进行简单加密
	 * 
	 * @param input
	 * @return
	 */
	public static String base32Encrypt(long input) {

		int loop = Long.toBinaryString(input).length() / 5 + 1;
		int codeBase = (int) (input % 8); // new Random().nextInt(7);
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < loop; i++)
			ret.append(base32_array[codeBase].charAt(Integer.parseInt(Long
					.toString((input >> (5 * i) & 31)))));

		return ret.toString() + code_base.charAt(codeBase);
	}

	/**
	 * 提供被32进制加密整数的还原
	 * 
	 * @param input
	 * @return
	 */
	public static long base32Decrypt(String input) {

		long ret = 0l;
		if(input == null || "".equals(input)){
			return ret ;
		}
		try {
			if(!org.apache.commons.lang3.StringUtils.isBlank(input)){
				int codeBase = code_base.indexOf(input.charAt(input.length() - 1));
				for (int i = input.length() - 2; i > -1; i--) {
					ret <<= 5;
					ret += base32_array[codeBase].indexOf(input.charAt(i)) & 31;
				}
			}

		} catch (Exception e) {
			System.err.println(e);
		}
		return ret;
	}

	private static final String[] base32_array = {
			"iq1sb4jgphn026c378rflakdtev9umo5",
			"e02qchj1g84ml9vnaodurktib5fs67p3",
			"9q2vetf3jgnrlibm1ak6d8ohsc540p7u",
			"vslqk0fbdcn83mj7epho51iua42t69rg",
			"n0ip5e7hq98jkdbmc2rvtgas643o1flu",
			"mvkir827dq9jaoucn06getls14pb35hf",
			"gp6c35sfe4u7rn0thkja21dq8bli9vom",
			"abde2k4gl05pqrnc8v19fi67s3mjutoh" };

	private static final String code_base = "ds8fj9er";

	
}
