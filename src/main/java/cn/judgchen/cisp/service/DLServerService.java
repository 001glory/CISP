package cn.judgchen.cisp.service;



import cn.judgchen.cisp.entity.DLServer;

import java.util.List;

public interface DLServerService {

    void addDLServer(DLServer dlServer);

    List<DLServer> selectDLServer(int uid);

    void updateDLIcon(String icon, int dlId);


}
