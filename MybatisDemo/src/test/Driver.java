package test;

import view.MenuView;
import view.ProductView;
import view.SaleDetailView;
import view.UserView;

public class Driver {
    public static void main(String[] args) throws Exception {
        UserView.loginProcessView();
        while (true) {
            int choose = MenuView.showMenu();
            switch (choose) {
                case 1:
                    SaleDetailView.cashProcessView();
                    break;
                case 2:
                    SaleDetailView.showDetailView();
                    break;
                case 3:
                    ProductView.serviceProcessView();
                    break;
                case 4:
                    UserView.changePasswordView();
                    break;
                case 5:
                    SaleDetailView.exportProcess();
                    break;
                case 6:
                    UserView.dropOut();
                    break;
                default:
                    System.out.println("无效的选择！");
            }
        }
    }
}