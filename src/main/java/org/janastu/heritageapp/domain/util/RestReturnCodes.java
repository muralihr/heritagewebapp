 package org.janastu.heritageapp.domain.util;

public class RestReturnCodes
{
    public static int USER_ID_EXISTS = 402;
    public static int EMAIL_ID_EXISTS = 403;
    public static int USER_NOT_FOUND = 404;
    public static int EXCEPTION = 407;
    public static int LOGIN_FAILURE_USERNAME_MISMATCH = 405;
    public static int LOGIN_FAILURE_PWD_MISMATCH= 406;    
    public static int SUCCESS = 200;
    
    public static int MEDIA_CREATION_EXCEPTION = 505;
    public static int GROUP_NOT_FOUND_EXCEPTION = 506;
    public static int USER_NOT_FOUND_EXCEPTION  = 507;
    public static int USER_STORAGE_CAPACITY_EXCEEDED = 508;
}
