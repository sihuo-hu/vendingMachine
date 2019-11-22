package com.royal.admin.modular.system.mapper;

import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.User;
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
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 根据条件查询商品列表
     */
    Page<Map<String, Object>> selectCommodity(@Param("page") Page page, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    int updateStatusById(@Param("commodityId")String commodityId,@Param("status") String status);

    List<Map<String, Object>> selectGoodsInfo(@Param("floorId")Integer floorId);

}
