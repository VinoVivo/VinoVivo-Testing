import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AdminReportsTest {
    
    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";
    private String reportsUrl = baseUrl + "/admin/reports";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        // Simular inicio de sesión como usuario administrador
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("admin");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("adminpassword");

        WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
        loginButton.click();

        // Navegar a la página de reportes
        driver.get(reportsUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void viewSalesReport() throws InterruptedException {
        // Seleccionar filtros y personalizar reporte
        WebElement wineTypeFilter = driver.findElement(By.id("wine-type-filter"));
        wineTypeFilter.sendKeys("Red Wine");

        WebElement yearFilter = driver.findElement(By.id("year-filter"));
        yearFilter.sendKeys("2023");

        // Refrescar el reporte
        WebElement refreshButton = driver.findElement(By.id("refresh-button"));
        refreshButton.click();

        // Verificar que el reporte se muestra correctamente
        Thread.sleep(2000); // Esperar a que cargue el reporte
        WebElement salesTable = driver.findElement(By.id("sales-table"));
        Assertions.assertTrue(salesTable.isDisplayed(), "Se debería mostrar la tabla de ventas");

        // Verificar el botón de exportar a PDF
        WebElement exportButton = driver.findElement(By.id("export-pdf-button"));
        Assertions.assertTrue(exportButton.isDisplayed(), "El botón de exportar a PDF debería estar visible");

        // Exportar el reporte a PDF
        exportButton.click();
        
    }
}
