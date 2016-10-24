package com.my.home.ejb;

import javax.ejb.Remote;

/**
 *
 */
@Remote
public interface DirTreeControllerRemote
{
    String getDirTree();
}
