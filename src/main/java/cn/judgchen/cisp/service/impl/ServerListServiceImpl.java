package cn.judgchen.cisp.service.impl;


import cn.judgchen.cisp.dao.ServerListRepository;
import cn.judgchen.cisp.entity.ServerList;
import cn.judgchen.cisp.service.ServerListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerListServiceImpl implements ServerListService {
    private Logger logger = LoggerFactory.getLogger(DLServerServiceImpl.class);

    @Autowired
    private ServerListRepository serverListRepository;

    @Override
    public ServerList addServerInfo(ServerList server) {
        logger.info(server.getOrderNum()+"写入成功！");
        return serverListRepository.save(server);
    }

    @Override
    public int upateStateById(int state,int isPay,int id) {

        return serverListRepository.updateState(state,isPay,id);
    }

    @Override
    public ServerList selectByOrderNum(String orderNum) {
        return serverListRepository.findByOrderNum(orderNum);
    }

}
