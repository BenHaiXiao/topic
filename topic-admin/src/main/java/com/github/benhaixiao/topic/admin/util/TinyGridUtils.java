package com.github.benhaixiao.topic.admin.util;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * jquery tinygrid控件前/后台交互的数据转化工具类.
 * <p/>
 * 主要用于转换列表(List)数据变成前后需要JSON格式的数据.
 *
 * @author xiaobenhai
 */
public final class TinyGridUtils {
    /**
     * 默认的时间输出格式: yyyy-MM-dd HH:mm. 精确到分钟.
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    /**
     * 时间输出格式: yyyy-MM-dd. 精确到天.
     */
    public static final String DAY_FORMAT = "yyyy-MM-dd";

    private static final Logger LOG = LoggerFactory.getLogger(TinyGridUtils.class);

    private static JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

    private TinyGridUtils() {
        // empty!
    }

    /**
     * 把列表信息转化成json格式的Bean, 没有分页信息.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties) {
        return convert(beans, properties, null);
    }

    /**
     * 把列表信息转化成json格式的Bean, 没有分页信息.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     * @param extra      额外信息.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties, Object extra) {
        return convert(beans, properties, null, extra);
    }

    /**
     * 把列表信息转化成json格式的Bean, 不分页.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     * @param dateFormat 时间格式.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties,
                                       String dateFormat) {
        int page = 1, total = 0;
        if (null != beans) {
            total = beans.size();
        }
        return convert(beans, properties, page, total, dateFormat, null);
    }

    /**
     * 把列表信息转化成json格式的Bean, 不分页.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     * @param dateFormat 时间格式.
     * @param extra      额外信息.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties,
                                       String dateFormat, Object extra) {
        int page = 1, total = 0;
        if (null != beans) {
            total = beans.size();
        }
        return convert(beans, properties, page, total, dateFormat, extra);
    }

    /**
     * 把列表信息转化成json格式的Bean.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     * @param page       TinyGrid当前显示的页码数.
     * @param total      查询出来的总记录数.
     * @return json格式的Bean.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties, int page,
                                       int total) {
        return convert(beans, properties, page, total, null, null);
    }

    /**
     * 把列表信息转化成json格式的Bean.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     * @param page       TinyGrid当前显示的页码数.
     * @param total      查询出来的总记录数.
     * @param dateFormat 时间格式.
     * @return json格式的Bean.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties, int page,
                                       int total, String dateFormat) {
        return convert(beans, properties, page, total, dateFormat, null);
    }

    /**
     * 把列表信息转化成json格式的Bean.
     *
     * @param beans      要输出到前台的列表信息.
     * @param properties 前台需要展现的JavaBean属性.
     * @param page       TinyGrid当前显示的页码数.
     * @param total      查询出来的总记录数.
     * @param dateFormat 时间格式.
     * @param extra      额外信息.
     * @return json格式的Bean.
     */
    public static TinyGridBean convert(List<?> beans, String[] properties, int page,
                                       int total, String dateFormat, Object extra) {
        TinyGridBean gridBean = new TinyGridBean();
        gridBean.setPage(page);
        gridBean.setTotal(total);
        gridBean.setExtra(extra == null ? StringUtils.EMPTY : JsonMapper.nonDefaultMapper().toJson(extra));
        if (CollectionUtils.isEmpty(beans) || ArrayUtils.isEmpty(properties)) {
            gridBean.setTotal(0);
            return gridBean;
        }
        String dateFormatOverride = StringUtils.defaultString(dateFormat, DEFAULT_DATE_FORMAT);
        for (int i = 0, size = beans.size(); i < size; i++) {
            Object bean = beans.get(i);
            gridBean.addRow(generateCellFormat(bean, properties, dateFormatOverride));
        }
        return gridBean;
    }

    private static Map<String, String> generateCellFormat(Object bean, String[] properties, String dateFormat) {
        int len = properties.length;
        Map<String, String> cell = Maps.newHashMap();
        for (int i = 0; i < len; i++) {
            String property = properties[i];
            if (StringUtils.isBlank(property)) {
                continue;
            }

            Object propertyValue = null;
            try {
                propertyValue = PropertyUtils.getProperty(bean, property);
            } catch (Exception e) {
                propertyValue = "";
                LOG.info("提取属性:[" + property + "]失败", e);
            }

            String cellValue = null;
            if (propertyValue == null) {
                cellValue = "";
            } else if (propertyValue instanceof Date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                        dateFormat);
                cellValue = simpleDateFormat.format(propertyValue);
            }else {
                String jsonValue = jsonMapper.toJson(propertyValue);
                cellValue=jsonValue.toString();
            }

            cell.put(property, cellValue);
        }
        return cell;
    }

}
