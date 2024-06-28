import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class ContactPageTest {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";
    private String contactUrl = baseUrl + "/contact";

    private void llenarCampo(String campoId, String valor) {
        WebElement campo = driver.findElement(By.id(campoId));
        campo.sendKeys(valor);
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximizar la ventana del navegador
        // Navega a la página principal
        driver.get(baseUrl);
        // Encuentra y haz clic en el botón de contacto en el header para dirigirse a la página
        WebElement conceptButton = driver.findElement(By.cssSelector("a[href='/contact']"));
        conceptButton.click();
        // Espera explícita de 2 segundos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Verifica que estamos en la página de concepto
        assertEquals(contactUrl, driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCompleteForm() {
        // Llenar el campo Nombre
        llenarCampo("name", "Juanito Juanes");
        esperar(2000); // Esperar 2 segundos

        // Llenar el campo Celular
        llenarCampo("number", "3512452033");
        esperar(2000); // Esperar 2 segundos

        // Llenar el campo Email
        llenarCampo("usermail", "jujuanes@gmail.com");
        esperar(2000); // Esperar 2 segundos

        // Llenar el campo Mensaje
        llenarCampo("message-user", "Este es mi mensaje de prueba");
        esperar(2000); // Esperar 2 segundos

        // Enviar el formulario
        //WebElement enviarButton = driver.findElement(By.xpath("//button[contains(text(), 'Enviar')]"));
        //enviarButton.click();

    }


    @Test
    public void testInvalidEmail() throws InterruptedException {


        // Llenar el campo Nombre
        llenarCampo("name", "Juanito Juanes");
        esperar(2000); // Esperar 2 segundos

        // Llenar el campo Celular
        llenarCampo("number", "3512452033");
        esperar(2000); // Esperar 2 segundos

        // Ingresar un email inválido
        llenarCampo("usermail", "correo-invalido");
        esperar(2000); // Esperar 2 segundos

        // Llenar el campo Mensaje
        llenarCampo("message-user", "Este es mi mensaje de prueba de correo falso");
        esperar(2000); // Esperar 2 segundos

        // Enviar el formulario
        WebElement enviarButton = driver.findElement(By.xpath("//form[@class='shadow-lg rounded-md bg-background p-4']//button[contains(text(), 'Enviar')]"));
        enviarButton.click();

        // Esperar a que aparezca el mensaje de error
        esperar(2000);

        // Verificar que aparezca el mensaje de error esperado
        WebElement mensajeError = driver.findElement(By.id("undefined-form-item-message"));
        assertTrue(mensajeError.isDisplayed());
        assertEquals("Ingrese un correo valido", mensajeError.getText());
    }




}