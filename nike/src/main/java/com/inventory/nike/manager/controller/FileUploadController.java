package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/10/27 16:52
 * @Description: TODO
 */

import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.em.ErrorCodeEnum;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Api(
        tags = {"文件上传"}
)
@RestController
@RequestMapping("/file")
public class FileUploadController extends BaseController {

    @Value("${file.uploadFolder}")
    private String uploadFolder;


    @Transactional(rollbackFor = Exception.class)
    @AopOperation(
            type = "文件上传"
    )
    @ApiOperation("文件上传")
    @PostMapping(value = "/upload")
    public ResultVO upload(@RequestBody @RequestParam("pictures") MultipartFile[] pictures) {
        String url = "";
        if(pictures==null){
            throw new BusinessException("上传不能为空");
        }
        // 获取服务器位置下面的路径
        String path = uploadFolder;
        //创建一个io流
        File file = new File(path);

        log.info("文件的保存路径:"+path);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("目录不存在,创建目录:"+file);
            file.mkdirs();
        }
        // 获取原始文件名称
        for (MultipartFile picture:pictures
        ) {
            String Filename = picture.getOriginalFilename();
            log.info("原始文件名称:" + Filename);

            // 获取文件类型,以最后一个"."为标识
            String type = Filename.substring(Filename.lastIndexOf(".") + 1);
            log.info("文件类型:" + type);

            String uuid = UUID.randomUUID().toString();
            String newFilename = uuid + "." + type;

            log.info("新文件名称:" + newFilename);
            //在指定路径创建文件夹
            File targetFile = new File(path, newFilename);
            //将文件保存在服务器指定位置


            try {
                picture.transferTo(targetFile);
                url = url + path + newFilename+";";
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new BusinessException(ErrorCodeEnum.MAX_UPLOAD_SIZE);
            }
        }
        return  this.success(url,"上传成功");
    }
}
