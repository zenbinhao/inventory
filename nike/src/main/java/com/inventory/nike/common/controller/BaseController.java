//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.nike.common.controller;

import com.inventory.nike.common.bo.SessionUser;
import com.inventory.nike.common.em.ErrorCodeEnum;
import com.inventory.nike.common.service.AuthenticationService;
import com.inventory.nike.common.vo.ResultVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    private AuthenticationService authenticationService;

    public BaseController() {
    }

    protected ResultVO success(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setCode(ErrorCodeEnum.SUCCESS.getCode());
        return resultVO;
    }

    protected ResultVO success(Object data, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setData(data);
        resultVO.setCode(ErrorCodeEnum.SUCCESS.getCode());
        return resultVO;
    }
    protected SessionUser getSessionUser() {
        return this.authenticationService.getSessionUser();
    }

    protected String redirect(String path) {
        return "redirect:" + path;
    }}
