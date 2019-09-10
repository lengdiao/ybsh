package com.ecard.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import com.ecard.pojo.Response;
import com.ecard.pojo.ResponseHasData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件上传测试类
 * 
 * @author yaoyu
 * @date 2018-7-30 14:32:55
 */

public class UploadUtil {
	  
	  public static boolean generateImage(String imgStr, String filePath) {
			 if (imgStr == null) {
				return false;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				  // 解密
				  byte[] b = decoder.decodeBuffer(imgStr);
				  // 处理数据
				for(int i = 0; i < b.length; ++i) {
					 if (b[i] < 0) {
						b[i] += 256;
					}
				}
				makeDir(filePath);
			 OutputStream out = new FileOutputStream(filePath);
				out.write(b);
				out.flush();
				 out.close();
				return true;
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				 e.printStackTrace();
			  }
			 return false;

		 }

	/**
	 * 确保目录存在，不存在则创建
	 *
	 * @param filePath
	 */
	private static void makeDir(String filePath) {
		if (filePath.lastIndexOf('/') > 0) {
			String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
}