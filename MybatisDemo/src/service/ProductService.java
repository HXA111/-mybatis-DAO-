package service;

import dao.IProductDAO;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.ibatis.session.SqlSession;
import util.MybatisUtils;
import vo.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private IProductDAO productDAO = null;
    private SqlSession sqlSession = null;

    public ProductService() {
    }

    public List<Product> searchName(String productName) {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.productDAO = sqlSession.getMapper(IProductDAO.class);
        List<Product> products = new ArrayList<Product>();
        try {
            Product product = new Product();
            product.setProductName(productName);
            products = productDAO.query(product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return products;
    }

    public boolean inputProduct(String[] array) {
        Product product = new Product();
        product.setBarCode(array[0]);
        product.setProductName(array[1]);
        double price = Double.parseDouble(array[2]);
        product.setPrice(price);
        product.setSupply(array[3]);
        boolean flag = false;
        try {
            if (!existProduct(product.getBarCode())) {
                this.sqlSession = MybatisUtils.getSqlSession();
                this.productDAO = sqlSession.getMapper(IProductDAO.class);
                productDAO.addProduct(product);
                sqlSession.commit();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return flag;
    }

    public int addProductFromExcel() {
        List<Product> products = new ArrayList<Product>();
        int rs = 0;

        try {
            products = loadFromExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Product product : products) {
            if (!existProduct(product.getBarCode())) {
                try {
                    this.sqlSession = MybatisUtils.getSqlSession();
                    this.productDAO = sqlSession.getMapper(IProductDAO.class);
                    productDAO.addProduct(product);
                    rs++;
                    sqlSession.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sqlSession.close();
                }
            }
        }

        return rs;
    }

    public List<Product> loadFromExcel() throws Exception {
        List<Product> products = new ArrayList<Product>();
        Workbook workbook = Workbook.getWorkbook(new File("data/product.xls"));
        Sheet sheet = workbook.getSheet(0);
        String[] strings = new String[100];
        for (int i = 1; i < sheet.getRows(); i++) {
            for (int j = 0; j < sheet.getColumns(); j++) {
                Cell cell = sheet.getCell(j, i);
                strings[j] = cell.getContents();
            }
            double price = Double.parseDouble(strings[2]);
            Product product = new Product(strings[0], strings[1], price, strings[3]);
            products.add(product);
        }
        workbook.close();
        return products;
    }

    public int addProductFromTxt() {
        List<Product> products = new ArrayList<Product>();
        int rs = 0;
        try {
            products = loadFromTxt();
            for (Product product : products) {
                if (!existProduct(product.getBarCode())) {
                    this.sqlSession = MybatisUtils.getSqlSession();
                    this.productDAO = sqlSession.getMapper(IProductDAO.class);
                    productDAO.addProduct(product);
                    rs++;
                    sqlSession.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return rs;
    }

    public List<Product> loadFromTxt() throws Exception {
        List<Product> products = new ArrayList<Product>();
        File file = new File("data/product.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        String data = bf.readLine();
        String data1 = bf.readLine();
        while (data1 != null) {
            String[] strings = data1.split("\\s+");
            double price = Double.parseDouble(strings[2]);
            Product product = new Product(strings[0], strings[1], price, strings[3]);
            products.add(product);
            data1 = bf.readLine();
        }
        bf.close();
        fr.close();
        return products;
    }

    public boolean existProduct(String barCode) {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.productDAO = sqlSession.getMapper(IProductDAO.class);
        List<Product> products = new ArrayList<Product>();
        boolean flag = false;
        try {
            Product product = new Product();
            product.setBarCode(barCode);
            products = productDAO.query(product);
            if (products.size() != 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return flag;
    }

    public Product getProduct(String barCode) {
        this.sqlSession = MybatisUtils.getSqlSession();
        this.productDAO = sqlSession.getMapper(IProductDAO.class);
        List<Product> products = new ArrayList<Product>();
        Product product = new Product();
        try {
            product.setBarCode(barCode);
            products = productDAO.query(product);
            product = products.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return product;
    }
}
