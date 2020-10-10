package com.joh.esms.controller;

import com.joh.esms.dao.ListDAO;
import com.joh.esms.dao.ListPhotoDAO;
import com.joh.esms.dao.ProductDAO;
import com.joh.esms.dao.SettingDAO;
import com.joh.esms.model.*;
import com.joh.esms.service.AccountService;
import com.joh.esms.service.AccountTransactionService;
import org.apache.commons.fileupload.FileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller()
@RequestMapping(path = "/store")
public class OnlineStorController {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ListDAO listDAO;
    @Autowired
    private ProductDAO prodao;
    @Autowired
    private ListPhotoDAO listPhotoDAO;
    @Autowired
    private SettingDAO settingDAO;
    private static final Logger logger = Logger.getLogger(OnlineStorController.class);


    @GetMapping()
    private String OnlineStore(Model model) {

        return "store";
    }

    @GetMapping(value = "/setting")
    private String setting(Model model) {


        if (!settingDAO.exists(1)) {
            Setting setting = new Setting();
            settingDAO.save(setting);
        }
        model.addAttribute("Setting", settingDAO.findByid(1));
        return "setting";
    }

    @PostMapping(value = "/setting")
    private String update(@RequestParam(name = "file", required = false) MultipartFile file, Model model, HttpServletRequest request, @ModelAttribute("setting") Setting setting) {
        setting.setId(1);
        System.out.println(request.getSession().getServletContext().getRealPath("resource/img"));
        try {
            file.transferTo(resourceLoader.getResource("resources/img/Store-logo.png").getFile());
        } catch (IOException e) {
            System.out.println(e.getMessage().toString());
        }
        settingDAO.save(setting);
        model.addAttribute("Setting", setting);
        return "setting";
    }

    @GetMapping(value = "/list")
    private String list(Model model) {

        model.addAttribute("lists", listDAO.findAll());
        return "list";
    }

    @GetMapping(value = "/list/add")
    private String add(Model model) {

        Iterable<Product> product = prodao.findAll();
        model.addAttribute("product", product);
        model.addAttribute("list", new store_list());
        return "item-add";
    }

    @GetMapping(value = "/list/edit/{id}")
    private String edit(Model model,@PathVariable int id) {


        store_list list=listDAO.findOne(id);
        model.addAttribute("list", list);
        return "item-edit";
    }

    @PostMapping(value = "/list/add")
    private String doAdd(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile[] files, @ModelAttribute("list") store_list list, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
        }

        list.setProduct(prodao.findOne(Integer.valueOf(request.getParameter("product"))));
        list.setActive(1);
        List_photos listPhotos = new List_photos();

        String Message="List SuccessFully Published";
        if (listDAO.exists(listDAO.save(list).getId())){
            for (int i = 0; i < files.length; i++) {

                if (!files[i].isEmpty())
                    try {


                        listPhotos = new List_photos();
                        String path = request.getSession().getServletContext().getRealPath("resources/img") + "/list/" + list.getId() + "_" + i + ".png";
                        listPhotos.setList(list);
                        listPhotos.setPath(list.getId() + "_" + i + ".png");

                        if (i == 0)
                            listPhotos.setMain(1);

                        File targetFile = new File(path);
                        if (!targetFile.exists()) {
                            targetFile.createNewFile();
                        }
                        files[i].transferTo(resourceLoader.getResource("resources/img/list/" + list.getId() + "_" + i + ".png").getFile());
                        listPhotoDAO.save(listPhotos);
                    } catch (IOException e) {
                        System.out.println(e.getMessage().toString());
                    }
            }
              }
        else{

            Message="an Error Happened !";
        }
        System.out.println(listDAO.findOne(2).getPhotos().size());
        model.addAttribute("message",Message);
        Iterable<Product> product = prodao.findAll();
        model.addAttribute("product", product);
        model.addAttribute("list", new store_list());
        return "item-add";
    }
}
