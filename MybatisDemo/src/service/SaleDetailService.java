package service;

import dao.ISaleDetailDAO;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Number;
import org.apache.ibatis.session.SqlSession;
import util.MybatisUtils;
import util.check;
import vo.Product;
import vo.SaleDetail;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaleDetailService {
    private ISaleDetailDAO saleDetailDAO = null;
    private SqlSession sqlSession = null;

    public SaleDetailService() {
    }

    public int cashProcess(String barCode, int count) {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.saleDetailDAO = sqlSession.getMapper(ISaleDetailDAO.class);
        int code = 0;
        check check = new check();
        Product product = null;
        if (!check.isProductCode(barCode)) {
            code = 2;
            return code;
        }
        try {
            ProductService productService = new ProductService();
            if (productService.existProduct(barCode)) {
                product = new Product();
                ProductService productService1=new ProductService();
                product=productService1.getProduct(barCode);
                String Date = check.getDate("cur");
                SaleDetail saleDetail = new SaleDetail();
                String rowCount=saleDetailDAO.lsh(Date);
                int count1=0;
                String lsh="";
                if(rowCount==null){
                    rowCount="0001";
                }else{
                    count1=Integer.parseInt(rowCount);
                    count1++;
                    rowCount=""+count1;
                    while(rowCount.length()<4){
                        rowCount="0"+rowCount;
                    }
                }
                lsh=Date+rowCount;
                saleDetail.setLsh(lsh);
                saleDetail.setBarCode(barCode);
                saleDetail.setProductName(product.getProductName());
                saleDetail.setPrice(product.getPrice());
                saleDetail.setCount(count);
                saleDetail.setOperator(UserService.user1.getChrName());
                saleDetail.setSaleTime(check.getDate("time"));
                code = saleDetailDAO.addDetail(saleDetail);
                sqlSession.commit();
            } else {
                code = 3;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return code;
    }

    public double getTotalSaleMoney(String saleTime) {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.saleDetailDAO = sqlSession.getMapper(ISaleDetailDAO.class);
        double totalSaleMoney = 0;
        try {
            totalSaleMoney = saleDetailDAO.totalSaleMoney(saleTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return totalSaleMoney;
    }

    public ArrayList<SaleDetail> getSaleList(String saleTime) {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.saleDetailDAO = sqlSession.getMapper(ISaleDetailDAO.class);
        ArrayList<SaleDetail> list = new ArrayList<>();
        try {
            list = saleDetailDAO.querySaleDetail(saleTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return list;
    }

    public int writerExcel() {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.saleDetailDAO = sqlSession.getMapper(ISaleDetailDAO.class);
        int count = 0;
        ArrayList<SaleDetail> list = new ArrayList<>();
        check check = new check();
        try {
            list = saleDetailDAO.readSaleDetail();
            String excelName = "data/saleDetail" + check.getDate("cur") + ".xls";
            File file = new File(excelName);
            WritableWorkbook wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("商品销售信息", 0);
            String[] title = {"流水号", "商品条形码", "商品名称", "价格", "数量", "收银员",
                    "销售时间"};
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14,
                    WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE,
                    Colour.RED);
            WritableCellFormat format = new WritableCellFormat(font);
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 12);
            WritableCellFormat format2 = new WritableCellFormat(font2);
            format.setBorder(Border.ALL, BorderLineStyle.DASHED);
            for (int col = 0; col < title.length; col++) {
                Label label = new Label(col, 0, title[col], format);
                ws.addCell(label);
            }
            for (int row = 1; row < list.size() + 1; row++) {
                Label label1 = new Label(0, row, list.get(row - 1).getLsh(), format2);
                ws.addCell(label1);
                Label label2 = new Label(1, row, list.get(row - 1).getBarCode(), format2);
                ws.addCell(label2);
                Label label3 = new Label(2, row, list.get(row - 1).getProductName(), format2);
                ws.addCell(label3);
                jxl.write.Number label4 = new jxl.write.Number(3, row, list.get(row - 1).getPrice(), format2);
                ws.addCell(label4);
                jxl.write.Number label5 = new Number(4, row, list.get(row - 1).getCount(), format2);
                ws.addCell(label5);
                Label label6 = new Label(5, row, list.get(row - 1).getOperator(), format2);
                ws.addCell(label6);
                Label label7 = new Label(6, row, list.get(row - 1).getSaleTime(), format2);
                ws.addCell(label7);
                count++;
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return count;
    }

    public int writeTxt() {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.saleDetailDAO = sqlSession.getMapper(ISaleDetailDAO.class);
        int count = 0;
        ArrayList<SaleDetail> list = new ArrayList<>();
        check check = new check();
        try {
            list = saleDetailDAO.readSaleDetail();
            String excelName = "data/saleDetail" + check.getDate("cur") + ".txt";
            File file = new File(excelName);
            FileWriter wr = new FileWriter(file);
            PrintWriter pw = new PrintWriter(wr);
            String title = "流水号，商品条形码，商品名称，价格，数量，收银员，销售时间";
            pw.println(title);
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i).toString();
                pw.println(s);
                count++;
            }
            wr.close();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return count;
    }
}
