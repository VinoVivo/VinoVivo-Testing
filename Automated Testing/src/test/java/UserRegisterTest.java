
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class UserRegisterTest {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";

    @BeforeEach
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximizar la ventana del navegador
        // Navega a la página principal
        driver.get(baseUrl);

        // Click en el ícono de usuario en el header
        WebElement userIcon = driver.findElement(By.tagName("svg"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userIcon);
        userIcon.click();

        // Esperar brevemente para que el botón de "registrarse" esté disponible
        Thread.sleep(3000);

        // Click en el botón "Registrarse"
        WebElement registerButton = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
        registerButton.click();

        // Esperar brevemente para que cargue el form
        Thread.sleep(2000);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void SuccessfulRegistration() {
        // Simular registro exitoso con información válida
        WebElement nameInput = driver.findElement(By.id("firstName"));
        nameInput.sendKeys("Usuario");

        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        lastNameInput.sendKeys("Ejemplo");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("Ejemplo");

        WebElement userNameInput = driver.findElement(By.id("username"));
        userNameInput.sendKeys("Ejemplo");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("12345");

        WebElement passwordConfirmInput = driver.findElement(By.id("password-confirm"));
        passwordConfirmInput.sendKeys("12345");

        WebElement createAccountButton = driver.findElement(By.xpath("//button[normalize-space()='Register']"));
        createAccountButton.click();

        // Verificar mensaje de éxito y redirección
        WebElement successMessage = driver.findElement(By.xpath("/html/body/div[1]/div[1]"));
        Assertions.assertTrue(successMessage.isDisplayed());

    }

    @Test
    public void EmptyMandatoryFields() {
        // Dejar todos los campos obligatorios vacíos y hacer clic en "crear cuenta"
        WebElement createAccountButton = driver.findElement(By.id("crear-cuenta"));
        createAccountButton.click();

        // Verificar mensajes de error para campos obligatorios
        WebElement errorMessage = driver.findElement(By.id("mensaje-error-campos-obligatorios"));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void PasswordConfirmationMismatch() throws InterruptedException {
        // Completar los campos obligatorios
        WebElement nameInput = driver.findElement(By.id("firstName"));
        nameInput.sendKeys("Usuario");

        // Esperar brevemente después de cada acción
        Thread.sleep(1000);

        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        lastNameInput.sendKeys("Ejemplo");

        Thread.sleep(1000);

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("Ejemplo@ejemplo");

        Thread.sleep(1000);

        WebElement userNameInput = driver.findElement(By.id("username"));
        userNameInput.sendKeys("Ejemplo");

        Thread.sleep(1000);

        // Poner errada la confirmación de la contraseña
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("contrasena123");

        Thread.sleep(1000);

        WebElement confirmPasswordInput = driver.findElement(By.id("password-confirm"));
        confirmPasswordInput.sendKeys("contrasena456");

        Thread.sleep(1000);

        WebElement createAccountButton = driver.findElement(By.tagName("button"));
        createAccountButton.click();

        // Esperar brevemente para que el mensaje de error se muestre
        Thread.sleep(2000);

        // Verificar mensaje de error por contraseña no coincidente
        WebElement errorMessage = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/form/div[5]/div[2]"));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void InvalidEmailFormat() {
        // Simular formato de correo electrónico inválido
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("correo.invalido");

        WebElement createAccountButton = driver.findElement(By.id("crear-cuenta"));
        createAccountButton.click();

        // Verificar mensaje de error por formato de correo electrónico inválido
        WebElement errorMessage = driver.findElement(By.id("mensaje-error-formato-correo"));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void InsufficientCharactersInNameOrLastName() {
        // Simular caracteres insuficientes en nombre y apellido
        WebElement nameInput = driver.findElement(By.id("nombre"));
        nameInput.sendKeys("J");

        WebElement lastNameInput = driver.findElement(By.id("apellido"));
        lastNameInput.sendKeys("S");

        WebElement createAccountButton = driver.findElement(By.id("crear-cuenta"));
        createAccountButton.click();

        // Verificar mensaje de error por caracteres insuficientes
        WebElement errorMessage = driver.findElement(By.id("mensaje-error-caracteres-insuficientes"));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void OptionalProfileImage() {
        // Dejar el campo de imagen de perfil vacío y completar otros campos obligatorios
        WebElement nameInput = driver.findElement(By.id("nombre"));
        nameInput.sendKeys("Usuario");

        WebElement lastNameInput = driver.findElement(By.id("apellido"));
        lastNameInput.sendKeys("Ejemplo");

        // Simular otros campos como correo electrónico, contraseña, etc.

        WebElement createAccountButton = driver.findElement(By.id("crear-cuenta"));
        createAccountButton.click();

        // Verificar mensaje de éxito y redirección
        WebElement successMessage = driver.findElement(By.id("mensaje-exito"));
        Assertions.assertTrue(successMessage.isDisplayed());
    }

    @Test
    public void EmailAlreadyRegistered() {
        // Simular correo electrónico ya registrado
        WebElement nameInput = driver.findElement(By.id("firstName"));
        nameInput.sendKeys("Usuario");

        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        lastNameInput.sendKeys("Ejemplo");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("Ejemplo");

        WebElement userNameInput = driver.findElement(By.id("username"));
        userNameInput.sendKeys("Ejemplo");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("12345");

        WebElement passwordConfirmInput = driver.findElement(By.id("password-confirm"));
        passwordConfirmInput.sendKeys("12345");

        WebElement createAccountButton = driver.findElement(By.xpath("//button[normalize-space()='Register']"));
        createAccountButton.click();
        
        // Verificar mensaje de error por correo electrónico ya registrado
        WebElement errorMessage = driver.findElement(By.id("mensaje-error-correo-ya-registrado"));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }


}