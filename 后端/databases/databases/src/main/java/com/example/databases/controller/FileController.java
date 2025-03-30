package com.example.databases.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.example.databases.Common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author:Iscream
 * @brief:文件流的上传和下载，用IO流实现
 * @way:个人中心头像上传
 */
@RestController
@RequestMapping("/file")
public class FileController {
    //根目录
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";  // D:\project3\databases\files
    //文件的上传
    @PostMapping("/upload")
    @CrossOrigin
    public Result upload( MultipartFile file) throws IOException {
        //获得文件的原始名字
        String originalFilename= file.getOriginalFilename();
        //前缀名
        String mainName=FileUtil.mainName(originalFilename);
        //后缀名
        String extName=FileUtil.extName(originalFilename);
        //如果当前文件的父级路径，就创建
        if(!FileUtil.exist(ROOT_PATH))
        {
            FileUtil.mkdir(ROOT_PATH);
        }


        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://localhost:80/file/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }
    @PostMapping("/del")
    public boolean delfile(@RequestBody String path)
    {

        String []a=path.split("/");
        if(a.length==1)
            return false;
        String path1=a[a.length-1];
        String path2="";
        for(int i=0;i<path1.length()-1;i++)
            path2+=path1.charAt(i);
        String filePath=ROOT_PATH+File.separator+path2;
        System.out.println(filePath);
        if (!FileUtil.exist(filePath)) {
            return false;
        }
        File f=new File(filePath);
        boolean fl=f.delete();
        return fl;
    }
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载

        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        String filePath = ROOT_PATH  + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        response.setContentLength(bytes.length);
        response.addHeader("Accept-Ranges","bytes");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @author:Iscream
     * @brief:富文本上传图片专用接口
     */
    @PostMapping("/editor/upload")
    @CrossOrigin
    public Dict editorUpload(MultipartFile file) throws IOException {
        //获得文件的原始名字
        String originalFilename=file.getOriginalFilename();
        //前缀名
        String mainName=FileUtil.mainName(originalFilename);
        //后缀名
        String extName=FileUtil.extName(originalFilename);
        //如果当前文件的父级路径，就创建
        if(!FileUtil.exist(ROOT_PATH))
        {
            FileUtil.mkdir(ROOT_PATH);
        }


        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://localhost:80/file/download/" + originalFilename;
        return Dict.create().set("errno",0).set("data", CollUtil.newArrayList(Dict.create().set("url",url)));
    }
    /**
     * @author:Iscream
     * @brief:富文本上传视频专用接口
     */
    @PostMapping("/editor/videoupload")
    @CrossOrigin
    public Dict editorUploadV(MultipartFile file) throws IOException {
        //获得文件的原始名字
        String originalFilename=file.getOriginalFilename();
        //前缀名
        String mainName=FileUtil.mainName(originalFilename);
        //后缀名
        String extName=FileUtil.extName(originalFilename);
        //如果当前文件的父级路径，就创建
        if(!FileUtil.exist(ROOT_PATH))
        {
            FileUtil.mkdir(ROOT_PATH);
        }


        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://localhost:80/file/download/" + originalFilename;
        return Dict.create().set("errno",0).set("data", Dict.create().set("url",url));

    }
}
