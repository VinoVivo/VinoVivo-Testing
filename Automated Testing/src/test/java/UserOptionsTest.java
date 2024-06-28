import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class UserOptionsTest {
    
    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app/";

    
    @BeforeEach
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximizar la ventana del navegador
        // Navega a la página principal
        driver.get(baseUrl);

         // Click en el ícono de usuario en el header
        WebElement userIcon = driver.findElement(By.xpath("/html/body/div[1]/header/div/div[3]/svg/path"));
        userIcon.click();
        
         // Esperar brevemente para que el botón de "registrarse" esté disponible
        Thread.sleep(2000);        
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void verifyUserOptions() throws InterruptedException {
        // Ingresar con usuario y contraseña
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("jere1");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("123456");

        WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
        loginButton.click();

        // Esperar brevemente para la autenticación
        Thread.sleep(2000);

        // Crear una instancia de Actions para realizar hover
        Actions actions = new Actions(driver);

        // Hover sobre el botón de usuario
        WebElement userDropdownButton = driver.findElement(By.xpath("//*[@id='hs-dropdown-hover-event']"));
        actions.moveToElement(userDropdownButton).perform();

        // Esperar brevemente para que el dropdown esté visible
        Thread.sleep(2000);

        // Verificar y hacer clic en "Mi Perfil"
        WebElement myProfileOption = driver.findElement(By.xpath("(//a[normalize-space()='Mi Perfil'])[1]"));
        myProfileOption.click();

        // Esperar y verificar que se haya redirigido a "Mi Perfil"
        Thread.sleep(2000);
        Assertions.assertEquals("https://vivo-front-4a6f.vercel.app/user-settings", driver.getCurrentUrl(), "Should be redirected to 'Mi Perfil'");

        // Hover sobre el botón de usuario nuevamente
        actions.moveToElement(userDropdownButton).perform();
        Thread.sleep(2000);

        // Verificar y hacer clic en "Mis Compras"
        WebElement myPurchasesOption = driver.findElement(By.xpath("//a[normalize-space()='Mis Compras']"));
        myPurchasesOption.click();

        // Esperar y verificar que se haya redirigido a "Mis Compras"
        Thread.sleep(2000);
        Assertions.assertEquals("https://vivo-front-4a6f.vercel.app/orders", driver.getCurrentUrl(), "Should be redirected to 'Mis Compras'");

        // Hover sobre el botón de usuario nuevamente
        actions.moveToElement(userDropdownButton).perform();
        Thread.sleep(2000);

        // Verificar y hacer clic en "Cerrar Sesión"
        WebElement logoutOption = driver.findElement(By.xpath("(//a[normalize-space()='Cerrar Sesión'])[1]"));
        logoutOption.click();

        // Esperar y verificar que se haya cerrado la sesión
        Thread.sleep(2000);
        Assertions.assertEquals(baseUrl, driver.getCurrentUrl(), "Should be redirected to home page after logout");
    }
}

