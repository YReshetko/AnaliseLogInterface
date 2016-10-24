package com.my.home.ejb;

import javax.ejb.Remote;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 28.08.16
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface DirTreeControllerRemote
{
    String getDirTree();
}
