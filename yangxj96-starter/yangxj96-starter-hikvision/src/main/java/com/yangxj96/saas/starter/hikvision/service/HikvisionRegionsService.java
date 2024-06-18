package com.yangxj96.saas.starter.hikvision.service;

import com.yangxj96.saas.starter.hikvision.response.HikvisionPage;
import com.yangxj96.saas.starter.hikvision.response.entity.Regions;

/**
 * 区域相关接口业务层
 */
public interface HikvisionRegionsService {

    /**
     * 获取根区域信息
     *
     * @return 根区域信息
     * @throws Exception e
     */
    Regions root() throws Exception;

    /**
     * 分页获取区域信息
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return 区域信息列表
     * @throws Exception e
     */
    HikvisionPage<Regions> page(Integer pageNo, Integer pageSize) throws Exception;
}
