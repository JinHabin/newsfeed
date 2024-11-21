package com.example.newsfeedproject.util;

import com.example.newsfeedproject.exception.InternalServerException;
import jakarta.servlet.http.HttpSession;

import static com.example.newsfeedproject.exception.ErrorCode.SESSION_TIMEOUT;

public class SessionUtil {
    public static String validateSession(HttpSession session) {
        if (session == null || session.getAttribute("email") == null) {
            throw new InternalServerException(SESSION_TIMEOUT);
        }
        return (String) session.getAttribute("email");
    }
}
