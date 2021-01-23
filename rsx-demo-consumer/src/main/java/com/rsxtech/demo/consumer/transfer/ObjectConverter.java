package com.rsxtech.demo.consumer.transfer;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConverter {

    private static final Map<String, Method> converters = new HashMap<>();

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        Method[] methods = ObjectConverter.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("convertFrom")) {
                converters.put(method.getName(), method);
            }
        }
    }

    public static <T> T convert(Object from, Class<T> toClass) {
        if (from == null) {
            return null;
        }

        if (toClass.isAssignableFrom(from.getClass())) {
            return toClass.cast(from);
        }

        String fromType = from.getClass().getSimpleName();
        String toType = toClass.getSimpleName();

        String converterName = String.format("convertFrom%sTo%s", fromType, toType);
        Method converter = converters.get(converterName);

        if (converter == null) {
            // 考虑两者都是复杂类型，通过序列化、反序列化方式实现转换。
            try {
                fromType = from.getClass().getName();
                toType = toClass.getName();

                String json = objectMapper.writeValueAsString(from);
                return objectMapper.readValue(json, toClass);
            } catch (Exception e) {
                String message = String.format("Can't convert from `%s` to `%s`. Not found satisfied conversion method.", fromType, toType);
                throw new UnsupportedOperationException(message, e);
            }
        }

        try {
            return toClass.cast(converter.invoke(toClass, from));
        } catch (Exception e) {
            String message = String.format("Can't convert from `%s` to `%s`. Conversion aborted with error: %s", fromType, toType, e.getMessage());
            throw new RuntimeException(message, e);
        }
    }

    private static Integer convertFromStringToInteger(String value) {
        return Integer.valueOf(value);
    }

    private static Long convertFromStringToLong(String value) {
        return Long.valueOf(value);
    }

    private static Float convertFromStringToFloat(String value) {
        return Float.valueOf(value);
    }

    private static Double convertFromStringToDouble(String value) {
        return Double.valueOf(value);
    }

    private static Boolean convertFromStringToBoolean(String value) {
        return Boolean.valueOf(value);
    }

    private static BigInteger convertFromStringToBigInteger(String value) {
        return new BigInteger(value);
    }

    private static BigDecimal convertFromStringToBigDecimal(String value) {
        return new BigDecimal(value);
    }

    // From Integer

    private static String convertFromIntegerToString(Integer value) {
        return String.valueOf(value);
    }

    private static Long convertFromIntegerToLong(Integer value) {
        return Long.valueOf(value);
    }

    private static Float convertFromIntegerToFloat(Integer value) {
        return Float.valueOf(value);
    }

    private static Double convertFromIntegerToDouble(Integer value) {
        return Double.valueOf(value);
    }

    private static Boolean convertFromIntegerToBoolean(Integer value) {
        return value != 0;
    }

    private static BigInteger convertFromIntegerToBigInteger(Integer value) {
        return BigInteger.valueOf(value);
    }

    private static BigDecimal convertFromIntegerToBigDecimal(Integer value) {
        return BigDecimal.valueOf(value);
    }

    // From Long

    private static String convertFromLongToString(Long value) {
        return String.valueOf(value);
    }

    private static Integer convertFromLongToInteger(Long value) {
        return value.intValue();
    }

    private static Float convertFromLongToFloat(Long value) {
        return Float.valueOf(value);
    }

    private static Double convertFromLongToDouble(Long value) {
        return Double.valueOf(value);
    }

    private static Boolean convertFromLongToBoolean(Long value) {
        return value != 0;
    }

    private static BigInteger convertFromLongToBigInteger(Long value) {
        return BigInteger.valueOf(value);
    }

    private static BigDecimal convertFromLongToBigDecimal(Long value) {
        return BigDecimal.valueOf(value);
    }

    // From Float

    private static String convertFromFloatToString(Float value) {
        return String.valueOf(value);
    }

    private static Integer convertFromFloatToInteger(Float value) {
        return value.intValue();
    }

    private static Long convertFromFloatToLong(Float value) {
        return value.longValue();
    }

    private static Double convertFromFloatToDouble(Float value) {
        return Double.valueOf(value);
    }

    private static Boolean convertFromFloatToBoolean(Float value) {
        return value != 0;
    }

    private static BigInteger convertFromFloatToBigInteger(Float value) {
        return BigInteger.valueOf(value.longValue());
    }

    private static BigDecimal convertFromFloatToBigDecimal(Float value) {
        return BigDecimal.valueOf(value);
    }

    // From Double

    private static String convertFromDoubleToString(Double value) {
        return String.valueOf(value);
    }

    private static Integer convertFromDoubleToInteger(Double value) {
        return value.intValue();
    }

    private static Long convertFromDoubleToLong(Double value) {
        return value.longValue();
    }

    private static Float convertFromDoubleToFloat(Double value) {
        return value.floatValue();
    }

    private static Boolean convertFromDoubleToBoolean(Double value) {
        return value != 0;
    }

    private static BigInteger convertFromDoubleToBigInteger(Double value) {
        return BigInteger.valueOf(value.longValue());
    }

    private static BigDecimal convertFromDoubleToBigDecimal(Double value) {
        return BigDecimal.valueOf(value);
    }

    // From Boolean

    private static String convertFromBooleanToString(Boolean value) {
        return String.valueOf(value);
    }

    private static Integer convertFromBooleanToInteger(Boolean value) {
        return value ? 1 : 0;
    }

    private static Long convertFromBooleanToLong(Boolean value) {
        return value ? 1L : 0L;
    }

    private static Float convertFromBooleanToFloat(Boolean value) {
        return value ? 1F : 0F;
    }

    private static Double convertFromBooleanToDouble(Boolean value) {
        return value ? 1D : 0D;
    }

    private static BigInteger convertFromBooleanToBigInteger(Boolean value) {
        return BigInteger.valueOf(value ? 1 : 0);
    }

    private static BigDecimal convertFromBooleanToBigDecimal(Boolean value) {
        return BigDecimal.valueOf(value ? 1 : 0);
    }

    // From BigInteger

    private static String convertFromBigIntegerToString(BigInteger value) {
        return String.valueOf(value);
    }

    private static Integer convertFromBigIntegerToInteger(BigInteger value) {
        return value.intValue();
    }

    private static Long convertFromBigIntegerToLong(BigInteger value) {
        return value.longValue();
    }

    private static Float convertFromBigIntegerToFloat(BigInteger value) {
        return value.floatValue();
    }

    private static Double convertFromBigIntegerToDouble(BigInteger value) {
        return value.doubleValue();
    }

    private static Boolean convertFromBigIntegerToBoolean(BigInteger value) {
        return value.longValue() != 0;
    }

    private static BigDecimal convertFromBigIntegerToBigDecimal(BigInteger value) {
        return new BigDecimal(value);
    }

    // From BigDecimal

    private static String convertFromBigDecimalToString(BigDecimal value) {
        return String.valueOf(value);
    }

    private static Integer convertFromBigDecimalToInteger(BigDecimal value) {
        return value.intValue();
    }

    private static Long convertFromBigDecimalToLong(BigDecimal value) {
        return value.longValue();
    }

    private static Float convertFromBigDecimalToFloat(BigDecimal value) {
        return value.floatValue();
    }

    private static Double convertFromBigDecimalToDouble(BigDecimal value) {
        return value.doubleValue();
    }

    private static Boolean convertFromBigDecimalToBoolean(BigDecimal value) {
        return value.longValue() != 0;
    }

    private static BigInteger convertFromBigDecimalToBigInteger(BigDecimal value) {
        return value.toBigInteger();
    }

}
