package com.toy.search.utils;

import com.toy.search.constant.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;


/**
 * @author: 冯彧
 * @Desc:Token相关工具类
 * @createTime:2018年3月1日
 */
public class TokenUtil {

	private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

	/**
	 * @author: 冯彧
	 * @Desc:创建cookie里的ZL_UEC(固定的过期时间+token+用户id, 把组装好的字符串先进行AES 加密 然后base64转码)
	 * @createTime:2018年2月28日
	 */
	public static Cookie createCookie(long userId,String token,long expireTime) {
		try {
			String all = expireTime + Constants.COOKIE_SPLIT +"6446" +Constants.COOKIE_SPLIT + userId;
			byte[] enc = CipherUtil.encByAES(all.getBytes(), CipherUtil.TOKEN_KEY);
			String string = new String(Base64.encodeBase64(enc),"UTF-8");
			Cookie cookie = new Cookie(Constants.COOKIE_NAME, string);
			cookie.setMaxAge(3600 * 24 * 180);
			cookie.setPath("/");
			return cookie;
		} catch (Exception e) {
			log.error("错误原因:",e);
			log.error("userId==>{}",userId);
			return null;
		}
	}

	/**
	 * @author: 冯彧
	 * @Desc:解密token字符串，从而得到用户id
	 * @createTime:2018年2月28日
	 */
	public static long getUserIdByCookie(String token) {
		try {
			if(StringUtils.isEmpty(token)) {
				return Constants.INVALID_USER_ID;
			}
			token = URLDecoder.decode(token,"UTF-8");
			if(token.indexOf(" ")!=-1){
				token=token.replaceAll("\\s","+");
			}
			byte[] enc = token.getBytes(Constants.ENCODING);
			enc = CipherUtil.decByAES(Base64.decodeBase64(enc),CipherUtil.TOKEN_KEY);
			String[] ss = new String(enc).split(Constants.COOKIE_SPLIT);
			// cookie: [0]-过期时间 [1]-token [2]:userId
			long expireTime = Long.parseLong(ss[0]);
			//String token = ss[1];
			long userId = Long.parseLong(ss[2]);
			if (System.currentTimeMillis() > expireTime) {
				userId = -3;
			}
			return userId;
		} catch (Exception e) {
			log.error("错误原因:",e);
			log.error("token==>{}",token);
			return Constants.INVALID_USER_ID;
		}
	}

	/**
	 * @author: 冯彧
	 * @Desc:获取request里的用户Id(有些移动设备不支持种cookie,所以采用传参的方式(参数名叫token))
	 * @createTime:2018年4月26日
	 */
	public static long getUserIdFromCookie(HttpServletRequest request){
		String token = request.getParameter((Constants.TOKEN));
		if(null!=token && !token.trim().equals("")){
			return TokenUtil.getUserIdByCookie(token);
		}else{
			Cookie[] cookies = request.getCookies();
			if(null != cookies){
				for (Cookie cookie : cookies) {
					if (Constants.COOKIE_NAME.equals(cookie.getName())) {
						token = cookie.getValue();
						return TokenUtil.getUserIdByCookie(token);
					}
				}
			}
		}
		return Constants.INVALID_USER_ID;
	}

	/**
	 * @author: 冯彧
	 * @Desc:获取cookie里的参数
	 * @createTime:2018年4月26日
	 */
	public static String getTokenFromCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if (Constants.COOKIE_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return StringUtils.EMPTY;
	}
}