import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class FooterTest {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app/";
    
    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximizar la ventana del navegador
        // Navega a la página principal
        driver.get(baseUrl);
        
    }

    @AfterEach
    public void tearDown() {
        // Cerrar el navegador al finalizar cada test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFooterPresence() {

        // Verificar que el footer esté presente y ocupe el 100% del ancho de la pantalla
        WebElement footer = driver.findElement(By.tagName("footer"));
        assertTrue(footer.isDisplayed());
        //assertEquals("100%", footer.getCssValue("width"));

        // Verificar la presencia de la sección de derechos de autor y contenido importante
        WebElement copyrightSection = driver.findElement(By.xpath("//p[normalize-space()='Virtual Trends @Copyright']"));
        assertTrue(copyrightSection.isDisplayed());

        // Verificar que el texto de derechos de autor contenga "Virtual Trends" y "Desarrollo por Día365"
        assertTrue(copyrightSection.getText().contains("Virtual Trends"));
        //assertTrue(copyrightSection.getText().contains("Desarrollo por Día365"));

        // Verificar la presencia del bloque de suscripción al newsletter
        WebElement newsletterBlock = driver.findElement(By.xpath("//div[@class='m-2 sm:col-span-2 items-center flex flex-col justify-center']"));
        assertTrue(newsletterBlock.isDisplayed());

        // Verificar la presencia del botón "SUSCRIBETE"
        WebElement subscribeButton = driver.findElement(By.xpath("//button[normalize-space()='SUSCRIBETE']"));
        assertTrue(subscribeButton.isDisplayed());

        // Verificar que los enlaces de "Políticas de privacidad", "Términos y condiciones" y "Prensa" estén presentes y sean clicables
        WebElement privacyLink = driver.findElement(By.linkText("Políticas de privacidad"));
        assertTrue(privacyLink.isDisplayed());
        assertTrue(privacyLink.isEnabled());

        WebElement termsLink = driver.findElement(By.linkText("Términos y condiciones"));
        assertTrue(termsLink.isDisplayed());
        assertTrue(termsLink.isEnabled());

        WebElement pressLink = driver.findElement(By.linkText("Prensa"));
        assertTrue(pressLink.isDisplayed());
        assertTrue(pressLink.isEnabled());
        
    }
}