package com.custom.chenxz.object.utils.id_validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 身份证号验证器：
 * 使用方法：
 * DValidator validator = new IDValidator();
 * <p>
 * //验证身份证是否有效
 * validator.isValid("152103198909218022")
 * <p>
 * //分析详细信息
 * validator.getInfo(id15)
 * <p>
 * //生成18位身份证号
 * validator.makeID(false)
 * <p>
 * //生成15位身份证号
 * validator.makeID(true)
 */
public class IDValidator {
    private static Map<String, String> gb2260 = GB2260.getInstance();
    private static Map<String, IDCodeInfo> cache = new HashMap<String, IDCodeInfo>();
    private static Map<String, String> cacheOther = new HashMap<>();

    /**
     * 身份证是否有效
     *
     * @param id {@link String}
     * @return {@link Boolean}
     */
    public static boolean isValid(String id) {
        if (id == null) {
            return false;
        }
        // 查询cache
        if (cache.containsKey(id)) {
            return cache.get(id).isValid();
        }
        if (cacheOther.containsKey(id)) {
            return true;
        }
        IDCodeInfo code = Utils.checkArg(id);
        if (code == null) {
            String num = id.toUpperCase();
            //如果是特别行政区ID
            String regEx = null;
            //台 校验码=10-通算值的末尾数
            //通算值= 首字母对应的第一位验证码 + 首字母对应的第二位验证码*9 + 性别码*8 + 第二位数字*7 + 第三位数字*6 + 第四位数字*5 + 第五位数字*4 + 第六位数字*3 + 第七位数字*2 + 第八位数字*1
            String regExTW = "^[A-KM-QT-XZ]{1}[1|2][0-9]";
            //澳
            String regExAM = "^[1|5|7][0-9]{6}\\([0-9aA]\\)$";
            //港
            String regExHK = "^((\\s[A-Za-z]|([A-Za-z]{2}))\\d{6}((\\([0-9aA]\\))|([0-9aA])))$";
            boolean isID = Pattern.compile(regExTW).matcher(num).find() |
                    Pattern.compile(regExAM).matcher(num).find() |
                    Pattern.compile(regExHK).matcher(num).find();
            if (isID) {
                cacheOther.put(id, num);
            }
            return isID;
        }
        Utils.parseCode(code);
        if (!(Utils.checkAddr(code.getAddrCode())
                && Utils.checkBirth(code.getBirthCode()) && Utils.checkOrder(code.getOrder()))) {
            code.setValid(false);
            cache.put(id, code);
            return false;
        }

        // 15位不含校验码，到此已结束
        if (code.getType() == 15) {
            code.setValid(true);
            cache.put(id, code);
            return true;
        }

		/* 校验位部分 */
        // 位置加权
        int[] posWeight = new int[17];
        for (int i = 18; i > 1; i--) {
            int wei = Utils.weight(i);
            posWeight[18 - i] = wei;
        }

        // 累加body部分与位置加权的积
        int bodySum = 0;
        String body = code.getBody();
        String[] bodyArr = body.split("");
        for (int j = 0; j < bodyArr.length-1; j++) {
            bodySum += (Integer.valueOf(bodyArr[j+1], 10) * posWeight[j]);
        }

        // 得出校验码
        int tempCheckBit = 12 - (bodySum % 11);
        String checkBit = String.valueOf(tempCheckBit);
        if (tempCheckBit == 10) {
            checkBit = "X";
        } else if (tempCheckBit > 10) {
            checkBit = String.valueOf(tempCheckBit % 11);
        }

        // 检查校验码
        if (!checkBit.equals(code.getCheckBit())) {
            code.setValid(false);
            cache.put(id, code);
            return false;
        } else {
            code.setValid(true);
            cache.put(id, code);
            return true;
        }
    }

    /**
     * 分析详细信息
     *
     * @param id {@link String} 身份证号
     * @return {@link IDCodeInfo}
     */
    public static IDCodeInfo getInfo(String id) {
        // 号码必须有效
        if (IDValidator.isValid(id) == false) {
            return null;
        }
        //复用此部分
        IDCodeInfo code = Utils.checkArg(id);
        // 查询cache
        // 到此时通过isValid已经有了cache记录
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        Utils.parseCode(code);
        // 记录cache
        cache.put(id, code);
        return code;
    }

    /**
     * 仿造一个号
     *
     * @param isFifteen 是否生成15位数
     * @return
     */
    public static String makeID(boolean isFifteen) {
        // 地址码
        String addr = "";
        if (gb2260 != null) {
            int loopCnt = 0;
            while (addr == "") {
                // 防止死循环
                if (loopCnt > 10) {
                    addr = "110101";
                    break;
                }
                String prov = Utils.strPad(String.valueOf(Utils.rand(50, 1)),
                        2, '0', false);
                String city = Utils.strPad(String.valueOf(Utils.rand(60, 1)),
                        2, '0', false);
                String area = Utils.strPad(String.valueOf(Utils.rand(20, 1)),
                        2, '0', false);
                String addrTest = prov + city + area;
                if (gb2260.containsKey(addrTest)) {
                    addr = addrTest;
                    break;
                }
            }
        } else {
            addr = "110101";
        }

        // 出生年
        String yr = Utils.strPad(String.valueOf(Utils.rand(99, 50)), 2, '0',
                false);
        String mo = Utils.strPad(String.valueOf(Utils.rand(12, 1)), 2, '0',
                false);
        String da = Utils.strPad(String.valueOf(Utils.rand(28, 1)), 2, '0',
                false);
        if (isFifteen) {
            return addr
                    + yr
                    + mo
                    + da
                    + Utils.strPad(String.valueOf(Utils.rand(999, 1)), 3, '1',
                    false);
        }

        yr = "19" + yr;
        String body = addr
                + yr
                + mo
                + da
                + Utils.strPad(String.valueOf(Utils.rand(999, 1)), 3, '1',
                false);

        // 位置加权
        int[] posWeight = new int[17];
        for (int i = 18; i > 1; i--) {
            int wei = Utils.weight(i);
            posWeight[18 - i] = wei;
        }

        // 累加body部分与位置加权的积
        int bodySum = 0;
        String[] bodyArr = body.split("");
        for (int j = 0; j < bodyArr.length-1; j++) {
            bodySum += (Integer.valueOf(bodyArr[j+1], 10) * posWeight[j]);
        }

        // 得出校验码
        int tempCheckBit = 12 - (bodySum % 11);
        String checkBit = String.valueOf(tempCheckBit);
        if (tempCheckBit == 10) {
            checkBit = "X";
        } else if (tempCheckBit > 10) {
            checkBit = String.valueOf(tempCheckBit % 11);
        }

        return (body + checkBit);
    }
}