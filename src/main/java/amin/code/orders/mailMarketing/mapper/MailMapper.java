package amin.code.orders.mailMarketing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Component
public interface MailMapper {
    int writeBoard(Map<String, Object> paraMap);
    int delBoard(Map<String, Object> paraMap);
    Map<String, Object> getBoard(Map<String, Object> paraMap);
    int delselectMail(Map<String, Object> paraMap);
    int selectMail(Map<String, Object> paraMap);
    Map<String, Object> getSelectMail();

}
