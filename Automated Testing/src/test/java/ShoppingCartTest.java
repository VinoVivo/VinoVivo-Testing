import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;


public class ShoppingCartTest {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";
    private String productsUrl = baseUrl + "/products";
    private String username = "jere1";
    private String password = "123456";

    @BeforeEach
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    
        // Navegar a la página de inicio de sesión
        driver.get(baseUrl);

        // Click en el ícono de usuario en el header
        WebElement userIcon = driver.findElement(By.xpath("/html/body/div[1]/header/div/div[3]/svg/path"));
        userIcon.click();
        
         // Esperar brevemente para que el botón de "registrarse" esté disponible
        Thread.sleep(2000);  

        // Iniciar sesión
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();

        // Esperar a que la página cargue después del inicio de sesión (puedes ajustar el tiempo según necesites)
        try {
            Thread.sleep(2000); // Espera para asegurar que se cargue la página después del inicio de sesión
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Navegar a la página de productos
        driver.get(productsUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }  

    @Test
    public void verifyShoppingCartDropdown() {
        // Verificar que el usuario puede ver los productos agregados al carrito en un desplegable lateral
        WebElement cartIcon = driver.findElement(By.id("cart-icon"));
        cartIcon.click();

        WebElement cartDropdown = driver.findElement(By.id("cart-dropdown"));
        Assertions.assertTrue(cartDropdown.isDisplayed(), "El carrito desplegable debería estar visible");
    }

    @Test
    public void addProductsToShoppingCart() {
        // Verificar que el usuario puede sumar unidades de un producto en el carrito
        WebElement increaseQuantityButton = driver.findElement(By.xpath("//button[@class='increase-quantity-button']"));
        increaseQuantityButton.click();

        WebElement quantityInput = driver.findElement(By.xpath("//input[@class='quantity-input']"));
        int quantity = Integer.parseInt(quantityInput.getAttribute("value"));
        Assertions.assertEquals(2, quantity, "La cantidad del producto debería incrementarse a 2");
    }

    @Test
    public void decreaseProductsInShoppingCart() {
        // Verificar que el usuario puede restar unidades de un producto en el carrito
        WebElement decreaseQuantityButton = driver.findElement(By.xpath("//button[@class='decrease-quantity-button']"));
        decreaseQuantityButton.click();

        WebElement quantityInput = driver.findElement(By.xpath("//input[@class='quantity-input']"));
        int quantity = Integer.parseInt(quantityInput.getAttribute("value"));
        Assertions.assertEquals(0, quantity, "La cantidad del producto debería decrementarse a 0");
    }

    @Test
    public void removeProductFromShoppingCart() {
        // Verificar que el usuario puede eliminar un producto del carrito
        WebElement removeButton = driver.findElement(By.xpath("//button[@class='remove-button']"));
        removeButton.click();

        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        Assertions.assertEquals(0, productCards.size(), "No deberían quedar productos en el carrito");
    }

    @Test
    public void closeShoppingCart() {
        // Verificar que el usuario puede cerrar el carrito de compras desplegable
        WebElement cartIcon = driver.findElement(By.id("cart-icon"));
        cartIcon.click();

        WebElement closeButton = driver.findElement(By.xpath("//button[@class='close-button']"));
        closeButton.click();

        List<WebElement> cartDropdown = driver.findElements(By.id("cart-dropdown"));
        Assertions.assertEquals(0, cartDropdown.size(), "El carrito desplegable debería cerrarse");
    }

    @Test
    public void continueShopping() {
        // Verificar que el usuario puede continuar comprando sin cerrar el carrito
        WebElement continueShoppingButton = driver.findElement(By.xpath("//button[@class='continue-shopping-button']"));
        continueShoppingButton.click();

        List<WebElement> cartDropdown = driver.findElements(By.id("cart-dropdown"));
        Assertions.assertEquals(0, cartDropdown.size(), "El carrito desplegable debería cerrarse o permanecer abierto según implementación");
    }

    @Test
    public void proceedToCheckout() {
        // Verificar que el usuario puede proceder al proceso de compra desde el carrito
        WebElement checkoutButton = driver.findElement(By.xpath("//button[@class='checkout-button']"));
        checkoutButton.click();

        
    }

    @Test
    public void viewCostsInShoppingCart() {
        // Verificar que el usuario puede ver el costo subtotal e individual de los productos en el carrito
        WebElement subtotalElement = driver.findElement(By.xpath("//span[@class='subtotal']"));
        String subtotal = subtotalElement.getText();
        Assertions.assertFalse(subtotal.isEmpty(), "Debe mostrarse el subtotal del carrito");

        WebElement totalElement = driver.findElement(By.xpath("//span[@class='total']"));
        String total = totalElement.getText();
        Assertions.assertFalse(total.isEmpty(), "Debe mostrarse el total del carrito");
    }


}
