package com.atguigu.cloud.response;

import com.atguigu.cloud.enumeration.ReturnCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)//支持链式编程是什么鬼？
public class ResultData<T> {

    private String code;
    private String message;
    private T data;
    private Long timestamp;//调用该返回值时的时间戳

    //通过构造方法填充时间戳
    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data){

        ResultData<T> resultData = new ResultData<>();

        resultData.setCode(ReturnCodeEnum.RC200.getCode());
        resultData.setMessage(ReturnCodeEnum.RC200.getMessage());
        resultData.setData(data);

        return resultData;
    }

    public static <T> ResultData<T> fail(String code, String message){

        //返回失败结果，没有数据响应
        ResultData<T> resultData = new ResultData<>();

        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(null);

        return resultData;
    }

}
