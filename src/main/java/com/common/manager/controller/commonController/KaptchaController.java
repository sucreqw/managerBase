package com.common.manager.controller.commonController;


import com.common.manager.annotation.RequiredRole;
import com.common.manager.common.RoleConstants;
import com.common.manager.enums.ResultCodeEnum;
import com.common.manager.exception.BizException;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-03-28
 */
@CrossOrigin()
@RequiredRole(RoleConstants.ANONYMOUS)
@RestController
@RequestMapping("/Kaptcha")
public class KaptchaController {
    //在DO里配置一个 配置类，定义验证码的相关信息---> KaptchaConfig
    @Autowired
    Producer captchaProducer;

    /**
     * 取验证码
     * @return
     */
    @GetMapping("/")
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //response.setHeader("Access-Control-Allow-Credentials", "true");
        // create the text for the image
        String capText = captchaProducer.createText();
        //log.info("******************验证码是: " + capText + "******************");
        // store the text in the session
        String uuid=request.getSession().getId();
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        response.setHeader("uuid", uuid);
        response.setHeader("Access-Control-Expose-Headers", "uuid");
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);

        // write the data out
        try(ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(bi, "jpg", out);
            out.flush();
        }catch(Exception e){
            throw new BizException(ResultCodeEnum.FILE_ERROR);
        }
        return null;
    }
    /**
     * 验证 前端提交上来的验证码是否正确
     *
     */
    /*@PostMapping("/")
    public CommonResult<BizException> Kverify(String parameter,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ){
        CommonResult<BizException> ret = new CommonResult<>();
        String captchaId = (String) httpServletRequest.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //String parameter = httpServletRequest.getParameter("vrifyCode");
        System.out.println("Session  vrifyCode "+captchaId+" form vrifyCode "+parameter);

        if (null==captchaId || null==parameter || !captchaId.equals(parameter)) {
            BizException bizException=new BizException(ResultCodeEnum.ERROR);
            ret.setData(bizException);

        } else {
            //登录成功
            BizException bizException=new BizException(ResultCodeEnum.SUCCESS);
            ret.setData(bizException);
        }
        httpServletRequest.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, null);
        return ret;
    }*/
}
