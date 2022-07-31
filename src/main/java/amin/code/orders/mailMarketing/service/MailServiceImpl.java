package amin.code.orders.mailMarketing.service;

import amin.code.orders.mailMarketing.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("mailService")
public class MailServiceImpl implements MailService{

    @Autowired
    MailMapper mailMapper;

    @Override
    public int writeBoard(Map<String, Object> paraMap) {
        mailMapper.delBoard(paraMap);
        return mailMapper.writeBoard(paraMap);
    }

    @Override
    public Map<String, Object> getBoard(Map<String, Object> paraMap) {
        return mailMapper.getBoard(paraMap);
    }

    @Override
    public int selectMail(HashMap<String, Object> paraMap) {
        mailMapper.delselectMail(paraMap);
        return mailMapper.selectMail(paraMap);
    }

    @Override
    public Map<String, Object> getSelectMail() {
        return mailMapper.getSelectMail();
    }
}
