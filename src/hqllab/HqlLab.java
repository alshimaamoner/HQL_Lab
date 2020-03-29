/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hqllab;
import entity.Buyer;
import entity.BuyerBidProduct;
import entity.BuyerBidProductId;
import entity.BuyerBuyProduct;
import entity.BuyerBuyProductId;
import entity.Category;
import entity.Product;
import entity.Seller;
import entity.User;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author DELL
 */
public class HqlLab {

    /**
     * @param args the command line arguments
     */
  static  SessionFactory sessionFactory = new Configuration() .configure("hibernate.cfg.xml").buildSessionFactory();
  static   Session session = sessionFactory.openSession();
    public static void main(String[] args) {

             // diplayProductByCategory();
              //displayProductByBids();
              showTotalAmount();
               
                
                
                  

    }
    public static void diplayProductByCategory(){
         Transaction transaction=session.getTransaction();
                transaction=session.beginTransaction();
                Category category=(Category) session.get(Category.class, 1);
                String queryString="from Product p where ? member of p.categories";
                Query query=session.createQuery(queryString);
                query.setEntity(0, category);
                List<Product> products=query.list();
                for(Product product:products){
                    System.out.println("Products Name:"+product.getName()+", Product desc"+product.getDescription());
                }
                transaction.commit();
    }

    private static void displayProductByBids() {
          Transaction transaction=session.getTransaction();
                transaction=session.beginTransaction();
                Product product=(Product) session.get(Product.class, 10);
                String queryString="from BuyerBidProduct p where product_id like ?";
                Query query=session.createQuery(queryString);
                query.setInteger(0, product.getId());
                List<BuyerBidProduct> buyers=query.list();
                for(BuyerBidProduct buyer:buyers){
                    System.out.println("Buyer Id : "+buyer.getId().getBuyerId()+", Buyer Name : "+buyer.getBuyer().getUser().getUserName()+" , Product : "+buyer.getProduct().getName());
                }
                transaction.commit();
       
    }
    private static void showTotalAmount(){
                Transaction transaction=session.getTransaction();
                transaction=session.beginTransaction();
                Product product=(Product) session.get(Product.class, 10);
                String queryString=" Select sum(p.amount*p.quantity) from BuyerBidProduct p  where product_id like ?";
                Query query=session.createQuery(queryString);
                query.setInteger(0, product.getId());
                double totalAmount= (double) query.list().get(0);
               
                    System.out.println("Total Amount : "+totalAmount);
               
                transaction.commit();
        
    }
    
}
