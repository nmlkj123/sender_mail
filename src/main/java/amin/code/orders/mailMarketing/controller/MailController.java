package amin.code.orders.mailMarketing.controller;


import amin.code.orders.mailMarketing.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class MailController {

    @Autowired
    MailService mailService;

    @GetMapping("/main")
    public String main(Model model) {
        Map<String,Object> map =mailService.getSelectMail();
        String option = (String) map.get("gbn");
        model.addAttribute("selectMail",option);
        return "main";
    }
    @RequestMapping(value="/mail/contentImage",method= RequestMethod.POST)
    @ResponseBody
    public String contentImage(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");

        // 업로드할 폴더 경로
        String realFolder = request.getSession().getServletContext().getRealPath("\\images\\reviewImage");
        UUID uuid = UUID.randomUUID();

        // 업로드할 파일 이름
        String org_filename = file.getOriginalFilename();
        String str_filename = uuid.toString() + org_filename;

        System.out.println("원본 파일명 : " + org_filename);
        System.out.println("저장할 파일명 : " + str_filename);

        String filepath = realFolder +"\\" + str_filename;
        System.out.println("파일경로 : " + filepath);

        File f = new File(filepath);
        if (!f.exists()) {
            f.mkdirs();
        }
        file.transferTo(f);
        return new String(  str_filename);
    }
    @RequestMapping(value="/mail/saveForm",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> writeForm(@RequestParam HashMap<String, Object> paraMap, HttpServletResponse response) {
        System.out.println(paraMap.get("option"));
        mailService.writeBoard(paraMap);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("success","저장되었습니다");
        return map;
    }
    @RequestMapping(value="/mail/getBoard",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getBoard(@RequestParam HashMap<String, Object> paraMap, HttpServletResponse response) {
        System.out.println(paraMap.get("option"));
        Map<String, Object> map = mailService.getBoard(paraMap);
        return map;
    }
    @RequestMapping(value="/mail/selectMail",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectMail(@RequestParam HashMap<String, Object> paraMap, HttpServletResponse response) {
        System.out.println(paraMap.get("option"));
        mailService.selectMail(paraMap);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("success","저장되었습니다");
        return map;
    }
}
