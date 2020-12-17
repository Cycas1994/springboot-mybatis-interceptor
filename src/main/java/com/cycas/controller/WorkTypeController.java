package com.cycas.controller;

import com.cycas.service.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author naxin
 * @Description:
 * @date 2020/12/910:47
 */
@RestController
@RequestMapping("/workType")
public class WorkTypeController {

    @Autowired
    private WorkTypeService workTypeService;


}
