package uitests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import uitests.pages.shop_main.Product;
import uitests.pages.shop_main.ProductPageShop;
import uitests.pages.shop_main.SortDirection;

import java.util.List;

public class ProductsTests extends BaseTests {

    @DataProvider
    public Object[][] sortingWays() {
        return new Object[][]{{"NAME_A_TO_Z"}, {"NAME_Z_TO_A"}, {"PRICE_LOW_TO_HIGH"}, {"PRICE_HIGH_TO_LOW"}};
    }

    @Test(priority = 1)
    public void allProductsTest() throws InterruptedException {
        ProductPageShop productList = new ProductPageShop();
        productList.extendProductList();
        productList.getProducts();
        Assert.assertEquals(productList.getProducts().size(), 30);
    }

//    @Test
//    public void checkSortingTest() throws InterruptedException {
//        ProductPageShop productPageShop = new ProductPageShop();
//        List<Product> productsAsIs = productPageShop.getProducts();
//
//        List<Product> productsAfterSorting = productPageShop.sortBy(SortDirection.PRICE_LOW_TO_HIGH).getProducts();
//
//        Collections.sort(productsAsIs, new Comparator<Product>() {
//            @Override
//            public int compare(Product o1, Product o2) {
//                return o1.getPrice().compareTo(o2.getPrice());
//            }
//        });
//
//        Assert.assertEquals(productsAsIs, productsAfterSorting);
//    }

    @Test(priority = 2, dataProvider = "sortingWays")
    public void checkSortingTestCustom(String sortingWays) throws InterruptedException {
        ProductPageShop productPageShop = new ProductPageShop();
        productPageShop.extendProductList();

        List<Product> List_1 = productPageShop.getProducts();
        List<Product> List_2 = productPageShop.sortBy((SortDirection.valueOf(sortingWays))).getProducts();
        List_1.sort(Product.getComparatorForSorting(SortDirection.valueOf(sortingWays)));

        // list debugging
        //System.out.println("Manual sorted : " + List_1);
        //System.out.println("On site sorted: " + List_2);
        //System.out.println("----------");

        Assert.assertEquals(List_1, List_2, sortingWays);
        //Assert.assertEqualsNoOrder(List_1, List_2);
    }
}
