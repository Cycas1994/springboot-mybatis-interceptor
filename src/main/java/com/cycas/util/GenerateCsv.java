package com.cycas.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GenerateCsv {

    private static final Logger logger = LoggerFactory.getLogger(GenerateCsv.class);


    public static void generateCsvFile() {

//        String filePth = "/mantis/static/binding/";
        String filePth = "/Users/admin/Documents/binding/";
        String fileName = "binding_3401.csv";

        File csvFile = null;
        BufferedWriter csvWriter = null;

        try {
            csvFile = new File(filePth + File.separator + fileName);
            File parent = csvFile.getParentFile();
            if (Objects.nonNull(parent) && !parent.exists()) {
                parent.mkdirs();
            }
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"));
            String[] headArr = new String[]{"企业ID（corpId）", "姓名（企业微信）", "企业微信用户ID", "螳螂账号（姓名）", "账号"};
            csvWriter.write(String.join(",", headArr));
            csvWriter.newLine();
            csvWriter.flush();
        } catch (Exception e) {
            logger.error("����csv�ļ�ʧ��:", e);
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                logger.error("�ļ��ر�ʧ�ܣ�");
            }
        }
    }

    public void uploadBandingTemplate(MultipartFile file) {

        String uploadPath = "/Users/admin/Documents/IMPORT_BANDING_USER_SERVICE/" + file.getOriginalFilename();
        try {
            FileUtils.writeByteArrayToFile(new File(uploadPath), file.getBytes());
        } catch (IOException e) {
            logger.error("失败", e);
        }

    }

    public static String importOrderService(String fileName) {

        String fileUrl = "/Users/admin/Documents/IMPORT_BANDING_USER_SERVICE/" + File.separator + fileName;

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
        String newFileName = "/Users/admin/Documents/binding/binding_3401_副本.csv";
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
                logger.error("失败: {}", rowNum, e.getMessage());
                res.add(String.format("%s %s", rowNum, e.getMessage()));
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
                throw new RuntimeException("失败");
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
//                writer.writeNext(nextRow);
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

    /**
     * 得到文件的编码
     * @param filePath 文件路径
     * @return 文件的编码
     */
    public static String getJavaEncode(String filePath){
        BytesEncodingDetect s = new BytesEncodingDetect();
        String fileCode = BytesEncodingDetect.javaname[s.detectEncoding(new File(filePath))];
        return fileCode;
    }

    public static void main(String[] args) {

        HttpURLConnection conn = null;
        try {
            String pictureUrl = "https://probe.bjmantis.net/hcFile/2021/05/3401/png/下载_1620891889046.png";
            String originalFilename = pictureUrl.substring(pictureUrl.lastIndexOf("/") + 1);
            logger.info("originalFilename:{}", originalFilename);
            String fileName = originalFilename.substring(0,originalFilename.lastIndexOf("."))+"_"+System.currentTimeMillis();
            String suffix=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            if (StringUtils.isBlank(suffix)){
                throw new RuntimeException("该文件后缀异常!");
            }
            if (StringUtils.isBlank(suffix)) {
                throw new RuntimeException("图片格式不正确!");
            }
            URL url = new URL(pictureUrl);
            // 打开链接
            conn = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            conn.setRequestMethod("GET");
            // 响应超时时间
            conn.setConnectTimeout(5 * 1000);
            // 获取输入流
            InputStream inputStream = conn.getInputStream();
            // 大小校验
            int fileLength = inputStream.available();
            logger.info("fileLength:{}", fileLength);
            if (fileLength > 5 * 1024 * 2014) {
                throw new RuntimeException("文件大小超过5M!");
            }
            // 图片字节数据
            byte[] buffer = readAndCloseInputStream(inputStream);
            // 保存
            String filePath = "/Users/admin/Documents/";
            File imageFile = new File(filePath + fileName + "." + suffix);
            FileUtils.writeByteArrayToFile(imageFile, buffer);
            logger.info("文件下载完成:{}", pictureUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    private static byte[] readAndCloseInputStream(InputStream inputStream) {

        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(inputStream);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (IOException e) {
            logger.error("readInputStream error!", e);
            throw new RuntimeException("");
        } finally {
            if (Objects.nonNull(in)) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("文件输入流关闭失败！");
                }
            }
            if (Objects.nonNull(out)) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("文件输出流关闭失败！");
                }
            }

        }

    }
}
