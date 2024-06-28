import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class ProductsPageTest {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";
    private String productsUrl = baseUrl + "/products";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximizar la ventana del navegador
        // Navega a la página principal
        driver.get(productsUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void verifyShowMoreButton() {
        // Verificar que el botón "Mostrar más" está visible en la página de productos
        WebElement showMoreButton = driver.findElement(By.id("show-more-button"));
        Assertions.assertTrue(showMoreButton.isDisplayed(), "El botón 'Mostrar más' debería estar visible");
    }

    @Test
    public void accessAllProductsView() {
        // Hacer clic en el botón "Mostrar más"
        WebElement showMoreButton = driver.findElement(By.id("show-more-button"));
        showMoreButton.click();

        // Verificar que el usuario es dirigido a una nueva vista que muestra todos los productos sin clasificación
        Assertions.assertTrue(driver.getCurrentUrl().contains("/products/all"), "Debería estar en la vista de todos los productos");
    }

    @Test
    public void verifyProductsViewTitle() {
        // Confirmar que la nueva vista tiene un título que indica que el usuario está en la vista de "Productos"
        WebElement titleElement = driver.findElement(By.tagName("h1"));
        Assertions.assertEquals("Productos", titleElement.getText(), "El título de la vista debería ser 'Productos'");
    }

    @Test
    public void verifyProductsDisplayOnHomePage() {
        // Confirmar que se muestran 8 productos en la página inicial de productos
        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        Assertions.assertEquals(8, productCards.size(), "Deberían mostrarse 8 productos en la página inicial");

        // Verificar que los productos están organizados en dos filas de 4 productos cada una
        WebElement firstRow = driver.findElement(By.xpath("(//div[@class='product-row'])[1]"));
        List<WebElement> productsInFirstRow = firstRow.findElements(By.className("product-card"));
        Assertions.assertEquals(4, productsInFirstRow.size(), "Deberían haber 4 productos en la primera fila");
    }

    @Test
    public void verifyPagination() {
        // Desplazarse al final de la lista de productos
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Verificar que hay una opción de paginación
        WebElement pagination = driver.findElement(By.className("pagination"));
        Assertions.assertTrue(pagination.isDisplayed(), "Debería haber una opción de paginación");

        // Hacer clic en la opción de paginación
        WebElement nextPageButton = pagination.findElement(By.className("next-page-button"));
        nextPageButton.click();

        // Verificar que se muestran otros 8 productos (si hay más de 8 productos en total)
        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        Assertions.assertTrue(productCards.size() > 8, "Deberían mostrarse más de 8 productos después de hacer clic en la paginación");
    }
}