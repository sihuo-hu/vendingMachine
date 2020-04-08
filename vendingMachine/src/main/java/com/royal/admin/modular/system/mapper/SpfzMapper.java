package com.royal.admin.modular.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Floor;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.model.GoodsList;
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
public interface SpfzMapper extends BaseMapper<Spfz> {

    List<Map<String, Object>> selectSpfzAndFloor(@Param("id") Integer id,@Param("machinesId") String machinesId);

    List<GoodsList> fzList(@Param("machinesId")String machinesId);

    Floor getFloorListBySpfzId(@Param("spfzId") Integer spfzId,@Param("grade") Integer grade);
}
