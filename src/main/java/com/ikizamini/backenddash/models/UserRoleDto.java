package com.ikizamini.backenddash.models;

public class UserRoleDto
{
    private Integer userRoleId;
    private String userRole;
    private Integer rank;

    public Integer getUserRoleId()
    {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId)
    {
        this.userRoleId = userRoleId;
    }

    public String getUserRole()
    {
        return userRole;
    }

    public void setUserRole(String userRole)
    {
        this.userRole = userRole;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }
}
