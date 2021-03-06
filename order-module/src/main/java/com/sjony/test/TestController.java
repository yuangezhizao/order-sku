package com.sjony.test;

import com.sjony.base.BaseController;
import com.sjony.service.SkuQtyService;
import com.sjony.vo.SkuQtyVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("rest/test")
public class TestController extends BaseController {

    @Autowired
    private SkuQtyService skuQtyService;


    /**
     * @Description: 秒杀商品扣库存
     * @Create on: 2017/7/24 下午1:39
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String test(@RequestParam(value = "data") String data) throws IOException {

        /*-----------------------------------------------------------------*
                                入参处理
        *----------------------------------------------------------------*/

        String pathResult = "/Users/sjony/Desktop/study/test/result.txt";

        File file = new File(pathResult);

        //获取所有的sysNo
        List<String> result = FileUtils.readLines(file);

        /*-----------------------------------------------------------------*
                                逻辑处理
        *----------------------------------------------------------------*/
        Long t1 = System.currentTimeMillis();
        List<SkuQtyVO> result1 = skuQtyService.getSkuQtyListBySkuList(result, false);
        Long t2 = System.currentTimeMillis();
        System.out.println("耗时：" + (t2-t1) + "ms");

        Long t3 = System.currentTimeMillis();
        for(String s : result) {
            skuQtyService.getSkuQtyBySkuCode(s, false);
        }
        Long t4 = System.currentTimeMillis();
        System.out.println("耗时：" + (t4-t3) + "ms");

        /*-----------------------------------------------------------------*
                                数据返回
        *----------------------------------------------------------------*/
        return success(result1);

    }


    //工具测试
    public static void main(String[] args) throws IOException {
        /*String sm = FileUtils.readFileToString(new File("/Users/sjony/Downloads/test1.txt"));
        String big = FileUtils.readFileToString(new File("/Users/sjony/Downloads/test2.txt"));
        List<String> bigList = Arrays.asList(big.split(","));
        List<String> smList = Arrays.asList(sm.split(","));
        Inclusive(bigList, smList);*/
        /*SimpleDateFormat sdfShelfLife = new SimpleDateFormat("yyyy-MM-dd");

        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+2);
        Date date=curr.getTime();
        System.out.print(sdfShelfLife.format(date));*/

        String s = "12345689t876543";
        String pattern = "[\\d]{15}";

        boolean isNum = Pattern.matches(pattern, s);
        System.out.println(isNum);

    }

    /**
     * @Description: 找到包含外的数据
     * @Create on: 2017/11/17 上午10:44 
     *
     * @author shujiangcheng
     */
    private static void Inclusive(List<String> bigList, List<String> smList) {
        for(String bigString : bigList) {
            if(!smList.contains(bigString)) {
                System.out.println(bigString);
            }
        }
    }

}
