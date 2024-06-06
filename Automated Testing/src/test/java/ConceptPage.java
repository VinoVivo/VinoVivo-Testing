import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ConceptPage {

    private WebDriver driver;
    private String baseUrl = "https://vivo-front-4a6f.vercel.app/";
    private String conceptUrl = baseUrl + "concept";

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
    public void testNuestraHistoriaSection() {
        // Encuentra y haz clic en el botón de concepto en el header para dirigirse a la página
        WebElement conceptButton = driver.findElement(By.cssSelector("a[href='/concept']"));
        conceptButton.click();

        // Espera explícita de 2 segundos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica que estamos en la página de concepto
        assertEquals(conceptUrl, driver.getCurrentUrl());

        // Validar la Sección "Nuestra Historia"
        WebElement historiaSection = driver.findElement(By.xpath("//div[contains(@class,'w-full px-5 sm:px-2')]/section"));
        assertTrue(historiaSection.isDisplayed(), "La sección 'Nuestra Historia' no está visible");

        // Verificar el título "Nuestra Historia"
        WebElement historiaTitle = historiaSection.findElement(By.tagName("h2"));
        assertTrue(historiaTitle.isDisplayed(), "El título de la sección 'Nuestra Historia' no está visible");
        assertEquals("Nuestra Historia", historiaTitle.getText(), "El título de la sección no es 'Nuestra Historia'");

        // Verificar la presencia de texto de la historia del restaurante
        WebElement historiaText = historiaSection.findElement(By.xpath(".//p[contains(@class,'text-secondary text-sm sm:py-10 sm:text-md md:py-0')]"));
        assertTrue(!historiaText.getText().isEmpty(), "El texto de la historia del restaurante está vacío");

        // Verificar que la sección contiene al menos 3 imágenes únicas
        List<WebElement> historiaImages = historiaSection.findElements(By.tagName("img"));
        Set<String> imageSourcesHistoria = new HashSet<>();
        for (WebElement img : historiaImages) {
            String src = img.getAttribute("src");
            System.out.println("Imagen encontrada en 'Nuestra Historia': " + src);
            assertTrue(imageSourcesHistoria.add(src), "Imagen duplicada encontrada en 'Nuestra Historia': " + src);
        }
        assertTrue(imageSourcesHistoria.size() >= 3, "No se encontraron al menos 3 imágenes únicas en 'Nuestra Historia'");
    }

    @Test
    public void testCatasSection() {
        // Encuentra y haz clic en el botón de concepto en el header para dirigirse a la página
        WebElement conceptButton = driver.findElement(By.cssSelector("a[href='/concept']"));
        conceptButton.click();

        // Espera explícita de 2 segundos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica que estamos en la página de concepto
        assertEquals(conceptUrl, driver.getCurrentUrl());

        // Validar la Sección "Las Catas"
        WebElement catasSection = driver.findElement(By.xpath("/html/body/div[1]/section[2]"));
        assertTrue(catasSection.isDisplayed(), "La sección 'Las Catas' no está visible");

        // Verificar el título "Las Catas"
        WebElement catasTitle = catasSection.findElement(By.tagName("h2"));
        assertTrue(catasTitle.isDisplayed(), "El título de la sección 'Las Catas' no está visible");
        assertEquals("Las Catas", catasTitle.getText(), "El título de la sección no es 'Las Catas'");

        // Verificar que la sección contiene al menos 4 imágenes únicas
        List<WebElement> catasImages = catasSection.findElements(By.tagName("img"));
        Set<String> imageSourcesCatas = new HashSet<>();
        for (WebElement img : catasImages) {
            String src = img.getAttribute("src");
            System.out.println("Imagen encontrada en 'Las Catas': " + src);
            assertTrue(imageSourcesCatas.add(src), "Imagen duplicada encontrada en 'Las Catas': " + src);
        }
        assertTrue(imageSourcesCatas.size() >= 4, "No se encontraron al menos 4 imágenes únicas en 'Las Catas'");
    }

    @Test
    public void testReservaSection() {
        // Encuentra y haz clic en el botón de concepto en el header para dirigirse a la página
        WebElement conceptButton = driver.findElement(By.cssSelector("a[href='/concept']"));
        conceptButton.click();

        // Espera explícita de 2 segundos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica que estamos en la página de concepto
        assertEquals(conceptUrl, driver.getCurrentUrl());

        // Validar la Sección de Reserva de Catas
        WebElement reservaSection = driver.findElement(By.xpath("/html/body/div[1]/section[3]"));
        assertTrue(reservaSection.isDisplayed(), "La sección de reserva de catas no está visible");

        WebElement imagenLateral = reservaSection.findElement(By.xpath("/html/body/div[1]/section[3]/div/img"));
        assertTrue(imagenLateral.isDisplayed(), "La imagen lateral en la sección de reserva no está visible");

        WebElement textoLateral = reservaSection.findElement(By.xpath("/html/body/div[1]/section[3]/div/div/p[1]"));
        assertTrue(!textoLateral.getText().isEmpty(), "El texto lateral en la sección de reserva está vacío");

        WebElement botonReserva = reservaSection.findElement(By.xpath("/html/body/div[1]/section[3]/div/div/button"));
        assertTrue(botonReserva.isDisplayed(), "El botón de reserva en la sección de reserva no está visible");
    }

    @Test
    public void testResponsiveDesign() throws InterruptedException {
        // Encuentra y haz clic en el botón de concepto en el header para dirigirse a la página
        WebElement conceptButton = driver.findElement(By.cssSelector("a[href='/concept']"));
        conceptButton.click();

        // Espera explícita de 2 segundos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica que estamos en la página de concepto
        assertEquals(conceptUrl, driver.getCurrentUrl());

        // Tamaños de pantalla que queremos probar: tablet y celular
        Dimension[] screenSizes = {
            new Dimension(768, 1024),  // Tamaño de tablet
            new Dimension(360, 640)    // Tamaño de celular
        };

        // Iterar sobre cada tamaño de pantalla definido
        for (Dimension screenSize : screenSizes) {
            // Configurar el tamaño de la ventana del navegador
            driver.manage().window().setSize(screenSize);

            // Espera explícita de 2 segundos
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Validar la visibilidad de la sección "Nuestra Historia"
        WebElement historiaSection = driver.findElement(By.xpath("//div[contains(@class,'w-full px-5 sm:px-2')]/section"));
        assertTrue(historiaSection.isDisplayed());

            // Validar la visibilidad de la sección "Las Catas"
        WebElement catasSection = driver.findElement(By.xpath("/html/body/div[1]/section[2]"));
        assertTrue(catasSection.isDisplayed());

        // Verificar que las imágenes en "Las Catas" estén en modo carrusel
        WebElement carouselElement = catasSection.findElement(By.xpath("/html/body/div[1]/section[2]/div[1]"));
        assertTrue(carouselElement.isDisplayed());
    }
}
}