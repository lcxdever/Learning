package com.blackbread.utils.httpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

public class Example {
	public static void main(String[] args) {
		Map<String, String> requestPara = new HashMap<String, String>();
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("UTF-8");
		request.setConnectionTimeout(10000000);
		request.setParameters(ParamsUtil.generatNameValuePair(requestPara));
		request.setUrl("http://zhifu.amap.com/pay/api/alipay/batchtrans/notify/");

		HttpResponse response;
		try {
			response = httpProtocolHandler.execute(request, "", "");
			if (response == null) {
				System.out.println("null");
			}
			String strResult = response.getStringResult();
			System.out.println(strResult);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
