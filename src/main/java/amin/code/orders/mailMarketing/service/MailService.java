package amin.code.orders.mailMarketing.service;

import java.util.HashMap;
import java.util.Map;

public interface MailService {
    int writeBoard(Map<String, Object> paraMap);
    Map<String, Object> getBoard(Map<String, Object> paraMap);

    int selectMail(HashMap<String, Object> paraMap);
    Map<String, Object> getSelectMail();
}
