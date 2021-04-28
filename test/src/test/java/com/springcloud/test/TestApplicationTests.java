package com.springcloud.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springcloud.test.mapper.HistoryMapper;
import com.springcloud.test.model.History;
import com.springcloud.test.util.DecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TestApplicationTests {

    @Resource
    HistoryMapper historyMapper;

    static final int DUAN_MIN = 0;
    static final int DUAN_MAX = 1;

    /**
     * 分段
     */
    static final List<List<Integer>> dualList = Lists.newArrayList(
            Lists.newArrayList(1, 8),
            Lists.newArrayList(9, 16));

    @Test
    public void test() {
        List<History> historyList = historyMapper.getList(Maps.newHashMap());
        int total = historyList.size();
        int num = 0;

        /**
         * 第一层是算法
         * 第二层对应几个分段
         */
        List<List<Integer>> countList = Lists.newArrayList();
        for (int i=0; i<3; i++) {
            countList.add(nCopy(dualList.size(), 0));
        }

        Map<Integer, Integer> redCountMap = Maps.newHashMap();
        for (int i=1; i<=33; i++) {
            redCountMap.put(i, 0);
        }

        /**
         * 算法
         */
        List<String> countPlayNameList = Lists.newArrayList("连号", "同号", "同区");

        for (History history : historyList) {
            //连号
            if ((num == history.getBlue() + 1 || num == history.getBlue() - 1)){
                log.info("连号：{}, 期数：{}", history.getBlue(), history.getNo());
                addCount(0, history.getBlue(), countList);
            }

            //同号
            if (num == history.getBlue()) {
                log.info("同号：{}, 期数：{}", history.getBlue(), history.getNo());
                addCount(1, history.getBlue(), countList);
            }

            //同区
            if ((num > 8 && history.getBlue() > 8) || (num <= 8 && history.getBlue() <= 8)) {
                log.info("同区：{}, 期数：{}", history.getBlue(), history.getNo());
                addCount(2, history.getBlue(), countList);
            }

            redCountMap.put(history.getRed1(), redCountMap.get(history.getRed1()) + 1);
            redCountMap.put(history.getRed2(), redCountMap.get(history.getRed2()) + 1);
            redCountMap.put(history.getRed3(), redCountMap.get(history.getRed3()) + 1);
            redCountMap.put(history.getRed4(), redCountMap.get(history.getRed4()) + 1);
            redCountMap.put(history.getRed5(), redCountMap.get(history.getRed5()) + 1);
            redCountMap.put(history.getRed6(), redCountMap.get(history.getRed6()) + 1);

            num = history.getBlue();
        }


        for (int i=0; i<countList.size(); i++) {
            String play = countPlayNameList.get(i);
            for (int j=0; j<dualList.size(); j++) {
                int count = countList.get(i).get(j);
                log.info("{}-{}{}：{}, 概率：{}", dualList.get(j).get(DUAN_MIN), dualList.get(j).get(DUAN_MAX), play, count, DecimalUtil.div(count, total));
            }
        }

        HashMap<Integer, Integer> finalOut = new LinkedHashMap<>();
        redCountMap.entrySet()
                        .stream()
                        .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                        .collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
        //

        for (int key : finalOut.keySet()) {
            log.info("{}-{}", key, finalOut.get(key));
        }
    }

    public List<Integer> nCopy(int n, int value){
        List<Integer> list = Lists.newArrayList();
        for (int i=0; i<n; i++) {
            list.add(value);
        }
        return list;
    }

    public void addCount(int countIndex, int value, List<List<Integer>> countList){
        for (int i=0; i<dualList.size(); i++) {
            List<Integer> list = dualList.get(i);
            if (value >= list.get(DUAN_MIN) && value <= list.get(DUAN_MAX)) {
                List<Integer> count = countList.get(countIndex);
                count.set(i, countList.get(countIndex).get(i) + 1);
                break;
            }
        }
    }

}
