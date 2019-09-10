package com.ecard.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

public class AppUtil {

	public static void outJson(Map<String, Object> rs) throws Exception {
		//处理跨域问题
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String callbackName = request.getParameter("jsoncallback"); 
//        Gson g  = new Gson();
//        String json = g.toJson(rs);
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = requestAttributes.getResponse();
//        response.setContentType("application/json;charset=utf-8");
//        response.setHeader("Cache-Control","no-cache");
//        
//        PrintWriter pw = response.getWriter();
//        pw.print(callbackName + "("+json.toString()+")");
//        pw.flush();
//        pw.close(); 
        
        //服务器部署代码
        Gson g  = new Gson();
        String json = g.toJson(rs);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
	}

	public static void outText(String rs,String contentType) throws Exception {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        response.setContentType(contentType);
        response.setHeader("Cache-Control","no-cache");
        DataOutputStream wr = new DataOutputStream(response.getOutputStream());
        wr.write(rs.getBytes());//输出流
        wr.flush();
        wr.close();
	}

    public static void outXml(String xml) throws Exception {
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        response.setContentType("application/xml;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(xml);
        pw.flush();
        pw.close();
    }

	public static String makeJsonStr(Object obj) throws Exception {
        Gson g  = new Gson();
        return g.toJson(obj);
	}

	public static void setResultFlg(Map<String, Object> resultlMap, boolean isOk, String msg) throws Exception {
		if (isOk) {
			resultlMap.put("result", "1");
		} else {
			resultlMap.put("result", "0");
		}
		
		if (msg != null) {
			resultlMap.put("message", msg);
		}
	}
	
	public static boolean isNumeric(String str){ 
		if (str == null || str.length() == 0) return false;
		
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	}

	public static boolean isDecimal(String str){ 
		if (str == null || str.length() == 0) return false;
		
	    Pattern pattern = Pattern.compile("[0-9]*\\.{0,1}[0-9]*"); 
	    return pattern.matcher(str).matches();    
	}
	
    public static String formatPureLongDate(Date date) {
        if (date == null)
            return "";
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    
    public static Date parseDate(String str) {
        if (str == null || str.equals("")) {
            return null;
        }

        try {
            return DateUtils.parseDate(str, "yyyy-MM-dd");
        } catch (ParseException e) {
        	return null;
        }
    }

    public static Date parseDate(String str, String strFormat) {
        if (str == null || str.equals("")) {
            return null;
        } else if (strFormat == null || strFormat.length() == 0) {
        	strFormat = "yyyyMMdd";
        }

        try {
            return DateUtils.parseDate(str, strFormat);
        } catch (ParseException e) {
        	return null;
        }
    }

    public static Date parseTime(String str, String strFormat) {
        if (str == null || str.equals("")) {
            return null;
        } else if (strFormat == null || strFormat.length() == 0) {
        	strFormat = "HH:mm";
        }

        try {
            return DateUtils.parseDate(str, strFormat);
        } catch (ParseException e) {
        	return null;
        }
    }

    public static Date trimDate(Date date) {
    	if (date == null) return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
    }
    
    public static Date getOtherDate(Date baseDate, String intervalStr) {
    	int intervalType = Calendar.DAY_OF_YEAR;
    	int intervalCnt = 3;
    	
		return getOtherDate(baseDate,intervalType,intervalCnt);
    }

    public static Date getOtherDate(Date baseDate, int intervalType, int intervalCnt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(intervalType, intervalCnt);

		return calendar.getTime();
    }


    
    public static String calculateAge(Date birthday) {
    	if (birthday == null) {
    		return "";
    	}
    	Calendar cal = Calendar.getInstance();

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthday);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return Long.toString(age);
    }
    
    public static boolean checkIdNo(String idNo) {
    	if (idNo != null && idNo.length() != 15 && idNo.length() != 18) {
    		return false;
    	} else {
    		int endIdx = idNo.length() > 15 ? 17 : 15;
    		if (!isNumeric(idNo.substring(0, endIdx))) {
    			return false;
    		}
    	}
    	
    	return true;
    }

    public static String getCheckCode(int bitNum) {
    	StringBuffer sb = new StringBuffer("");
    	Random random = new Random();
    	
		for (int i=0; i<bitNum; i++) {
			sb.append(random.nextInt(10));
		}
		
		return sb.toString();
    }

    public static boolean sendSms(String mobile, String sendMsg) throws Exception {
    	GetMethod method = null;
    	InputStream ins = null;
		try {
			HttpClient client = new HttpClient();
			StringBuilder sb = new StringBuilder();

	    	String msg = java.net.URLEncoder.encode(sendMsg, "gbk");
	    	String url = String.format(
	    			"http://116.62.244.37/qdplat/SMSSendYD?usr=6686&pwd=zdyy6686@yz&mobile=%s&sms=%s&extdsrcid=",
	    			mobile, msg);

			method = new GetMethod(url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,  
	                new DefaultHttpMethodRetryHandler(3, false)); 
			
			int statusCode = client.executeMethod(method);  
		    if (statusCode == HttpStatus.SC_OK) {  
		    	ins = method.getResponseBodyAsStream();  
		        byte[] b = new byte[1024];  
		        int r_len = 0;  
		        while ((r_len = ins.read(b)) > 0) {  
		            sb.append(new String(b, 0, r_len, method.getResponseCharSet()));  
		        }  
		    } else { 
		    	return false;
//		        System.err.println("Response Code: " + statusCode);  
		    }
		    
		} catch (HttpException e) {
		    System.err.println("Fatal protocol violation: " + e.getMessage());
		    return false;
		} catch (IOException e) {
		    System.err.println("Fatal transport error: " + e.getMessage());
		    return false;
		} finally {
			if (method !=null) {
				method.releaseConnection();
			}
		    if (ins != null) {  
		        ins.close();  
		    }  
		}  
			
		return true;
    }
    public static String getStr(Object obj) {
    	if (obj == null) {
    		return "";
    	} else {
    		return obj.toString();
    	}
    }

}
