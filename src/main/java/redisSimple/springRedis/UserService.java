package redisSimple.springRedis;

import redisSimple.model.User;

/**
 * UserService
 *
 * Author: zirui
 * Date: 2021/7/19
 */
public interface UserService {

    /**
     * read
     *
     * @param id id
     * @return user user
     */
    User getUser(long id);

    /**
     * update
     *
     * @param user user
     */
    User saveUser(final User user);

    /**
     * delete
     *
     * @param id id
     */

    void deleteUser(long id);


    /**
     * del all
     */
    public void deleteAll();

}