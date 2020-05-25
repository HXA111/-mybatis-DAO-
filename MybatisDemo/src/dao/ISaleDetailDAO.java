package dao;

import vo.SaleDetail;

import java.util.ArrayList;

public interface ISaleDetailDAO {
    public String lsh(String Date)throws Exception;
    public int addDetail (SaleDetail saleDetail)throws Exception;
    public ArrayList<SaleDetail> readSaleDetail() throws Exception;
    public double totalSaleMoney(String saleTime) throws Exception;
    public ArrayList<SaleDetail> querySaleDetail(String saleTime) throws Exception;
}
