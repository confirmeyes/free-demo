package utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WIN10 .
 * @create 2020-05-15-17:35 .
 * @description .
 */

public class ServletUtil {

    private static final String AJAX_HEAD = "x-requested-with";
    private static final String AJAX_REQUEST = "XMLHttpRequest";

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajax = request.getHeader(AJAX_HEAD);
        return request.getHeader(AJAX_HEAD) != null && ajax.equalsIgnoreCase(AJAX_REQUEST);
    }
}
