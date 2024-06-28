import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;




public class HeaderTests {
    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app";
    
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximizar la ventana del navegador
        // Navega a la página principal
        driver.get(baseUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }



    @Test
    public void testHeaderIsFixedAndResponsive() {
        WebElement header = driver.findElement(By.tagName("header"));
        
        // Verifica que el header ocupa el 100% del ancho de la pantalla
        assertEquals("100%", header.getCssValue("width"));
        
        // Verifica que el header es fijo al hacer scroll
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");
        String position = header.getCssValue("position");
        assertTrue(position.equals("fixed") || position.equals("sticky"));
    }

    @Test
    public void testHeaderConsistencyAndUsability() {
        WebElement header = driver.findElement(By.tagName("header"));
        List<WebElement> navLinks = header.findElements(By.tagName("a"));
        
        // Verifica que los enlaces de navegación existen y son visibles
        assertTrue(navLinks.size() > 0);
        for (WebElement link : navLinks) {
            assertTrue(link.isDisplayed());
        }
    }

    @Test
    public void testHeaderResponsiveness() {
        WebElement header = driver.findElement(By.tagName("header"));
        WebElement hamburgerMenu = header.findElement(By.xpath("//button[@class='text-white text-2xl cursor-pointer hover:text-gray-300']//*[name()='svg']"));
        
        // Cambiar a una resolución de dispositivo móvil
        driver.manage().window().setSize(new Dimension(375, 812));

        try {
            // Espera explícita de 2 segundos
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Manejo de la excepción (puede imprimir un mensaje de error o lanzarla nuevamente si es necesario)
            e.printStackTrace();
        }
        
        // Verifica que el menú de hamburguesa es visible
        assertTrue(hamburgerMenu.isDisplayed());
        
        // Cambiar a una resolución de escritorio
        driver.manage().window().setSize(new Dimension(1920, 1080));
        
        // Verifica que el menú de hamburguesa no es visible
        assertFalse(hamburgerMenu.isDisplayed());
    }

    @Test
    public void testHeaderBlocks() {
        WebElement header = driver.findElement(By.tagName("header"));
        
        // Bloque Izquierdo: Logo y Lema
        WebElement logo = header.findElement(By.xpath("//img[@alt='logo']"));
        assertTrue(logo.isDisplayed());
        
        
        // Verificar redirección al hacer clic en el logo o lema
        logo.click();
        assertEquals(baseUrl, driver.getCurrentUrl());
        
        // Bloque Central: Enlaces de navegación
        List<WebElement> navLinks = header.findElements(By.xpath("//div[@class='hidden md:flex items-center space-x-8']"));
        assertTrue(navLinks.size() > 0);
        
        // Bloque Derecho: Buscador, íconos de usuario y carrito
        //WebElement searchBox = header.findElement(By.tagName("input"));
        WebElement userIcon = header.findElement(By.xpath("//div[@class='flex space-x-4 items-center']//*[name()='svg']"));
        WebElement cartIcon = header.findElement(By.xpath("//*[name()='path' and contains(@d,'M528.12 30')]"));
        
        //assertTrue(searchBox.isDisplayed());
        assertTrue(userIcon.isDisplayed());
        assertTrue(cartIcon.isDisplayed());
    }

    @Test
    public void testProductDropdown() {

        // Encontrar y hacer clic en el botón de Productos en el navbar
        WebElement productButton = driver.findElement(By.xpath("//button[@id='hs-dropdown-hover-event']"));
        productButton.click();

        // Esperar un momento para que el desplegable se abra 
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Encontrar los elementos del desplegable
        List<WebElement> dropdownItems = driver.findElements(By.xpath("//button[@id='hs-dropdown-hover-event']"));

        // Verificar que los elementos esperados estén presentes
        boolean hasTinto = false, hasBlanco = false, hasRosado = false, hasEspumantes = false, hasTodos = false;

        for (WebElement item : dropdownItems) {
            String text = item.getText().toLowerCase();
            if (text.contains("tinto")) {
                hasTinto = true;
            } else if (text.contains("blanco")) {
                hasBlanco = true;
            } else if (text.contains("rosado")) {
                hasRosado = true;
            } else if (text.contains("espumantes")) {
                hasEspumantes = true;
            } else if (text.contains("todos")) {
                hasTodos = true;
            }
        }

        // Imprimir resultados de la verificación
        System.out.println("¿Se encontró Tinto? " + hasTinto);
        System.out.println("¿Se encontró Blanco? " + hasBlanco);
        System.out.println("¿Se encontró Rosado? " + hasRosado);
        System.out.println("¿Se encontró Espumosos? " + hasEspumantes);
        System.out.println("¿Se encontró Todos? " + hasTodos);
    
    }

