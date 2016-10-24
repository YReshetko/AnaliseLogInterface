package com.my.home.ejb;

import com.my.home.data.access.IDAORepository;
import com.my.home.data.access.impl.directory.DAORepositoryDir;
import com.my.home.directory.beans.Directory;
import com.my.home.utils.JsonUtils;

import javax.ejb.Stateless;

/**
 * Repository controller
 */
@Stateless(name = "DirTreeControllerRemote")
public class DirTreeController implements DirTreeControllerRemote {
    @Override
    public String getDirTree() {
        IDAORepository repository = new DAORepositoryDir();
        Directory directory = repository.getRepositoryStructure("");
        return JsonUtils.getJson(directory);
    }
}
