package com.cycas.controller;

import com.cycas.util.GenerateCsv;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private GenerateCsv generateCsv;

    @RequestMapping("/upload")
    public String importOrder(HttpServletRequest request, @RequestParam("file") MultipartFile file){

        generateCsv.uploadBandingTemplate(file);
        return "success";
    }



    @PostMapping("/readBindingFile")
    public String importOrderService(@RequestParam String fileName) {

        String fileUrl = "/Users/admin/Documents/import binding" + File.separator + fileName;
        //读取csv
        logger.info("Begin to importOrder the file: {}", fileUrl);
        CSVReader reader = null;
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileUrl), Charset.forName("UTF-8")));
            reader = new CSVReader(br, ',', '"', '\b', 0);
        } catch (IOException e) {
            logger.error("BufferedReader  error:", e);
        }
        CSVWriter writer = null;
        String newFileName = getNewFileName(fileUrl);
        try {
            BufferedWriter wbr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFileName), Charset.forName("UTF-8")));
            writer = new CSVWriter(wbr, ',', CSVWriter.NO_QUOTE_CHARACTER);
        } catch (IOException e) {
            logger.error("uploadFirstOrder -error", e);
        }

        List<String> res = new ArrayList<>();
        String[] nextRow = null;
        int rowNum = 0;
        String[] header = null;

        while (true) {
            try {
                rowNum++;
                nextRow = reader.readNext();
            } catch (IOException e) {
                logger.error("导入 数据  第{}行读取失败,原因: {}", rowNum, e.getMessage());
                res.add(String.format("导入 数据 第%s行读取失败,原因: %s", rowNum, e.getMessage()));
                break;
            }
            if (nextRow == null) {
                logger.info("read  null row");
                if (rowNum == 2 || rowNum == 1) {

                }
                break;
            }
            int length = nextRow.length;
            if (length < 5) {
                throw new RuntimeException("文件格式错误");
            }
            String[] target = Arrays.copyOf(nextRow, nextRow.length + 1);
            logger.info(String.format("%s--%s--%s--%s", target[0], target[1], target[2], target[3]));
            if (rowNum == 1) {
                logger.info("The head is {}", Arrays.toString(nextRow));
                header = nextRow;
                writer.writeNext(nextRow);
                continue;
            }
            if (rowNum == 2) {
                logger.info("The head is {}", Arrays.toString(nextRow));
                writer.writeNext(nextRow);
                continue;
            }
            writer.writeNext(nextRow);
        }

        try {
            reader.close();
        } catch (Exception e) {
            logger.error("fail to  read file", e);
        }
        try {
            writer.close();
        } catch (Exception e) {
            logger.error("fail to writer file", e);
        }
        return "success";
    }

    private static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    private static String getNewFileName(String fileURL) {
        int dot = fileURL.lastIndexOf(".");
        int name = fileURL.lastIndexOf("/");
        String path = FilenameUtils.getFullPath(fileURL);
        String fileName = FilenameUtils.getName(fileURL);
        fileName = FilenameUtils.removeExtension(fileName);
        String surFix = FilenameUtils.getExtension(fileURL);
        return String.format("%s%s%s.%s", path, fileName, System.currentTimeMillis(), surFix);
    }
}
