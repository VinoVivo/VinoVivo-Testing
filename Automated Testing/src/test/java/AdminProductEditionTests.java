import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class AdminProductEditionTests {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";
    private String ProductUrl = baseUrl + "/admin/productos";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        // Iniciar sesión como administrador
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("test");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("1234");

        WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
        loginButton.click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void viewMyProductsWithProducts() throws InterruptedException {

        // Navegar a la página "Mis Productos"
        
        // Crear una instancia de Actions para realizar hover
        Actions actions = new Actions(driver);

        // Hover sobre el botón de usuario 
        WebElement userDropdownButton = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        actions.moveToElement(userDropdownButton).perform();

        // Esperar brevemente para que el dropdown esté visible
        Thread.sleep(2000);

        // Verificar y hacer clic en "productos"
        WebElement myProfileOption = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        myProfileOption.click();
        
        // Esperar y verificar que se haya redirigido a "Productos"
        Thread.sleep(2000);
        Assertions.assertEquals(ProductUrl, driver.getCurrentUrl(), "Debería estar redirigido a la página de productos");
        
        
        // Verificar el título
        WebElement title = driver.findElement(By.tagName("h1"));
        Assertions.assertEquals("Mis Productos", title.getText(), "El título debería ser 'Mis Productos'");

        // Verificar que se muestren las tarjetas de los productos con botones de editar y eliminar
        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        Assertions.assertFalse(productCards.isEmpty(), "Deberían mostrarse las tarjetas de los productos");

        for (WebElement productCard : productCards) {
            WebElement editButton = productCard.findElement(By.className("edit-button"));
            WebElement deleteIcon = productCard.findElement(By.className("delete-icon"));
            Assertions.assertTrue(editButton.isDisplayed(), "El botón de editar debería mostrarse");
            Assertions.assertTrue(deleteIcon.isDisplayed(), "El icono de eliminar debería mostrarse");
        }

        // Verificar la paginación
        List<WebElement> pages = driver.findElements(By.className("pagination-page"));
        Assertions.assertTrue(pages.size() > 1, "Debería haber paginación si hay más de 8 productos");
    }

    @Test
    public void viewMyProductsWithoutProducts() throws InterruptedException {
        // Navegar a la página "Mis Productos"
        
        // Crear una instancia de Actions para realizar hover
        Actions actions = new Actions(driver);

        // Hover sobre el botón de usuario 
        WebElement userDropdownButton = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        actions.moveToElement(userDropdownButton).perform();

        // Esperar brevemente para que el dropdown esté visible
        Thread.sleep(2000);

        // Verificar y hacer clic en "productos"
        WebElement myProfileOption = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        myProfileOption.click();
        
        // Esperar y verificar que se haya redirigido a "Productos"
        Thread.sleep(2000);
        Assertions.assertEquals(ProductUrl, driver.getCurrentUrl(), "Debería estar redirigido a la página de productos");

        // Verificar el mensaje indicando que no hay productos
        WebElement noProductsMessage = driver.findElement(By.className("no-products-message"));
        Assertions.assertEquals("Aún no tiene productos creados, da click en ‘crear producto’", noProductsMessage.getText(), "Debería mostrarse el mensaje de que no hay productos");

        // Verificar el botón "Crear Producto"
        WebElement createProductButton = driver.findElement(By.xpath("//button[normalize-space()='Crear Producto']"));
        Assertions.assertTrue(createProductButton.isDisplayed(), "El botón 'Crear Producto' debería mostrarse");
    }

    @Test
    public void editProduct() throws InterruptedException {
        // Navegar a la página "Mis Productos"
        
        // Crear una instancia de Actions para realizar hover
        Actions actions = new Actions(driver);

        // Hover sobre el botón de usuario 
        WebElement userDropdownButton = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        actions.moveToElement(userDropdownButton).perform();

        // Esperar brevemente para que el dropdown esté visible
        Thread.sleep(2000);

        // Verificar y hacer clic en "productos"
        WebElement myProfileOption = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        myProfileOption.click();
        
        // Esperar y verificar que se haya redirigido a "Productos"
        Thread.sleep(2000);
        Assertions.assertEquals(ProductUrl, driver.getCurrentUrl(), "Debería estar redirigido a la página de productos");
        // Hacer clic en el botón de editar en una tarjeta de producto
        WebElement editButton = driver.findElement(By.className("edit-button"));
        editButton.click();

        // Esperar a que la página cargue
        Thread.sleep(2000);

        // Verificar la redirección a la página de edición del producto
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/product/edit"), "La URL debería contener '/product/edit'");
    }

    @Test
    public void createProduct() throws InterruptedException {
        // Navegar a la página "Mis Productos"
        
        // Crear una instancia de Actions para realizar hover
        Actions actions = new Actions(driver);

        // Hover sobre el botón de usuario 
        WebElement userDropdownButton = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        actions.moveToElement(userDropdownButton).perform();

        // Esperar brevemente para que el dropdown esté visible
        Thread.sleep(2000);

        // Verificar y hacer clic en "productos"
        WebElement myProfileOption = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        myProfileOption.click();
        
        // Esperar y verificar que se haya redirigido a "Productos"
        Thread.sleep(2000);
        Assertions.assertEquals(ProductUrl, driver.getCurrentUrl(), "Debería estar redirigido a la página de productos");
        // Hacer clic en el botón "Crear Producto"
        WebElement createProductButton = driver.findElement(By.xpath("//button[normalize-space()='Crear Producto']"));
        createProductButton.click();

        // Esperar a que la página de creación de productos cargue
        Thread.sleep(2000);

        // Verificar la redirección a la ruta de creación de productos
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/product/register"), "La URL debería contener '/product/register'");
    }

    @Test
    public void deleteProduct() throws InterruptedException {
        // Navegar a la página "Mis Productos"
        
        // Crear una instancia de Actions para realizar hover
        Actions actions = new Actions(driver);

        // Hover sobre el botón de usuario 
        WebElement userDropdownButton = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        actions.moveToElement(userDropdownButton).perform();

        // Esperar brevemente para que el dropdown esté visible
        Thread.sleep(2000);

        // Verificar y hacer clic en "productos"
        WebElement myProfileOption = driver.findElement(By.xpath("//a[normalize-space()='Productos']"));
        myProfileOption.click();
        
        // Esperar y verificar que se haya redirigido a "Productos"
        Thread.sleep(2000);
        Assertions.assertEquals(ProductUrl, driver.getCurrentUrl(), "Debería estar redirigido a la página de productos");

        // Hacer clic en el icono de eliminar en una tarjeta de producto
        WebElement deleteIcon = driver.findElement(By.className("delete-icon"));
        deleteIcon.click();

        // Esperar a que aparezca el cuadro de confirmación
        Thread.sleep(1000);

        // Confirmar la eliminación
        WebElement confirmButton = driver.findElement(By.xpath("//button[normalize-space()='Confirmar']"));
        confirmButton.click();

        // Esperar a que el producto sea eliminado
        Thread.sleep(2000);

        // Verificar que el producto ya no esté en la lista
        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        Assertions.assertTrue(productCards.isEmpty(), "La tarjeta del producto no debería mostrarse después de eliminarlo");
    }

    @Test
    public void paginateProducts() throws InterruptedException {
        // Navegar a la página "Mis Productos"
        WebElement myProductsLink = driver.findElement(By.linkText("Mis Productos"));
        myProductsLink.click();

        // Esperar a que la página cargue
        Thread.sleep(2000);

        // Verificar la disposición de los productos en la página
        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        Assertions.assertTrue(productCards.size() <= 8, "Deberían mostrarse hasta 8 productos por página");

        // Verificar la paginación
        List<WebElement> pages = driver.findElements(By.className("pagination-page"));
        Assertions.assertTrue(pages.size() > 1, "Debería haber paginación si hay más de 8 productos");

        // Navegar entre las páginas de productos
        WebElement nextPage = driver.findElement(By.className("pagination-next"));
        nextPage.click();

        // Esperar a que la página cargue
        Thread.sleep(2000);

        // Verificar que la segunda página se muestra correctamente
        productCards = driver.findElements(By.className("product-card"));
        Assertions.assertTrue(productCards.size() <= 8, "Deberían mostrarse hasta 8 productos por página en la segunda página");
    }
    


}
