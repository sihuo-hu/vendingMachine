package com.royal.admin.modular.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Replenishment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface ReplenishmentMapper extends BaseMapper<Replenishment> {

    Page<Map<String, Object>> selectReplenishment(@Param("page") Page page, @Param("status") String status, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
