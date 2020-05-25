package dao;

import vo.Product;

import java.util.List;

public interface IProductDAO {
    public List<Product> query(Product product) throws Exception;
    public int addProduct(Product product) throws Exception;
    public int delete(String barCode) throws Exception;
}
