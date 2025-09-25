package com.example.test_01.Controller;

import com.example.test_01.DTO.Test02DTO;
import com.example.test_01.DTO.TestDTO;
import com.example.test_01.Entity.Test02Entity;
import com.example.test_01.Entity.TestEntity;
import com.example.test_01.Service.Test02Service;
import com.example.test_01.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    TestService service;
    String path = "C:\\◎study\\01. spring boot\\study\\src\\main\\resources\\static\\image";

    @Autowired
    private final Test02Service service02;

    public MainController(Test02Service service02) {
        this.service02 = service02;
    }

    @GetMapping(value = "/")
    public String main(){
        return "main";
    }

    @GetMapping(value = "/input")
    public String input01(TestDTO testDTO, Model mo){
        mo.addAttribute("testDTO", testDTO);
        return "testinput";
    }

    @PostMapping(value = "/save")
    public String save01(TestDTO dto) {
        service.save(dto);
        return "redirect:/";
    }

    @GetMapping(value = "/output")
    public String output01(Model mo){
        List<TestEntity> list = service.out();
        mo.addAttribute("list", list);
        return "testoutput";
    }

    @GetMapping(value = "/update")
    public String update01(@RequestParam("num") long num, Model mo) {
        TestEntity entity = service.update(num);
        mo.addAttribute("update", entity);

        return "testupdate";
    }

    @PostMapping(value = "/update_save")
    public String update01_2(@ModelAttribute("update") TestEntity entity) {
        service.update_save(entity);

        return "redirect:/output";
    }

    @GetMapping(value = "/delete")
    public String delete01(@RequestParam("num") long num, Model mo) {
        service.delete(num);

        return "redirect:/output";
    }

    // -------------------------------------------------------------------
//입력창 이동
    @GetMapping(value = "/input02")
    public String input02(Test02DTO dto, Model model) {
        model.addAttribute("dto", dto);
        return "test02input";
    }
//저장
    @PostMapping(value = "/save02")
    public String save02(Test02DTO dto, MultipartHttpServletRequest mul) throws IOException {

        MultipartFile mf = mul.getFile("image");

        String filename = mf.getOriginalFilename();

        UUID uu = UUID.randomUUID();
        String fname = uu.toString() + "-" + filename;

        dto.setTestimage(fname);

        mf.transferTo(new File(path + "\\" + fname));

        service02.saveBoard(dto);

        return "redirect:/";
    }

    @GetMapping("/output02")
    public String output02(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        List<Test02Entity> list = service02.outBoard(page, size);
        int totalPages = service02.getTotalPages(size);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "test02output";
    }


    @GetMapping(value = "update2")
    public String update02_1(@RequestParam("num") long num, Model model) {
        Test02Entity entity= service02.update02(num);
        model.addAttribute("update", entity);

        return "test02update";
    }

    @PostMapping(value = "update_save02") //GPT가 해준 부분
    public String update02_2(
            @RequestParam("id") long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("writer") String writer,
            @RequestParam("inputdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inputdate,
            @RequestParam(value = "testimage", required = false) MultipartFile mf
    ) throws IOException {

        Test02Entity entity = service02.update02(id);

        entity.setTitle(title);
        entity.setContent(content);
        entity.setWriter(writer);
        entity.setInputdate(inputdate);

        if (mf != null && !mf.isEmpty()) {
            String filename = UUID.randomUUID() + "-" + mf.getOriginalFilename();
            mf.transferTo(new File(path + "\\" + filename));
            entity.setTestimage(filename);
        } // 파일 없으면 기존 이미지 유지

        service02.update_save2(entity);

        return "redirect:/output02";
    }


    @GetMapping("delete2")
    public String delete02(@RequestParam("num") long num,
                           @RequestParam("testimage") String testimage) {
        service02.delete02(num);
        File fname = new File(path + "\\" + testimage);
        fname.delete();
        return "redirect:/output02";
    }

    @GetMapping("detail02")
    public String detial02(@RequestParam("num") long num,
                           Model model) {
        Test02Entity entity = service02.detail02(num);
        model.addAttribute("detail", entity);

        return "test02detail";
    }

    @GetMapping(value = "/test02search")
    public String search02(@RequestParam("testsearch") String testsearch,
                           Model mo){
        List<Test02Entity> list = service02.titleSearch(testsearch);
        mo.addAttribute("list", list);
        mo.addAttribute("totalPages", 1);
        mo.addAttribute("currentPage", 1);

        return "test02output";
    }
}
