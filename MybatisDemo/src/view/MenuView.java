package view;

import service.UserService;

import java.util.Scanner;

public class MenuView {
    public static int  showMenu() {
        System.out.println("----****超市收银系统----");
        System.out.println("1.收银");
        System.out.println("2.查询统计");
        System.out.println("3.商品维护");
        System.out.println("4.修改密码");
        System.out.println("5.数据导出");
        System.out.println("6.退出");
        System.out.println("当前收银员：" + UserService.user1.getChrName());
        System.out.println("请选择（1-6）：");
        Scanner scan = new Scanner(System.in);
        int choose=scan.nextInt();
        return choose;
    }

    public static int menu2() {
        System.out.println("===****超市商品管理维护====\n" +
                "1、从excel中导入数据\n" +
                "2、从文本文件导入数据\n" +
                "3、键盘输入\n" +
                "4、按商品名称查询\n" +
                "5、返回主菜单\n" +
                "请选择（1-5）：\n");
        int choose;
        Scanner scan = new Scanner(System.in);
        choose = scan.nextInt();
        return choose;
    }

    public static int menu3() {
        Scanner scan = new Scanner(System.in);
        System.out.println("===****超市销售信息导出====");
        System.out.println("1、导出到excel文件");
        System.out.println("2、导出到文本文件");
        System.out.println("3、返回主菜单");
        System.out.println("请选择（1-3）：");
        int choose = scan.nextInt();
        return choose;
    }
}
