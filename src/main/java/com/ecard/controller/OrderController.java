package com.ecard.controller;

import com.ecard.pojo.Response;
import com.ecard.service.OrderService;
import com.ecard.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@CrossOrigin
@Api(value = "订单相关接口" )
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //文件上传
    @RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
    public String uploadFolder(MultipartFile[] folder) {
        FileUtil.saveMultiFile("D:/upload", folder);
        return "ok";
    }

    //文件下载
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public String downloadFile(String orderNo) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        System.out.println(orderNo);
        FileUtil.downloadFile(response, orderNo);
        return "ok";
    }


    //实现Spring Boot 的文件下载功能，映射网址为/download
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

        // 获取指定目录下的第一个文件
        //File scFileDir = new File("E://music_eg");
        //File TrxFiles[] = scFileDir.listFiles();
        //System.out.println(TrxFiles[0]);
        //String fileName = TrxFiles[0].getName(); //下载的文件名

        String fileName = "1234.txt"; //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://upload/";
            File file = new File(realPath, fileName);

            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the song successfully!");
                }
                catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    //添加图片
    @ApiOperation(value="新增图片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "img", value = "图片", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "orderNo", value = "订单号编号（有订单号时必传，编辑时必传，没有时不传）", dataType = "String")
    })
    @PostMapping("/addImg")
    public Response addImg(
            @RequestParam(value = "img") String img,
            @RequestParam(value = "orderNo" ,required = false) String orderNo) {
        Response response = orderService.addImg(img,orderNo);
        return response;
    }

    //添加订单
    @ApiOperation(value="添加订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "病人姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "病人年龄", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "病人电话", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "status", value = "订单状态", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "orderNo", value = "订单号编号（有订单号时必传，编辑时必传，没有时不传）", dataType = "String")
    })
    @PostMapping("/insert")
    public Response insert(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "age") int age,
            @RequestParam(value = "idCard") String idCard,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "status") int status,
            @RequestParam(value = "orderNo" ,required = false) String orderNo) {
        Response response = orderService.insert(name,age,idCard,phone,status,orderNo);
        return response;
    }


    //修改订单
    @ApiOperation(value="修改订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "病人姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "病人年龄", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "病人电话", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "status", value = "订单状态", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "orderNo", value = "订单号编号（有订单号时必传，编辑时必传，没有时不传）", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "remark", value = "备注", dataType = "String")
    })
    @PostMapping("/update")
    public Response update(
            @RequestParam(value = "name", required=false) String name,
            @RequestParam(value = "age", required=false) Integer age,
            @RequestParam(value = "idCard", required=false) String idCard,
            @RequestParam(value = "phone", required=false) String phone,
            @RequestParam(value = "status", required=false) Integer status,
            @RequestParam(value = "orderNo") String orderNo,
            @RequestParam(value = "remark", required=false) String remark) {
        Response response = orderService.update(name,age,idCard,phone,status,orderNo,remark);
        return response;
    }


    //查询订单列表
    @ApiOperation(value="查询订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "病人姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "病人年龄", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "病人电话", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "status", value = "订单状态", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "orderNo", value = "订单号编号（有订单号时必传，编辑时必传，没有时不传）", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "rows", value = "显示条数", dataType = "int")
    })
    @PostMapping("/select")
    public Response select(
            @RequestParam(value = "name" ,required = false) String name,
            @RequestParam(value = "age" ,required = false) int age,
            @RequestParam(value = "idCard" ,required = false) String idCard,
            @RequestParam(value = "phone" ,required = false) String phone,
            @RequestParam(value = "status" ,required = false) int status,
            @RequestParam(value = "orderNo" ,required = false) String orderNo,
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            @RequestParam(value="rows", required=false, defaultValue="10") int rows) {
        Response response = orderService.select(name,age,idCard,phone,status,orderNo,page,rows);
        return response;
    }

}
