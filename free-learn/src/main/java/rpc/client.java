package rpc;

import Service.IUserService;

/**
 * @author lpx .
 * @create 2020-03-08-14:42 .
 * @description .
 */
public class client {
    public static void main(String[] args) {
        IUserService userService = stub.getStub();
        System.out.println(userService.findUserById(123));
    }
}
