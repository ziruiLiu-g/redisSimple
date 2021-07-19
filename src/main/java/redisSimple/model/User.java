package redisSimple.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * User
 *
 * Author: zirui
 * Date: 2021/7/19
 */
@Slf4j
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7224829375397030083L;
    String uid;
    String nickName;
}