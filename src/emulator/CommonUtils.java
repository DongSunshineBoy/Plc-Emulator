package emulator;

import org.apache.commons.lang3.math.NumberUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: wenTaoDong
 * @Date: 2022/3/29 03-29 22:03
 * @Description: day8
 * @Version 1.0
 */
public class CommonUtils {

    private final static String RANDOM = "random";
    private final static String RAMP = "ramp";
    private final static String USER = "user";


    public static String getFunctionName(String functionParams) {

        if (!verifyUserParam(functionParams)) {
            throw new BaseException(ExceptionEnum.NOT_FOUND_FORMATION, functionParams);
        }

        String[] split = functionParams.split(",");
        String[] firstParams = split[0].split("\\(");
        return firstParams[0];
    }

    public static Boolean verifyUserParam(String functionParams) {
        String multipleArgument = "^\\w+\\(\\w+.+,.*\\)$";
        String singleArgument = "^\\w+\\(\\w.*\\)$";
        boolean multipleArgumentMatch = functionParams.matches(multipleArgument);
        boolean singleArgumentMatch = functionParams.matches(singleArgument);
        if (!multipleArgumentMatch && !singleArgumentMatch) {
            return false;
        }
        return true;
    }


    static List<Object> processParams(String functionParams) {

        if (!verifyUserParam(functionParams)) {
            throw new BaseException(ExceptionEnum.NOT_FOUND_FORMATION, functionParams);
        }

        String functionName = getFunctionName(functionParams);

        List<String> params = new LinkedList<>();
        if (!functionParams.contains(",") && functionName.equals(RANDOM)) {
            String[] firstParams = functionParams.split("\\(");

            String[] split = firstParams[firstParams.length - 1].split("\\)");
            params.add(split[0]);
            return convertToSpecialType(params);
        }

        if (!functionParams.contains(",") && functionName.equalsIgnoreCase(USER)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION, functionName);
        }

        if (!functionParams.contains(",") && functionName.equalsIgnoreCase(RAMP)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION, functionName);
        }

        String[] split = functionParams.split(",");
        String[] firstParams = split[0].split("\\(");

        params.add(firstParams[1]);


        params.addAll(Arrays.asList(split).subList(1, split.length - 1));

        String lastParams = split[split.length - 1].replace(")", "");
        params.add(lastParams);

        if (functionName.equals(RAMP) && !(params.size() == 4)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION, params.size());
        }

        if (functionName.equals(RANDOM) && !((params.size() <= 3) && (params.size() >= 1))) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION, params.size());
        }

        if(functionName.equals(USER) && !(params.size() >=  2)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION, params.size());
        }

        return convertToSpecialType(params);
    }

    private static List<Object> convertToSpecialType(List<String> userParams) {

        if (userParams == null) return new ArrayList<>();
        List<Object> specialType = new ArrayList<>();

        for (String userParam : userParams) {
            String value = userParam.trim().replace(" ", "");
            if (isNumeric(value)) {
                if (isDecimal(value)) {
                   specialType.add(Double.valueOf(value));
                } else {
                    if (isShort(value)) {
                        specialType.add(Short.valueOf(value));
                    }
                    if (!isShort(value) && isInteger(value)) {
                        specialType.add(Integer.valueOf(value));
                    }
                    if (!isInteger(value) && isLong(value)) {
                        specialType.add(Long.valueOf(value));
                    }
                }
            } else if (isBoolean(value)) {
                specialType.add(Boolean.valueOf(value));
            } else {
                specialType.add(value);
            }
        }
        return specialType;
    }

    public static boolean isFloat(String val) {
        if (val == null || val.isEmpty()) return false;
        if (isDecimal(val)) {
            verifyBound(val);
            Double longValue = Double.valueOf(val);
            return longValue >= Float.MIN_VALUE && longValue <= Float.MAX_VALUE;
        }
        return false;
    }

    private static boolean isDouble(String val) {
        if (val == null || val.isEmpty()) return false;
        if (isDecimal(val)) {
            verifyBound(val);
            Double longValue = Double.valueOf(val);
            return longValue >= Double.MIN_VALUE && longValue <= Double.MAX_VALUE;
        }
        return false;
    }



    private static boolean isShort(String val) {
        if (val == null || val.isEmpty()) return false;
        if (isNumeric(val) && !isDecimal(val)) {
            verifyBound(val);
            Long longValue = Long.valueOf(val);
            return longValue >= Short.MIN_VALUE && longValue <= Short.MAX_VALUE;
        }
        return false;
    }

    private static void verifyBound(String val) {
        if (isNumeric(val) && !isDecimal(val)) {
            int length = val.length();
            if (length > 19) {
                throw new BaseException(ExceptionEnum.VALUE_TOO_LONG, val);
            }
        }else {
            int length = val.length();
            if (length > 19) {
                throw new BaseException(ExceptionEnum.VALUE_TOO_LONG, val);
            }
        }
    }

    private static boolean isInteger(String val) {
        if (val == null || val.isEmpty()) return false;
        if (isNumeric(val) && !isDecimal(val)) {
            verifyBound(val);
            Long longValue = Long.valueOf(val);
            return longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE;
        }
        return false;
    }

    public static  <T> T getObjectType(String val) {
        if (isNumeric(val)) {
            if (isShort(val)) {
               return (T)Short.valueOf(val);
            }
            if (!isShort(val) && isInteger(val)) {
                return (T)Integer.valueOf(val);
            }
            if (!isInteger(val) && isLong(val)) {
               return (T)Long.valueOf(val);
            }
        }
        return null;
    }

    public static boolean isLong(String val) {
        if (val == null || val.isEmpty()) return false;
        if (isNumeric(val) && !isDouble(val)) {
            verifyBound(val);
            Long longValue = Long.valueOf(val);
            return longValue >= Long.MIN_VALUE && longValue <= Long.MAX_VALUE;
        }
        return false;
    }

    public static boolean isNumeric(String str){
       return NumberUtils.isNumber(str);
    }

    public static boolean isDecimal(String number) {
        if (null == number || "".equals(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");
        return pattern.matcher(number).matches();
    }

    public static boolean isBoolean(String number) {
        if (null == number || "".equals(number)) {
            return false;
        }
        return "true".equalsIgnoreCase(number) || "false".equalsIgnoreCase(number);
    }
}
