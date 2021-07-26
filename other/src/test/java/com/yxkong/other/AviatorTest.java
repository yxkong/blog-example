package com.yxkong.other;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yxkong
 * @Date: 2021/7/24 10:42 上午
 * @version: 1.0
 */
public class AviatorTest {
    @Test
    public void grayRule(){
        Map<String,Object> map = new HashMap<>();
        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        map.put("mobileTail","9");
        map.put("userId",901);
        map.put("registerTime",dateStr);
        map.put("mobile","15600000269");
        map.put("sex",1);
        map.put("age",28);
        String expression = "(string.startsWith('9,0',mobileTail) && userId>=901 && registerTime>'2021-06-25 00:00:00')";
        Boolean flag = (Boolean) AviatorEvaluator.execute(expression, map);
        Assert.assertTrue("规则验证通过",flag);
    }
    @Test
    public void function_in(){
        long num =(Long) AviatorEvaluator.getInstance().execute("math.round(4.3)");
        Assert.assertEquals("4.3四舍五入后", 4L ,num);
        Map<String,Object> map = new HashMap<>();
        map.put("str","yxkong");
        map.put("head","yxk");


        Boolean flag = (Boolean) AviatorEvaluator.getInstance().execute("string.contains(str,head)",map);
        Assert.assertTrue("yxkong包含yxk",flag);
    }

    @Test
    public void function_my(){
        // 注册自定义函数
        AviatorEvaluator.addFunction(new AddFunction());
        long num =(Long) AviatorEvaluator.getInstance().execute("add(3,4)");
        Assert.assertEquals("3+4", 7L,num);
    }

    @Test
    public void script2() throws IOException {
        Expression compiledExp = AviatorEvaluator.getInstance().compileScript("src/test/resources/script.av");
        final Object o = compiledExp.execute(compiledExp.newEnv("age", 12, "name", "yxk"));
        System.out.println(o);
    }
}
class AddFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {

        long num1 = FunctionUtils.getNumberValue(arg1, env).longValue();
        long num2 = FunctionUtils.getNumberValue(arg2, env).longValue();
        return AviatorLong.valueOf(num1+num2);
    }

    @Override
    public String getName() {
        return "add";
    }
}