import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HoodPopperTests {
    static WebDriver driver = new HtmlUnitDriver();

    @Before
    public void setUp() {
        driver.get("http://lit-bayou-7912.herokuapp.com/");
    }

    //Check to see if the user on the home page by checking that the "Code" label exists on the page.
    //If not, the test fails.
    @Test
    public void checkOnHomePage() {
        String homeTitle = driver.findElement(By.cssSelector("label")).getText();
        assertEquals(homeTitle, "Code");
    }

    //Check to make sure that the code area exists to write code on the home page.
    //If not, the test fails.
    @Test
    public void checkForHomeTextArea() {
        WebElement codeArea = driver.findElement(By.id("code_code"));
        assertNotNull(codeArea);
    }

    //Check to make sure that the tokenize button exists on the home page.
    //If not, the test fails.
    @Test
    public void checkForTokenizeButton() {
        WebElement tokenizeButton = driver.findElement(By.name("commit"));
        assertNotNull(tokenizeButton);
    }

    //Check to make sure that the parse button exists on the home page.
    //If not, the test fails.
    @Test
    public void checkForParseButton() {
        WebElement parseButton = driver.findElement(By.xpath("(//input[@name='commit'])[2]"));
        assertNotNull(parseButton);
    }

    //Check to make sure that the compile button exists on the home page.
    //If not, the test fails.
    @Test
    public void checkForCompileButton() {
        WebElement compileButton = driver.findElement(By.xpath("(//input[@name='commit'])[3]"));
        assertNotNull(compileButton);
    }

    //Check to see if entering a simple math equation gives a valid tokenization screen.
    //If not, the test fails.
    @Test
    public void testValidMathInputAndTokenize() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("a = 1\nb = 1\nc = a + b\nputs c");
        driver.findElement(By.name("commit")).click();
        assertEquals("Hood Popped - Tokenize Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in tokenize screen
        try {
            String tokenizeCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(tokenizeCode.contains(":on_ident"));    
            assertTrue(tokenizeCode.contains("puts")); 
            driver.navigate().back();        
        } catch(Exception e) {
            fail();
        }        
    } 

    //Check to see if entering a simple math equation gives a valid parsed screen.
    //If not, the test fails.    
    @Test
    public void testValidMathInputAndParse() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("a = 1\nb = 1\nc = a + b\nputs c");
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
        assertEquals("Hood Popped - Parse Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in parse screen
        try {
            String parseCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(parseCode.contains("puts"));
            assertTrue(parseCode.contains(":+"));
            assertFalse(parseCode.contains("on_sp") && parseCode.contains("on_nl"));    
            driver.navigate().back();        
        } catch(Exception e) {
            fail();
        }
    } 

    //Check to see if entering a simple math equation gives a valid compiled screen.
    //If not, the test fails.
    @Test
    public void testValidMathInputAndCompile() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("a = 1\nb = 1\nc = a + b\nputs c");
        driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
        assertEquals("Hood Popped - Compile Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in compile screen
        try {
            String compileCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(compileCode.contains("opt_plus")); 
            assertTrue(compileCode.contains("putself")); 
            driver.navigate().back();        
        } catch(Exception e) {
            fail();
        }        
    }

    //Check to see if entering a simple string concatenation gives a valid tokenization screen.
    //If not, the test fails.
    @Test
    public void testValidStringInputAndTokenize() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("cat = 'cat'\nputsI am printing a + cat");
        driver.findElement(By.name("commit")).click();
        assertEquals("Hood Popped - Tokenize Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in tokenize screen
        try {
            String tokenizeCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(tokenizeCode.contains("on_tstring_content"));
            assertTrue(tokenizeCode.contains("on_op"));
            driver.navigate().back();
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering a simple string concatenation gives a valid parsed screen.
    //If not, the test fails.
    @Test
    public void testValidStringInputAndParse() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("cat = 'cat'\nputsI am printing a + cat");
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
        assertEquals("Hood Popped - Parse Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in parse screen
        try {
            String parseCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(parseCode.contains("string_literal"));
            assertTrue(parseCode.contains("+"));
            driver.navigate().back();
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering a simple string concatenation gives a valid compiled screen.
    //If not, the test fails.
    @Test
    public void testValidStringInputAndCompile() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("cat = 'cat'\nputsI am printing a + cat");
        driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
        assertEquals("Hood Popped - Compile Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in compile screen
        try {
            String compileCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(compileCode.contains("putstring"));
            assertTrue(compileCode.contains("opt_plus"));
            driver.navigate().back();
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering strange keys gives a valid tokenization screen.
    //If not, the test fails.
    @Test
    public void testInvalidInputAndTokenize() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("-------");  
        driver.findElement(By.name("commit")).click();
        assertEquals("Hood Popped - Tokenize Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for improper code in tokenize screen
        try {
            String tokenizeCode = driver.findElement(By.cssSelector("code")).getText();
            assertFalse(tokenizeCode.contains("error"));
            driver.navigate().back();  
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering strange keys gives an invalid parsed screen.
    //If not, the test fails.
    @Test
    public void testInvalidInputAndParse() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("-------");
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
        assertEquals("We're sorry, but something went wrong.", driver.findElement(By.cssSelector("h1")).getText());

        //Try and go back after 500 failure on parse screen
        try {
            driver.navigate().back();              
        } catch(Exception e) {
            fail();
        }
    }  

    //Check to see if entering strange keys gives an invalid compiled screen with an error.
    //If not, the test fails.
    @Test
    public void testInvalidInputAndCompile() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("-------");  
        driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
        assertEquals("Hood Popped - Compile Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for error message in compile screen
        try {
            String compileCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(compileCode.contains("Syntax error"));
            driver.navigate().back();  
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering strange keys and valid text gives a valid tokenization screen.
    //If not, the test fails.
    @Test
    public void testInvalidInputPlusTextAndTokenize() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("-------\nhello");  
        driver.findElement(By.name("commit")).click();
        assertEquals("Hood Popped - Tokenize Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in tokenize screen
        try {
            String tokenizeCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(tokenizeCode.contains("on_op"));
            assertTrue(tokenizeCode.contains("on_ident"));
            driver.navigate().back();  
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering strange keys and valid text gives a valid parsed screen.
    //If not, the test fails.
    @Test
    public void testInvalidInputPlusTextAndParse() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("-------\nhello");  
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
        assertNotEquals("We're sorry, but something went wrong.", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in parse screen
        try {
            String parseCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(parseCode.contains("unary"));
            driver.navigate().back();  
        } catch(Exception e) {
            fail();
        }
    }

    //Check to see if entering strange keys and valid text gives a valid compiled screen.
    //If not, the test fails.
    @Test
    public void testInvalidInputPlusTextAndCompile() {
        driver.findElement(By.id("code_code")).clear();
        driver.findElement(By.id("code_code")).sendKeys("-------\nhello");  
        driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
        assertEquals("Hood Popped - Compile Operation", driver.findElement(By.cssSelector("h1")).getText());

        //Try and look for proper code in compile screen
        try {
            String compileCode = driver.findElement(By.cssSelector("code")).getText();
            assertTrue(compileCode.contains("opt_send_simple"));
            driver.navigate().back();  
        } catch(Exception e) {
            fail();
        }
    }    

    //Check to see if entering nothing gives a valid tokenization screen.
    //If not, the test fails.
    @Test
    public void testNoInputAndTokenize() {
        driver.findElement(By.id("code_code")).clear();  
        driver.findElement(By.name("commit")).click();
        assertEquals("Hood Popped - Tokenize Operation", driver.findElement(By.cssSelector("h1")).getText());
        assertEquals(driver.findElement(By.cssSelector("code")).getText(), "");      
    }      

    //Check to see if entering nothing gives a valid parsed screen.
    //If not, the test fails.
    @Test
    public void testNoInputAndParse() {
        driver.findElement(By.id("code_code")).clear();  
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
        assertEquals("Hood Popped - Parse Operation", driver.findElement(By.cssSelector("h1")).getText());
        assertTrue(driver.findElement(By.cssSelector("code")).getText().contains("program"));      
    }   

    //Check to see if entering nothing gives a valid compiled screen.
    //If not, the test fails.
    @Test
    public void testNoInputAndCompile() {
        driver.findElement(By.id("code_code")).clear();  
        driver.findElement(By.xpath("(//input[@name='commit'])[3]")).click();
        assertEquals("Hood Popped - Compile Operation", driver.findElement(By.cssSelector("h1")).getText());
        assertTrue(driver.findElement(By.cssSelector("code")).getText().contains("== disasm"));      
    }    
}