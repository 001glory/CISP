package cn.judgchen.cisp.service;


import cn.judgchen.cisp.entity.ServerList;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServerListService {

    ServerList addServerInfo(ServerList serverList);

    int upateStateById(int state, int isPay, int id);

    ServerList selectByOrderNum(String orderNum);
}
