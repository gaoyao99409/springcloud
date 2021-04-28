package com.springcloud.test.service;

import java.util.List;
import java.util.Map;

import com.springcloud.test.model.History;

public abstract class BasePlay {

    List<History> historyList;

    public abstract Map<Integer, Integer> play();
}
