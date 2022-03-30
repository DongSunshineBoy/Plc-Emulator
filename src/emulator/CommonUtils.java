package emulator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
            throw new BaseException(ExceptionEnum.NOT_FOUND_FORMATION);
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

    public static List<String> processParams(String functionParams) {

        if (!verifyUserParam(functionParams)) {
            throw new BaseException(ExceptionEnum.NOT_FOUND_FORMATION);
        }

        String functionName = getFunctionName(functionParams);

        List<String> params = new LinkedList<>();
        if (!functionParams.contains(",") && functionName.equals(RANDOM)) {
            String[] firstParams = functionParams.split("\\(");

            String[] split = firstParams[firstParams.length - 1].split("\\)");
            params.add(split[0]);
            return params;
        }

        if (!functionParams.contains(",") && functionName.equalsIgnoreCase(USER)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION);
        }

        if (!functionParams.contains(",") && functionName.equalsIgnoreCase(RAMP)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION);
        }

        String[] split = functionParams.split(",");
        String[] firstParams = split[0].split("\\(");

        params.add(firstParams[1]);


        params.addAll(Arrays.asList(split).subList(1, split.length - 1));

        String lastParams = split[split.length - 1].replace(")", "");
        params.add(lastParams);

        if (functionName.equals(RAMP) && !(params.size() == 4)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION);
        }

        if (functionName.equals(RANDOM) && !((params.size() <= 3) && (params.size() >= 1))) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION);
        }

        if(functionName.equals(USER) && !(params.size() >=  2)) {
            throw new BaseException(ExceptionEnum.NOT_AVAILABLE_PARAMS_FORMATION);
        }

        return params;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
    }


    public static boolean isDouble(String number) {
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
