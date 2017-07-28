package org.biac.service.fdfs;

import org.csource.common.MyException;

import java.io.IOException;
import java.util.HashMap;

/**
 * 由于DFS服务器的storage_server只能在内网访问,这个Mock类模拟Fdfs的输入输出,便于在本地测试
 */
public class FdfsServiceMock {

    public String upLoadByteArray(byte[] data, int offset, int length, String ext, HashMap<String,String> map) throws IOException,MyException {
        return "/M00/01/SKFDJSHKDK02931";

    }
}
