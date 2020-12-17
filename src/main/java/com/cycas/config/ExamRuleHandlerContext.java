package com.cycas.config;


import com.cycas.annotation.ExamRuleHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExamRuleHandlerContext implements ApplicationContextAware {

    private static final Map<String,Class> handlerMap = new HashMap<>(10);

//    public AbstractExamRuleHandler getHandlerInstance(String typeCode){
//        Class clazz = handlerMap.get(typeCode);
//        if (clazz == null){
//             clazz = handlerMap.get(ExamConstants.HC_EXAM_TYPE_CUSTOMIZE);
//        }
//        return (AbstractExamRuleHandler) applicationContext.getBean(clazz);
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,Object> beans = applicationContext.getBeansWithAnnotation(ExamRuleHandler.class);
        if (beans != null && beans.size() > 0) {
            for (Object serviceBean : beans.values()) {
                String payType = serviceBean.getClass().getAnnotation(ExamRuleHandler.class).value();
                handlerMap.put(payType, serviceBean.getClass());
            }
        }
    }
}