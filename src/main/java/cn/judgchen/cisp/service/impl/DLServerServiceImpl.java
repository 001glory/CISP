package cn.judgchen.cisp.service.impl;


import cn.judgchen.cisp.dao.DLServerRepository;
import cn.judgchen.cisp.entity.DLServer;
import cn.judgchen.cisp.service.DLServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DLServerServiceImpl implements DLServerService {
    private Logger logger = LoggerFactory.getLogger(DLServerServiceImpl.class);

    @Autowired
    private DLServerRepository dlServerRepository;

    @Override
    public void addDLServer(DLServer dlServer) {
        dlServerRepository.save(dlServer);
        logger.info(dlServer.getServerName()+"注册成功！");
    }

    @Override
    public List<DLServer> selectDLServer(int uid) {
        return dlServerRepository.findDLServersByDlId(uid);
    }

    @Override
    @Transactional
    public void updateDLIcon(String icon,int dlId) {
        dlServerRepository.updateIcon(icon,dlId);
    }

}
