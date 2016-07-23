package org.hinsteny.repository;

import org.hinsteny.bean.User;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends BaseRepository <User>{

}
