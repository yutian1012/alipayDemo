package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

@RequestMapping("alipay")
@Controller
public class AlipayController {
	
	public final static String URL="https://openapi.alipaydev.com/gateway.do";
	public final static String FORMAT="json";
	public final static String CHARSET="UTF-8";
	public final static String SIGN_TYPE="RSA2";
	public final static String APPID="2016091700532567";
	public final static String APP_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzKr6P2MmstF6M/Dov040Hl6/mfwolm5nPqYqeBcEkCiHnCmQ9VlYvULib77MGVrtIDXQpkFDoDLEfa/2fFFqnUYRpwcWyfaZgHWXF1QUhnuExwXRLR/SKTyxZM5K3PnZEeSGO+vD0EF850iFeYisCUI1/jLkMGm1b85HCiMI3yso1BxSk6yo9n/qXEDhvbAYQofUHzR1ZrKCRKcnBcwyOlBWdpB1ZlczKkvgvDBxwQgrS5J4U6QTAaz9Am8f+IkefMN9xrYmSahju4HTy8r9BufmfrZD5XYy5R7nBpUPNO6CuuHAC8sztkOBH0Gnr3ydKHyjXEL+LiZ12UggWqM9JAgMBAAECggEBAJDZZeb15VZzHF3vLTutVL9mqxc2bgWpnth8wUywak2ww2f+xruwKU1nfVjUmW/ufsrp1ZgkzMROPoSg0hLiacgL53l451Q+sVvTaIxghB2CTZ3kQRj7u4B2MeyL/XmYThVduPvXdSZcdiBQ+hlMVGkyprMQGirm0rMcYCNeXWasdV6mkuLcpIuXqBIMTgA7DxDkee6YSyzzX1Sqw3TYZBaIMzHz2fdBRhbRHZ2AAgxyyCM+zWRmb5iFyP+GCwwJrJpCZBpad4yiz+paTFu19pPdS+OlprsVd8zA5v/lW6Q25RwUnfd3/qG18aNdVDE+k45w1jRCtWh4+wX5b++mB2ECgYEA/Qu2RaiHqssZt+bQ+lvuI296N1RhXAFi6qc9AHKWYTp7vJTEh+ZQPd4qOyJzbUjqcNxPG7q389BkmzlM2KWs7etLnnFsS2Tx9solEim61D4ogvkr+g0fH0ZqI7MaBxTj9+1+M/kPZNRqr5WxH1J7bGc6AjCsHoQ6PMcpcAMH3r8CgYEAtUI6V/dABF/fkf8d9jyTL8MdQJq19fcVC1ZMYATIrllBAKnApG7FtNeAaVVGoCxh5lASPWsQih7PFrm8bU5iwVAZDpzsUAoq4eB/mHwh/ZjnxveX15/sFROoznKsWIrzioN9cUhwYkrB/Cjoj4ZR9wuJe1plgo3yZl8MMa3dW/cCgYEAhhezwlwZcIdqHIsDCPi4TCzh3fooEaJmcaSn7dnLGMDuGzPWpZGFyhM129pYwnvWSHJmtXIP0vMGrT8DGvdImVeL3e2LDrTyh+39EQ/uAlTiEO3LwRk8+czNqBdb3o7Sc2p160K7RppN4rf3gXqrDvnwwoJaY118owKDUek5PW8CgYBq5WX3Hs/YRJpbRbNr1omPL2h/Az6wkkkM6JYeQnP/ro+RTotX7iQW5MjMVghSyhnqc+pt4khczJKg+mTPqdy/2PcOESmkgnqLv5RAX2TEZHq8cqRP+/aE75UsO6C8jyXV2HoQZwIwVZpVleKhUjvgkCNCZitbdU8xZHiSi9DyPQKBgBPtat0CqXM36/rKOOJSJFMDNYDa/7dHwuLq+PAJB3qtCHW9Zukoqplw1DKVkkl9bD2P7jvFPMhIPrKcG6UisMkSqfE77yXEnXUGYwsnw3IJ2LGgjvPLzUmdzDmcmh7RmQ1HCfqB4F7l7qlwdNRntd3fg6MEieRa7G5gZhSjVSnz";
	public final static String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsyq+j9jJrLRejPw6L9ONB5ev5n8KJZuZz6mKngXBJAoh5wpkPVZWL1C4m++zBla7SA10KZBQ6AyxH2v9nxRap1GEacHFsn2mYB1lxdUFIZ7hMcF0S0f0ik8sWTOStz52RHkhjvrw9BBfOdIhXmIrAlCNf4y5DBptW/ORwojCN8rKNQcUpOsqPZ/6lxA4b2wGEKH1B80dWaygkSnJwXMMjpQVnaQdWZXMypL4LwwccEIK0uSeFOkEwGs/QJvH/iJHnzDfca2JkmoY7uB08vK/Qbn5n62Q+V2MuUe5waVDzTugrrhwAvLM7ZDgR9Bp698nSh8o1xC/i4mddlIIFqjPSQIDAQAB";
	public final static String returnUrl="http://localhost:8080/alipay/returnInfo";
	public final static String notifyUrl="http://localhost:8080/alipay/notifyInfo";
	
