package com.example.demo.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.util.CheckURL;
import com.example.demo.util.Payment;
import com.example.demo.util.UtilHttp;

@Controller
@RequestMapping("alipay/wap/")
public class AlipayWapController {
	private static final String partner = "2088311111593203";
	private static final String key = "5x5sazg0wq3zbd6s6a7yj1mnd6jfz4ji";
	/**
	 * 支付宝付款
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("pay")
	public static String callAlipay(HttpServletRequest request, HttpServletResponse response) {
	        // get the orderId
	        String orderId = System.currentTimeMillis()+"";//request.getParameter("serviceOrderId");
	        String orderTotal = "0.01";
	        String paygateway = "https://www.alipay.com/cooperate/gateway.do?";
	        String service = "create_direct_pay_by_user";//  trade_create_by_buyer
	        String sign_type = "MD5";
	        String out_trade_no = orderId;
	        String input_charset = "utf-8";
	        String seller_email = "laichushu@cnipr.com";
	        String body = orderId; //
	        String subject = orderId;
	        String quantity = "1";
	        String show_url = "show_url";
	        String payment_type = "1";
	        String discount = "0";
	        String logistics_type = "EMS";
	        String logistics_fee = "0.00";
	        String logistics_payment = "SELLER_PAY";
	        String url_header = request.getRequestURL().toString().replace(request.getRequestURI(), "");
	        String return_url = url_header + "alipay/wap/notify"; //回调状态URL(需要修改)
	        //前面字符串
	        String ItemUrl = Payment.CreateUrl(paygateway, service, sign_type,
	                out_trade_no, input_charset, partner, key, seller_email, body,
	                subject, orderTotal, quantity, show_url, payment_type, discount,
	                logistics_type, logistics_fee, logistics_payment, return_url);
	        // ----------------------------

	        Map<String,Object> parameters = new LinkedHashMap<>();
	        parameters.put("_input_charset", "utf-8");
	        parameters.put("body", body);
	        parameters.put("logistics_type", logistics_type);
	        parameters.put("logistics_fee", logistics_fee);
	        parameters.put("logistics_payment", logistics_payment);
	        parameters.put("out_trade_no", out_trade_no);
	        parameters.put("partner", partner);
	        parameters.put("payment_type", payment_type);
	        parameters.put("seller_email", seller_email);
	        parameters.put("service", service);
	        parameters.put("sign", ItemUrl);
	        parameters.put("sign_type", "MD5");
	        parameters.put("subject", subject);
	        parameters.put("price", orderTotal);
	        parameters.put("quantity", quantity);
	        parameters.put("discount", discount);
	        parameters.put("show_url", show_url);
	        parameters.put("return_url", return_url);

	        String encodedParameters = UtilHttp.urlEncodeArgs(parameters, false);
	        String redirectString = paygateway + encodedParameters;

	        // set the order in the session for cancelled orders
	        request.getSession().setAttribute("ALIPAY_ORDER", orderId);

	        // redirect to alipay
	        try {
	            response.sendRedirect(redirectString);
	        } catch (IOException e) {
	            request.setAttribute("errMsg","Problems connecting with Alipay, please contact customer service.");
	            return "error";
	        }

	        return "success";
	    }
	
	/**
	 * 支付宝回调通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("notify")
	@ResponseBody
    public String notify(HttpServletRequest request,HttpServletResponse response) {

        // get the user
        String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
                + "partner="
                + partner
                + "&notify_id="
                + request.getParameter("notify_id");
        //这里没有做签名的校验工作
		//String sign = request.getParameter("sign");
        
        //
        String responseTxt = CheckURL.check(alipayNotifyURL);
        String orderId = request.getParameter("out_trade_no");
        
        boolean okay = true;
        
        //支付处理
        if (!responseTxt.equals("true")) {//支付失败
            request.setAttribute("pay_result", "支付失败");
            okay=true;
        } else {//支付成功
            request.setAttribute("pay_result", "支付成功");
            okay = paymentSuccess(request, orderId);
        }
        
        if(!okay)
        	request.setAttribute("errMsg", "支付成功,订单异常.");
        
        return okay?"success":"error";
    }
	
	/**
	 * 支付成功
	 * @author liushangyang 2013-6-27
	 * @param delegator
	 * @param serviceOrderId
	 */
	public static String paymentSuccess(HttpServletRequest request, HttpServletResponse response){
		String serviceOrderId = request.getParameter("serviceOrderId");
		if(paymentSuccess(request,serviceOrderId)){
			return "success";
		}else{
			return "error";
		}
	}
	/**
	 * 支付成功触发相应事件
	 * @author liushangyang 2013-10-25
	 * @param delegator
	 * @param serviceOrderId
	 */
	public static boolean paymentSuccess(HttpServletRequest request,String serviceOrderId){
		//return ServiceEvents.paymentSuccess(request, serviceOrderId);
		return true;
	}
}
