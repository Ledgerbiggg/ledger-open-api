package com.ledger.api_common.util;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ledger.api_common.model.query.PageQuery;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {

    /**
     * 查询页参数转换
     *
     * @param pageQuery
     * @param T
     * @param <T>
     * @return
     */
    public static <T> Page<T> getPageFromPageQuery(PageQuery pageQuery, Class<T> T) {
        return new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
    }

    /**
     * 页码参数转换
     *
     * @param source
     * @param K
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> Page<K> getRecordsBySource(Page<T> source, Class<K> K, long total) {
        List<T> records = source.getRecords();
        List<K> collect = records.stream().map(i -> BeanUtil.copyProperties(i, K)).collect(Collectors.toList());
        Page<K> targetPage = new Page<>();
        BeanUtil.copyProperties(source, targetPage);
        targetPage.setRecords(collect);
        targetPage.setTotal(total);
        return targetPage;
    }




}
