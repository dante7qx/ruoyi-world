package com.risun.report.vo;

import java.io.Serializable;

import com.risun.common.constant.HttpStatus;

import cn.hutool.core.date.DateUtil;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
public class RP<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /** 失败 */
    public static final int FAIL = HttpStatus.ERROR;
    
    private Boolean success;

    private int code;

    private String message;

    private T result;
    
    private Long timestamp;

    public static <T> RP<T> ok()
    {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> RP<T> ok(T data)
    {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> RP<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> RP<T> fail()
    {
        return restResult(null, FAIL, "操作失败");
    }

    public static <T> RP<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> RP<T> fail(T data)
    {
        return restResult(data, FAIL, "操作失败");
    }

    public static <T> RP<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> RP<T> fail(int code, String msg)
    {
        return restResult(null, code, msg);
    }

    private static <T> RP<T> restResult(T data, int code, String msg)
    {
        RP<T> apiResult = new RP<>();
        apiResult.setSuccess(HttpStatus.SUCCESS == code ? Boolean.TRUE : Boolean.FALSE);
        apiResult.setCode(code);
        apiResult.setResult(data);
        apiResult.setMessage(msg);
        apiResult.setTimestamp(DateUtil.current());
        return apiResult;
    }
    
    public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public static <T> Boolean isError(RP<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(RP<T> ret)
    {
        return RP.SUCCESS == ret.getCode();
    }
}
