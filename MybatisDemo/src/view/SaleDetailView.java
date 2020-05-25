package view;

import service.SaleDetailService;
import util.check;
import vo.SaleDetail;

import java.util.ArrayList;
import java.util.Scanner;

public class SaleDetailView {
    public static void cashProcessView(){
        SaleDetailService saleDetailService=new SaleDetailService();
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入商品条形码（6位数字字符）");
        String barCode=scan.nextLine();
        System.out.println("请输入商品数量");
        int count=scan.nextInt();
        int code= saleDetailService.cashProcess(barCode,count);
        if(code==2){
            System.out.println("条形码输入格式不正确，请重新输入");
            cashProcessView();
        }else {
            if(code==1)
                System.out.println("成功增加"+code+"笔销售记录");
            if(code==3) {
                System.out.println("您输入的商品条形码不存在，请确认后重新输入");
                cashProcessView();
            }
        }
    }

    public static void showDetailView(){
        SaleDetailService saleDetailService=new SaleDetailService();
        ArrayList<SaleDetail> list=new ArrayList<>();
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入销售日期（yyyy-mm-dd）:");
        String saleTime=scan.nextLine();
        while(!check.isDate(saleTime)){
            System.out.println("你输入的日期格式不正确，请重新输入");
            saleTime=scan.nextLine();
        }
        String []str=saleTime.split("-");
        System.out.println(str[0] + "年" + str[1] + "月" + str[2]+"日销售如下：");
        System.out.println("流水号\t\t\t商品名称\t\t单价\t数量\t金额\t时间\t\t\t收银员");
        System.out.println("==================================================================");
        list=saleDetailService.getSaleList(saleTime);
        int productCount=0;
        int saleCount=0;
        double money=0;
        double totalMoney=0;
        saleCount=list.size();
        for (SaleDetail saleDetail:list) {
            money=saleDetail.getCount()*saleDetail.getPrice();
            productCount+=saleDetail.getCount();
            System.out.println(saleDetail.getLsh()+"\t"
                    +saleDetail.getProductName()+"\t\t"
                    +saleDetail.getPrice()+"\t\t"
                    +saleDetail.getCount()+"\t\t"
                    +money+"\t"+saleDetail.getSaleTime()+"\t\t"
                    +saleDetail.getOperator());
        }
        SaleDetailService saleDetailService1=new SaleDetailService();
        totalMoney=saleDetailService1.getTotalSaleMoney(saleTime);
        System.out.println("\n销售总数：" + saleCount + "\t商品总件：" + productCount
                + "\t销售总金额：" + totalMoney);
        System.out.println("日期" + str[0] + "年" + str[1] + "月" +
                str[2] + "日");
        System.out.println("按任意键返回主界面");
        scan.next();
    }

    public static void exportProcess()throws Exception{
        int choose= MenuView.menu3();
        int count=0;
        SaleDetailService saleDetailService=new SaleDetailService();
        while(true){
            switch (choose){
                case 1:count=saleDetailService.writerExcel();
                    System.out.println("成功导入"+count+"条数据到excel文件中");break;
                case 2:count=saleDetailService.writeTxt();
                    System.out.println("成功导入"+count+"条数据到文本文件中");break;
                case 3:return;
                default:
                    System.out.println("无效的选择！");
            }
            choose=MenuView.menu3();
        }
    }
}
