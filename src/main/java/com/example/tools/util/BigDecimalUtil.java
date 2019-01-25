package com.example.tools.util;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.BiFunction;

public class BigDecimalUtil {

    /**
     * @author: tiankuokuo
     * @description: bigDecimal null 转 0
     * @date: 2019/1/24 9:49
     * @return:
     * @throws:
     */
    public static BigDecimal bigDecimalToZero(BigDecimal bigDecimal) {
        if (Objects.isNull(bigDecimal)) {
            return new BigDecimal(0);
        }
        return bigDecimal;
    }

    /**
     * @author: tiankuokuo
     * @description: BigDecimal 操作  测试BiFunction<BigDecimal, BigDecimal, BigDecimal>
     * @date: 2019/1/24 13:59
     * @return:
     * @throws:
     */
    public static BigDecimal operattion(BiFunction<BigDecimal, BigDecimal, BigDecimal> function,
                                        BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return function.apply(getBigDecimalNotNull(bigDecimal), getBigDecimalNotNull(bigDecimal2));
    }

    /**
     * @author: tiankuokuo
     * @description: bigDecimal 空转为0
     * @date: 2019/1/24 14:01
     * @return:
     * @throws:
     */
    private static BigDecimal getBigDecimalNotNull(BigDecimal bigDecimal) {
        if (Objects.isNull(bigDecimal)) {
            bigDecimal = new BigDecimal(0);
        }
        return bigDecimal;
    }
}
