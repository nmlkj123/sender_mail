package amin.code.orders.batch.processor;

import amin.code.orders.email.EmailServiceImpl;
import amin.code.orders.entity.OrdersDTO;
import amin.code.orders.mailMarketing.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.SendFailedException;
import java.util.Map;

@Slf4j
public class OrderItemProcessor implements ItemProcessor<OrdersDTO, OrdersDTO> {

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    MailService mailService;


    @Override
    public OrdersDTO process(OrdersDTO ordersDTO) throws Exception {
        Map<String, Object> selectMail = mailService.getSelectMail();
        selectMail.put("option",selectMail.get("gbn"));
        Map<String, Object> map =mailService.getBoard(selectMail);
        String subject = (String) map.get("subject");
        String content = (String) map.get("content");

        log.debug("processor: {}", ordersDTO);
        try {
            emailService.sendSimpleMessage(ordersDTO.getEmail(), subject, content);
            ordersDTO.setEmailSent(true);
        } catch (SendFailedException sendFailedException) {
            ordersDTO.setEmailSent(true);
            log.debug("error: {}", sendFailedException.getMessage());
        }
        return ordersDTO;
    }
}
