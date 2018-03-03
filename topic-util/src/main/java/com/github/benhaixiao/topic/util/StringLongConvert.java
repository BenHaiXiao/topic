package com.github.benhaixiao.topic.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiaobenhai
 */
public class StringLongConvert {
    /**
     * 以字符串数组转Long数组
     * @param strings
     * @return
     */
    public static Long [] Strings2Longs(String[] strings){
        Long[] longs=new Long[strings.length];
        for (int i=0;i<strings.length;i++){
            longs[i]=Long.parseLong(strings[i]);
        }
        return longs;
    }
    /**
     * 以Long数组转字符串数组
     * @param longs
     * @return
     */
    public static String Strings2Longs(Long []longs){
      String s=  StringUtils.join(longs,",");
        return s;
    }
}