    @Test
    public void testProductDropdownOnHover() {

        // Encontrar el botón de Productos en el navbar
        WebElement productButton = driver.findElement(By.xpath("//button[@id='hs-dropdown-hover-event']"));

        // Realizar hover sobre el botón de Productos
        Actions actions = new Actions(driver);
        actions.moveToElement(productButton).perform();

         // Esperar un breve tiempo para permitir que el menú desplegable se muestre
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Encontrar los elementos del desplegable
        List<WebElement> dropdownItems = driver.findElements(By.xpath("//button[@id='hs-dropdown-hover-event']"));

        // Verificar que los elementos esperados estén presentes
        boolean hasTinto = false, hasBlanco = false, hasRosado = false, hasEspumantes = false, hasTodos = false;

        for (WebElement item : dropdownItems) {
            String text = item.getText().toLowerCase();
            if (text.contains("tinto")) {
                hasTinto = true;
            } else if (text.contains("blanco")) {
                hasBlanco = true;
            } else if (text.contains("rosado")) {
                hasRosado = true;
            } else if (text.contains("espumantes")) {
                hasEspumantes = true;
            } else if (text.contains("todos")) {
                hasTodos = true;
            }
        }

        // Imprimir resultados de la verificación
        System.out.println("¿Se encontró Tinto? " + hasTinto);
        System.out.println("¿Se encontró Blanco? " + hasBlanco);
        System.out.println("¿Se encontró Rosado? " + hasRosado);
        System.out.println("¿Se encontró Espumosos? " + hasEspumantes);
        System.out.println("¿Se encontró Todos? " + hasTodos);

        }

        @Test
        public void testProductDropdownNavigation() throws InterruptedException {
            // Encontrar el botón de Productos en el navbar
            WebElement productButton = driver.findElement(By.xpath("//button[@id='hs-dropdown-hover-event']"));
        
            // Realizar hover sobre el botón de Productos
            Actions actions = new Actions(driver);
            actions.moveToElement(productButton).perform();
        
            // Esperar un momento para que las opciones bajen
            Thread.sleep(1000);
        
            // Listado de opciones a verificar
            String[] opciones = {"Tinto", "Blanco", "Rosado", "Espumantes", "Ver todos"};
        
            // Iterar sobre cada opción
            for (String opcion : opciones) {
                // Construir el XPath dinámico
                String xpathOpcion = String.format("//a[normalize-space()='%s']", opcion);
        
                // Espera explícita para asegurar que el elemento esté presente antes de intentar interactuar con él
                Thread.sleep(1000);
        
                // Encontrar el elemento del menú desplegable según la opción actual
                WebElement dropdownOpcion = driver.findElement(By.xpath(xpathOpcion));
        
                // Hacer clic en la opción del menú desplegable 
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownOpcion);
        
                // Esperar a que la página cargue completamente
                waitForPageLoad();
        
                // Añadir una espera de 2 segundos para permitir que los vinos se carguen
                Thread.sleep(2000);
        
                // Verificar que la URL actual coincide con la URL esperada de la página correspondiente
                String expectedUrl;
                switch (opcion.toLowerCase()) {
                    case "tinto":
                        expectedUrl = baseUrl + "/type/3"; 
                        break;
                    case "blanco":
                        expectedUrl = baseUrl + "/type/2";
                        break;
                    case "rosado":
                        expectedUrl = baseUrl + "/type/1";
                        break;
                    case "espumantes":
                        expectedUrl = baseUrl + "/type/4";
                        break;
                    case "ver todos":
                        expectedUrl = baseUrl + "/products";
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de vino no reconocido: " + opcion);
                }
        
                assertEquals(expectedUrl, driver.getCurrentUrl(),
                        String.format("La opción '%s' no dirige a la página esperada: %s", opcion, expectedUrl));
        
                // Volver atrás en el navegador para la siguiente iteración
                driver.navigate().back();
        
                // Esperar un momento para permitir la navegación anterior
                Thread.sleep(1000);
        
                // Realizar hover sobre el botón de Productos nuevamente para la siguiente iteración
                actions.moveToElement(productButton).perform();
                Thread.sleep(1000);
            }
        }
        
        private void waitForPageLoad() {
            // Esperar a que la página esté completamente cargada usando JavaScriptExecutor
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            int timeoutInSeconds = 10;
            String documentReadyState = (String) jsExecutor.executeScript("return document.readyState");
            long endTime = System.currentTimeMillis() + (timeoutInSeconds * 1000);
        
            while (!documentReadyState.equals("complete") && System.currentTimeMillis() < endTime) {
                try {
                    Thread.sleep(500); // Esperar medio segundo antes de verificar de nuevo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                documentReadyState = (String) jsExecutor.executeScript("return document.readyState");
            }
        
            // Esperar un breve momento adicional para asegurar la estabilidad de la carga de la página
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    @Test
    public void testContactButtonRedirectsToContactPage() {

        // Encontrar el botón de Contacto en el encabezado y hacer clic en él
        driver.findElement(By.xpath("//span[normalize-space()='CONTACTO']")).click();

        // Esperar un breve tiempo para que la página carge correctamente
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar que la URL actual coincide con la URL de la página de Contacto
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseUrl + "contact";
        assertEquals(expectedUrl, currentUrl, "El botón de Contacto no dirige a la página de Contacto");
    }

    }
