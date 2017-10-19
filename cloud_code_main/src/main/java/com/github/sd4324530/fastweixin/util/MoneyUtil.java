package com.github.sd4324530.fastweixin.util;

import javax.tools.JavaFileManager;
//x.beans.property.DoublePropertyBase;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/4/25.
 */

//����Ǯ���Է�Ϊ��λ��ת������ԪΪ��λ
public class MoneyUtil {
    public static double changeMoney(int money){
        return Double.valueOf(BigDecimal.valueOf(Long.valueOf(money)).divide(new BigDecimal(100)).toString());
    }
}
