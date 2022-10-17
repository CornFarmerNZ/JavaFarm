
import Project2.controller.FarmController;
import Project2.entity.Animal;
import Project2.entity.Horse;
import Project2.entity.Kiwi;
import Project2.entity.Pig;
import Project2.entity.Sheep;
import Project2.service.AnimalFactory;
import Project2.service.ImageFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tim
 */
public class FarmControllerTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void farmNameGeneratedProperly() {
        FarmController instance = new FarmController();
        String testName = "Ram Ranch";
        instance.newFarm(testName);
        assertEquals("Not equal", instance.getName(), testName);
    }

    @Test
    public void farmGeneratedNotNull() {
        FarmController instance = new FarmController();
        instance.newFarm("Test Farm");
        assertNotNull(instance);
    }

    @Test
    public void animalFactoryGetAnimalsAreEqualToNormalInstantiation() {
        AnimalFactory instance = new AnimalFactory();
        Animal[] animalsFromFactory = new Animal[]{instance.get("HORSE"), instance.get("KIWI"), instance.get("SHEEP"), instance.get("PIG")};
        Animal[] animals = new Animal[]{new Horse(), new Kiwi(), new Sheep(), new Pig()};

        for (int i = 0; i < animals.length; ++i) {
            assertTrue(animalsFromFactory[i].getClass() == animals[i].getClass());
        }
    }

    @Test
    public void animalFactoryReturnsNullIfAnimalDoesntExist() {
        AnimalFactory instance = new AnimalFactory();
        Animal animalToGet = instance.get("FlyingKoala");
        assertNull(animalToGet);
    }

    @Test
    public void idsGeneratedAreAllUnique() {
        AnimalFactory instance = new AnimalFactory();
        String[] animalsToGenerate = new String[]{"KIWI", "HORSE", "SHEEP", "PIG"};
        //hash map to store. Duplicate Keys won't be stored, so we can tell if there are any duplicates.
        Map<Integer, Animal> mapOfAnimals = new HashMap<>();
        for (int i = 0; i < animalsToGenerate.length; ++i) {
            //generate each animal 100 times. 400 in total (4*100).
            for (int j = 0; j < 100; ++j) {
                Animal tempAnimal = instance.get(animalsToGenerate[i]);
                //add to map
                mapOfAnimals.put(tempAnimal.getId(), tempAnimal);
            }
        }
        assertEquals(mapOfAnimals.size(), 400);
    }

    @Test
    public void imageFactoryReturnsNullIfImageDoesntExist() {
        ImageFactory instance = new ImageFactory();
        BufferedImage image = instance.getImage("HalfLife3");
        assertNull(image);
    }

    @Test
    public void imageFactoryReturnsValidImageIfValidImageRequested() throws IOException {
        ImageFactory instance = new ImageFactory();
        BufferedImage testImage = ImageIO.read(new File("./resources/horse.png"));
        String validImageRequest = "Horse";
        BufferedImage imageFromFactory = instance.getImage(validImageRequest);

        if (testImage.getWidth() == imageFromFactory.getWidth() && testImage.getHeight() == imageFromFactory.getHeight()) {
            for (int x = 0; x < testImage.getWidth(); x++) {
                for (int y = 0; y < testImage.getHeight(); y++) {
                    //compares whether each individual pixel is the same.
                    if (testImage.getRGB(x, y) != imageFromFactory.getRGB(x, y)) {
                        assertEquals("Not equal", "equal");
                    } else {
                        assertEquals("Equal", "Equal");
                    }
                }
            }
        } else {
            assertEquals("Not equal", "equal");
        }
    }

}
