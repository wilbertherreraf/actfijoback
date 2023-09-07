package gob.gamo.activosf.app.dto;

import java.io.Serializable;
import java.util.Date;

import gob.gamo.activosf.app.domain.entities.User;

public class UserRequestVo implements Serializable {

    private String userName;
    private Integer userId;
    private String host;
    private Date date;

    public UserRequestVo(String userName, Integer userId, String host, Date date) {
        super();
        this.userName = userName;
        this.userId = userId;
        this.host = host;
        this.date = date;
    }

    public UserRequestVo() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public static UserRequestVo convertUser(User user){
        return new UserRequestVo();
    }
}
