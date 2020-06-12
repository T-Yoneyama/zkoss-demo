package local.satoseiki.demo.paging;

import java.util.List;

 
public class PagingViewModel {
    private PurchaseData data = new PurchaseData();
     
    public List<Purchase> getAllPurchases() {
        return data.getAllPurchases();
    }
     
    public List<String> getAvailableItems() {
        return data.getAvailableItems();
    }
}