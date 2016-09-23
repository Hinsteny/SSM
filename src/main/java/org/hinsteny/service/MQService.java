package org.hinsteny.service;

import java.util.List;

/**
 * @author Hinsteny
 * @date 2016/9/22
 * @copyright: 2016 All rights reserved.
 */
public interface MQService {

    boolean sendMessage(String message);

    List<String> getMessage();

}
