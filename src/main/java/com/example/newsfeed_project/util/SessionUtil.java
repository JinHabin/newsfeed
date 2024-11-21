package com.example.newsfeed_project.util;

import static com.example.newsfeed_project.exception.ErrorCode.SESSION_TIMEOUT;

import com.example.newsfeed_project.exception.CustomMessageOfException;
import com.example.newsfeed_project.exception.InternalServerException;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static String validateSession(HttpSession session) {
        if (session == null || session.getAttribute("email") == null) {
            throw new InternalServerException(SESSION_TIMEOUT);
//            throw new IllegalArgumentException("세션이 만료되었습니다. 다시 로그인 해주세요.");
        }
        return (String) session.getAttribute("email");
    }
}
