package view;

import service.ProductService;
import service.UserService;
import util.check;
import vo.Product;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    public static void serviceProcessView()throws Exception{
        int choose=0;
        int count=0;
        if(!UserService.user1.getRole().equals("管理员")){
            System.out.println("当前用户没有执行该项功能的权限");
            return;
        }else{
            ProductService productService=new ProductService();
            choose=MenuView.menu2();
            while(true){
                switch (choose){
                    case 1:
                        count= productService.addProductFromExcel();
                        System.out.println("成功从excel文件导入"+count+"条商品数据");break;
                    case 2: count=productService.addProductFromTxt();
                        System.out.println("成功从文本文件导入"+count+"条商品数据");break;
                    case 3: inputProductView();break;
                    case 4: searchNameView();break;
                    case 5:return;
                    default:
                        System.out.println("输入无效，只能输入1-5");
                }
                choose=MenuView.menu2();
            }
        }
    }

    public static void inputProductView() throws Exception {
        ProductService productService=new ProductService();
        String info;
        System.out.println("请输入商品信息（100001 书包 300 耐克）");
        Scanner scan=new Scanner(System.in);
        info=scan.nextLine();
        String []array=info.split("\\s+");
        check check=new check();
        if(check.isProductCode(array[0])&&array.length==4){

            if(productService.existProduct(array[0])){
                System.out.println("条形码不能重复，请重新输入");
                inputProductView();
            }else{
                if(productService.inputProduct(array)){
                    System.out.println("增加成功");
                }else{
                    System.out.println("增加失败");
                }
            }
        }else{
            System.out.println("你输入的格式不正确，请重新输入");
            inputProductView();
        }
    }

    public static void searchNameView(){
        ProductService productService=new ProductService();
        Scanner scan=new Scanner(System.in);
        List<Product> products=null;
        System.out.println("请输入查询的商品名称:");
        String productName=scan.nextLine();
        products=productService.searchName(productName);
        int count=0;
        if(products!=null){
            System.out.println("满足条件的记录总共" + products.size() + "条，信息如下：");
            System.out.println("序号\t条形码\t商品名称\t单价\t供应商");
            System.out.println("==============================================");
            for (Product product : products) {
                count++;
                System.out.println(count + "\t" + product.getBarCode()+"\t"+product.getProductName()
                        +"\t"+product.getPrice()+"\t"+product.getSupply());
            }
        }else{
            System.out.println("商品库中没有类似的商品");
        }
    }
}
