package com.royal.admin.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Floor;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.mapper.SpfzMapper;
import com.royal.admin.modular.system.model.GoodsList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class SpfzService extends ServiceImpl<SpfzMapper, Spfz> {

    public List<GoodsList> fzList(String machinesId) {
        List<GoodsList> list = this.baseMapper.fzList(machinesId);
        if (!list.isEmpty() && list.size() > 0) {
            for (GoodsList goodsList : list) {
                if ("一星".equals(goodsList.getDifficulty())) {
                    goodsList.setDifficulty("1");
                } else if ("二星".equals(goodsList.getDifficulty())) {
                    goodsList.setDifficulty("2");
                } else if ("三星".equals(goodsList.getDifficulty())) {
                    goodsList.setDifficulty("3");
                } else if ("四星".equals(goodsList.getDifficulty())) {
                    goodsList.setDifficulty("4");
                } else if ("五星".equals(goodsList.getDifficulty())) {
                    goodsList.setDifficulty("5");
                }
            }
        }
        return list;
    }

    public Floor getFloorListBySpfzId(Integer spfzId, Integer grade) {
        return this.baseMapper.getFloorListBySpfzId(spfzId, grade);
    }
}
