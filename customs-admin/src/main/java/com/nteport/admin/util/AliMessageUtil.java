package com.nteport.admin.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * Created by yuxl on 2019/9/25.
 * 本类参考message短信平台项目 AliMessageUtil.java进行一定扩展
 */
public class AliMessageUtil {
	public static String SOURCE_CODE="SMS_234030581";//老门户系统 用户注册短信验证模板
    public static AliMessageResult sendMsg(String phone,String source_code,String jsonParam){
    	AliMessageResult result=null;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FkL3L1358sqW34uLXhp", "8UnDPhIzUAp6e9rwgAfkV6x91JesqG");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "南通电子口岸");
        request.putQueryParameter("TemplateCode", source_code);
        request.putQueryParameter("TemplateParam", jsonParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            //阿里云短信接口回执   {"RequestId":"CA309717-6A79-5228-AD71-DBBDF8444107","Message":"OK","BizId":"280906043091714120^0","Code":"OK"}
//            Gson gson=new Gson();
//            result=gson.fromJson(response.getData(), AliMessageResult.class);
            result=JSON.parseObject(response.getData(), AliMessageResult.class);
        } catch (ServerException e) {
            e.printStackTrace();
            result=new AliMessageResult();
            result.setMessage("服务端异常");
            result.setCode("NO");
        } catch (ClientException e) {
            e.printStackTrace();
            result=new AliMessageResult();
            result.setMessage("客户端异常");
            result.setCode("NO");
        }
        return result;
    }

    public static void main(String[] args){
        sendMsg("18862923918","SMS_174275440","{'code':'88888888'}");//叠石桥市场采购模板短信
    }
}
