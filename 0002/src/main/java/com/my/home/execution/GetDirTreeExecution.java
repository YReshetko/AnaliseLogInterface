package com.my.home.execution;

import com.my.home.ejb.DirTreeControllerRemote;

/**
 *
 */
public class GetDirTreeExecution extends AbstractExecutionBean<DirTreeControllerRemote>
{
    public GetDirTreeExecution(){}
    @Override
    public String execute(String data) {
        DirTreeControllerRemote bean = getBean();
        String out = "";
        if (bean != null)
        {
            out = bean.getDirTree();
        }
        return out;
    }
}
