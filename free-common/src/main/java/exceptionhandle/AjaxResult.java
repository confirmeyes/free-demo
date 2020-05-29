package exceptionhandle;

import java.util.HashMap;

/**
 * @author WIN10 .
 * @create 2020-05-15-17:56 .
 * @description .
 */

public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    /**
     * 状态类型
     */
    public enum Type {
        /**
         * 成功
         */
        SUCCESS(1),
        /**
         * 警告
         */
        WARN(2),
        /**
         * 错误
         */
        ERROR(0),
        /**
         * 无权限
         */
        UNAUTH(3),
        /**
         * 未登录、登录超时
         */
        UNLOGIN(4);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 状态类型
     */
    private Type type;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     */
    public AjaxResult(Type type, String msg) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(Type type, String msg, Object data) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        /* 数据为空的时候，还是需要把参数传给前台   huangqr @2019.7.19
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }*/
        super.put(DATA_TAG, data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult warn(String msg) {
        return AjaxResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(Type.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(Type.ERROR, msg, data);
    }

    /**
     * 无权限返回
     *
     * @return
     */
    public static AjaxResult unauth() {
        return new AjaxResult(Type.UNAUTH, "您没有访问权限！", null);
    }

    /**
     * 无权限
     *
     * @param msg
     * @return com.wanda.labor.framework.web.domain.AjaxResult
     * @throws
     */
    public static AjaxResult unauth(String msg) {
        return new AjaxResult(Type.UNAUTH, msg, null);
    }

    /**
     * 未登录或登录超时。请重新登录
     *
     * @param
     * @return com.wanda.labor.framework.web.domain.AjaxResult
     * @throws
     */
    public static AjaxResult unlogin() {
        return new AjaxResult(Type.UNLOGIN, "未登录或登录超时。请重新登录！", null);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static class SUCCESS {

        public static AjaxResult data(Object data) {
            return new AjaxResult(Type.SUCCESS, "操作成功 Operation Successful", data);
        }

        public static AjaxResult iMessagesg(String msg) {
            return new AjaxResult(Type.SUCCESS, msg, null);
        }

        public static AjaxResult imsgAndData(String msg, Object data) {
            return new AjaxResult(Type.SUCCESS, msg, data);
        }
    }
    /*@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("code", getCode())
                .append("msg", getMsg()).append("data", getData()).toString();
    }*/

}
