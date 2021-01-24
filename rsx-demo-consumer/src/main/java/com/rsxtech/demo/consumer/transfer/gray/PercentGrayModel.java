package com.rsxtech.demo.consumer.transfer.gray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 百分比灰度配置
 * <p>
 * 优先级：白名单 > 黑名单 > 灰度规则
 *
 * @author raysonxin
 * @since 2021/1/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PercentGrayModel {

    /**
     * 百分比，整数。取值范围：[0,100]，0代表关闭，100代表全量
     */
    private Integer percent;

    /**
     * 白名单
     */
    private List<Long> whiteList;

    /**
     * 黑名单
     */
    private List<Long> blackList;

    /**
     * 检查目标是否命中灰度
     *
     * @param target 目标
     * @return true-命中，false-未命中
     */
    public boolean hitGray(Long target) {
        // 数据异常
        if (null == target || target < 0L) {
            return false;
        }

        // 命中白名单，返回true
        if (!ObjectUtils.isEmpty(whiteList) && whiteList.contains(target)) {
            return true;
        }

        // 命中黑名单，返回false
        if (!ObjectUtils.isEmpty(blackList) && blackList.contains(target)) {
            return false;
        }

        // 无百分比灰度规则
        if (percent == null || percent < 0) {
            return false;
        }

        // 大于等于100，相当于全量
        if (percent >= 100) {
            return true;
        }

        // 按照灰度百分比计算是否命中
        return target % 100 < percent;
    }



}
