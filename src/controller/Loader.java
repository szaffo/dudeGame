package controller;

import java.util.ArrayList;

import model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import physics.Position;

/**
 * @author Leczó Gábor Bálint
 */
public class Loader {

    private StringTokenizer tokenizer;
    private Model model;

    public Loader(Model model) {
        this.model = model;
    }

    private ArrayList<String> getFileNames() {
        ArrayList<String> fileNames = new ArrayList<>();
        for (File file : new File("data/").listFiles()) {
            if (file.isFile()) {
                fileNames.add(file.getName());
                System.out.println(file.getName());
            }
        }
        return fileNames;
    }

    public void load() {

        for (String fileName : getFileNames()) {
            try {
                model.addItemSet(fileName, loadFile(fileName));
            } catch (Exception e) {
                // System.out.println("Room not found. [" + room + "]");
                e.printStackTrace();
            }
        }

    }

    private ItemSet loadFile(String room) throws FileNotFoundException {
        File roomFile = new File("data/" + room);
        Scanner sc = new Scanner(roomFile);
        String line;
        ItemSet items = new ItemSet();

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            items = readLine(items, line);
        }

        sc.close();

        return items;
    }

    private ItemSet readLine(ItemSet theSet, String theLine) {
        tokenizer = new StringTokenizer(theLine, " ");

        while (tokenizer.hasMoreTokens()) {
            switch (tokenizer.nextToken()) {
            case "AGENT":
                theSet.setSpawnPoint(new Position(
                        Double.parseDouble(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken())));
                break;
            case "PLATFORM":
                theSet.add(new Platform(model, Double.parseDouble(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken())));
                break;
            case "FURNITURE":
                theSet.add(new Furniture(model, Double.parseDouble(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken()), Boolean.parseBoolean(tokenizer.nextToken()),
                        tokenizer.nextToken()));
                break;
            case "ROBOT":
                theSet.add(new Robot(model, Double.parseDouble(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken())));
                break;
            case "DOOR":
                theSet.add(new Door(model, Double.parseDouble(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken()), tokenizer.nextToken()));
                break;
            case "BALL":
                theSet.add(new Ball(model, Double.parseDouble(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken())));
                break;
            }
        }
        return theSet;
    }
}