package widget;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器后端和前端通讯参数解析
 *
 * @author Zero
 * @since 2016/8/5 9:02
 */
public class Sts {
    // 通用
    public static final Integer PARA_IS_NULL = 0;
    public static final Integer INNER_PARA_IS_NULL = 1;
    private static final String _PARA_IS_NULL = "参数为空";
    private static final String _INNER_PARA_IS_NULL = "内部参数为空";

    // 百位：模块号 十位：方法号（通常0为通用） 个位：状态号 （通常0为成功）
    // UserModel
    public static final Integer USER_REGISTER_SUCCESS = 110;
    public static final Integer USER_PHONE_IS_USED = 111;
    public static final Integer USER_LOGIN_SUCCESS = 120;
    public static final Integer USER_PHONE_OR_PWD_NOT_RIGHT = 121;
    public static final Integer USER_LOGOUT_SUCCESS = 130;
    private static final String _USER_REGISTER_SUCCESS = "用户注册成功";
    private static final String _USER_PHONE_IS_USED = "手机号已被使用";
    private static final String _USER_LOGIN_SUCCESS = "登录成功";
    private static final String _USER_PHONE_OR_PWD_NOT_RIGHT = "用户名或密码错误";
    private static final String _USER_LOGOUT_SUCCESS = "登出成功";

    private static final Map<Integer, String> kv = new HashMap<>();

    static {
        kv.put(PARA_IS_NULL, _PARA_IS_NULL);
        kv.put(INNER_PARA_IS_NULL, _INNER_PARA_IS_NULL);

        kv.put(USER_REGISTER_SUCCESS, _USER_REGISTER_SUCCESS);
        kv.put(USER_PHONE_IS_USED, _USER_PHONE_IS_USED);
        kv.put(USER_LOGIN_SUCCESS, _USER_LOGIN_SUCCESS);
        kv.put(USER_PHONE_OR_PWD_NOT_RIGHT, _USER_PHONE_OR_PWD_NOT_RIGHT);
        kv.put(USER_LOGOUT_SUCCESS, _USER_LOGOUT_SUCCESS);
    }

    public static String get(Integer key) {
        return kv.get(key);
    }
}