	/**
	 * 返回的form内容，可以看到这里的信息大部分都是AlipayClient提交的参数信息
	 * <form name="punchout_form" method="post" 
	 * 	action="https://openapi.alipaydev.com/gateway.do?charset=UTF-8
	 * 		&method=alipay.trade.page.pay
	 * 		&sign=DB0p9p2Au8Ok3ReC8yWLiXFpZDkNp1lQI%2Bchwt7dZpfifjaPqNkiyr5yBse%2BY21zJeV0MK3YncDU3%2F4nOBZLeAtdP8BlCzgRQiYpYbVdw3rmnAgb57h94sdWGvn8QID6mg5vk%2FBz%2BlxGOMxCs1OII%2FhX%2BoJyDP5kViHlfAP4RA1hUg25qkdvbp3AKWt7w%2FCFDo29mZesE9cWe0ygqjxb09BZlAP9vHotqDUy49TQ3uSBgmIhtpvH91FA1K%2F90G%2BtZwCdZfluHxRaai2rqVEAQgM5MusB%2FNl4o0Idj%2FX7KPYvF%2FcsLybeu7Wr%2Fycn6Esy14TixncPSEc3w9zEhEca3w%3D%3D
	 * 		&return_url=http%3A%2F%2Flocalhost%3A8080%2Falipay%2FreturnInfo
	 * 		&notify_url=http%3A%2F%2Flocalhost%3A8080%2Falipay%2FnotifyInfo
	 * 		&version=1.0
	 * 		&app_id=2016091700532567
	 * 		&sign_type=RSA2
	 * 		&timestamp=2018-09-16+22%3A28%3A25
	 * 		&alipay_sdk=alipay-sdk-java-3.3.49.ALL
	 * 		&format=json">
	 *  这里是提交的参数信息-业务提交数据
	 * <input type="hidden" name="biz_content" value="{
			&quot;out_trade_no&quot;:&quot;1537108105075&quot;,    
			&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;,    
			&quot;total_amount&quot;:0.01,   
			&quot;subject&quot;:&quot;Iphone6 16G&quot;,    
			&quot;body&quot;:&quot;Iphone6 16Gvvvv&quot;  
		}">
	 * <input type="submit" value="立即支付" style="display:none" >
	 * </form>
	 * <script>document.forms[0].submit();</script>
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/page")
	public void page(HttpServletRequest request,HttpServletResponse response) throws IOException {
		AlipayClient alipayClient = new DefaultAlipayClient(URL, APPID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE); //获得初始化的AlipayClient
	    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
	    alipayRequest.setReturnUrl(returnUrl);
	    alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
	    alipayRequest.setBizContent("{" +
	        "    \"out_trade_no\":\""+System.currentTimeMillis()+"\"," +
	        "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
	        "    \"total_amount\":0.01," +
	        "    \"subject\":\"Iphone6 16G\"," +
	        "    \"body\":\"Iphone6 16Gvvvv\"" +
	        "  }");//填充业务参数
	    String form="";
	    try {
	        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
	    } catch (AlipayApiException e) {
	        e.printStackTrace();
	    }
	    System.out.println(form);
	    response.setContentType("text/html;charset=" + CHARSET);
	    response.getWriter().write(form);//直接将完整的表单html输出到页面
	    response.getWriter().flush();
	    response.getWriter().close();
	}
	
	@RequestMapping("returnInfo")
	public void returnInfo(HttpServletRequest request) {
		
		System.out.println("触发回调");
	}
	
	//在用户支付完成之后，支付宝会根据API中商户传入的notify_url，通过POST请求的形式将支付结果作为参数通知到商户系统。
	@RequestMapping("notifyInfo")
	public void notifyInfo(HttpServletRequest request) {
		System.out.println("触发通知");
	}
}
