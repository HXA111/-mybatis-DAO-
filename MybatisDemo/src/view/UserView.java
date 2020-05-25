package view;

import service.UserService;
import util.check;
import vo.User;

import java.util.Scanner;

public class UserView {
    private static int count = 1;

    public static User show() {
        System.out.println("欢迎使用****超市收银系统，请登录");
        Scanner s = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String userName = s.nextLine();
        System.out.println("请输入密码：");
        String password = s.nextLine();
        String miwen = check.md5(password);
        User user = new User();
        user.setUserName(userName);
        user.setPassword(miwen);
        return user;
    }

    public static void loginProcessView() {
        User user = UserView.show();
        UserService us=new UserService();
        int s = us.checkLogin(user);
        if (s == 1) {
            System.out.println("登录成功！");
            return;
        } else {
            count++;
            if (count > 3) {
                System.out.println("最多只能尝试3次，即将退出！");
                System.exit(0);
            }
            System.out.println("用户名或密码不正确!");
            System.out.println("第" + count + "尝试登录");
            loginProcessView();
        }
    }

    public static void changePasswordView() {
        UserService us=new UserService();
        Scanner scan = new Scanner(System.in);
        String newPassword = null;
        String password = null;
        String passwordTwo = null;
        System.out.println("请输入当前用户的原密码：");
        password = scan.nextLine();
        while (!check.md5(password).equals(UserService.user1.getPassword())) {
            System.out.println("原密码输入不正确,请重新输入:");
            password = scan.nextLine();
        }
        System.out.println("请设置新的密码：");
        newPassword = scan.nextLine();
        while (!check.isPassword(newPassword)) {
            System.out.println("您的密码不符合复杂性要求");
            System.out.println("请设置新的密码：");
            newPassword = scan.nextLine();
        }
        System.out.println("请输入确认密码：");
        passwordTwo = scan.nextLine();
        while (!newPassword.equals(passwordTwo)) {
            System.out.println("两次输入的密码必须一致，请重新输入确认密码：");
            passwordTwo = scan.nextLine();
        }
        if (us.changePassword(newPassword)) {
            System.out.println("您已成功修改密码，请谨记");
        } else {
            System.out.println("发生错误，修改失败");
        }
    }

    public static void dropOut() {
        System.out.println("确定退出系统吗？");
        System.out.println("输入'Y、y',退出程序，输入'N/n'返回菜单");
        Scanner s = new Scanner(System.in);
        String choose = s.nextLine();
        if (choose.equals("Y") || choose.equals("y")) {
            System.out.println("谢谢使用！");
            System.exit(0);
        }
        if (choose.equals("N") || choose.equals("n")) {
            return;
        }
        System.out.println("无效的选择");
        dropOut();
    }
}
