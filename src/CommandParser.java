import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandParser {
    public void parse(String input, Player player, Map<String, Room> rooms, Scanner scanner) {
        String[] words = input.trim().toLowerCase().split("\\s+");
        if (words.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        Room currentRoom = rooms.get(player.getCurrentRoomId());
        String command = words[0].toLowerCase();

        switch (command) {
            case "go", "g":
                if (words.length < 2) {
                    System.out.print("Go where?\n\n> ");
                    String[] in = scanner.nextLine().toLowerCase().split("\\s+");
                    String nextRoomId = currentRoom.getExits().get(in[0]);
                    String direction = in[0];

                    if (in[0].equalsIgnoreCase("go")) {
                        direction = in[1];
                        nextRoomId = currentRoom.getExits().get(direction);
                    }

                    if (direction.equalsIgnoreCase("back")) {
                        if (player.getPrevRoomId() != player.getCurrentRoomId()) {
                            String temp = player.getCurrentRoomId();
                            player.setCurrentRoomId(player.getPrevRoomId());
                            player.setPrevRoomId(temp);
                            currentRoom = rooms.get(player.getCurrentRoomId());
                            System.out.println(currentRoom.getLongDescription(player));
                        } else {
                            System.out.println("You haven't even moved yet...");
                        }
                    } else if (nextRoomId != null) {
                        if (currentRoom.isExitLocked(direction)) {
                            System.out.println("That way is locked. You'll need a key");
                        } else {
                            player.setPrevRoomId(currentRoom.getId());;
                            player.setCurrentRoomId(nextRoomId);
                            System.out.println("You move " + direction + ".");
                            currentRoom = rooms.get(player.getCurrentRoomId());
                            System.out.println(currentRoom.getLongDescription(player));
                        }
                        
                    } else {
                        System.out.println("You can't go that way.");
                    }
                } else if (words[1].toLowerCase().equalsIgnoreCase("back")) {
                    if (player.getPrevRoomId() != player.getCurrentRoomId()) {
                        String temp = player.getCurrentRoomId();
                        player.setCurrentRoomId(player.getPrevRoomId());
                        player.setPrevRoomId(temp);
                        currentRoom = rooms.get(player.getCurrentRoomId());
                        System.out.println(currentRoom.getLongDescription(player));
                    } else {
                        System.out.println("You haven't even moved yet...");
                    }
                } else {
                    String direction = words[1];
                    String nextRoomId = currentRoom.getExits().get(direction);

                    if (nextRoomId != null) {
                        if (currentRoom.isExitLocked(direction)) {
                            System.out.println("That way is locked. You'll need a key");
                        } else {
                            
                            player.setPrevRoomId(currentRoom.getId());;
                            player.setCurrentRoomId(nextRoomId);
                            System.out.println("You move " + direction + ".");
                            currentRoom = rooms.get(player.getCurrentRoomId());
                            System.out.println(currentRoom.getLongDescription(player));
                        }
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
                break;

            case "look", "l":
                currentRoom = rooms.get(player.getCurrentRoomId());
                System.out.println(currentRoom.getLongDescription(player));
                break;
            
            case "inventory", "i":
                if (player.getInventory().isEmpty()) {
                    System.out.println("Your inventory is empty.");
                } else {
                    System.out.println("Hp: " + player.getHp());
                    System.out.println("Score: " + player.getScore());
                    System.out.println("Money: $" + player.getMoney());
                    System.out.println("You are carrying:");
                    for (Item item : player.getInventory()) {
                        if (item instanceof Weapon w) {
                            System.out.println("- " + w.getName() + " (" + w.getDmg() + " dmg)    " + w.getWeight() + " lb(s)");
                        } else {
                            System.out.println("- " + item.getName() + "    " + item.getWeight() + " lb(s)");
                        }
                    }
                    System.out.println(player.getCarry() + "/" + player.getCarry_cap() + " lbs");
                }
                break;
            
            case "take", "t":
                if (words.length < 2) {
                    System.out.print("Take what?\n\n> ");
                    String[] in = scanner.nextLine().toLowerCase().split("\\s+");
                    Item itemToTake = null;
                    String itemName = in[0];

                    if (in[0].equalsIgnoreCase("take")) {
                        itemName = in[1];
                    }

                    for (Item item : currentRoom.getItems()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToTake = item;
                            break;
                        }
                    }

                    if (itemToTake != null) {
                        if (player.getCarry() + itemToTake.getWeight() <= player.getCarry_cap()) {
                            if (itemToTake.getName().toLowerCase().contains("feather")) {
                                currentRoom.removeItem(itemToTake);
                                player.addItem(itemToTake);
                                System.out.println("You struggle greatly to lift this feather, it seems you can't hold anything else...");
                            } else {
                                currentRoom.removeItem(itemToTake);
                                player.addItem(itemToTake);
                                System.out.println("You take the " + itemToTake.getName() + ".");
                            }
                        } else {
                            if (itemToTake.getName().toLowerCase().contains("feather")) {
                                System.out.println("It's a really heavy feather, perhaps you should drop a few things first.");
                            } else {
                                System.out.println("You are carrying too much, drop something first.");
                            }
                        }
                    } else {
                        System.out.println("There is no " + itemName + " here.");
                    }

                } else {
                    String itemName = words[1];
                    Item itemToTake = null;
                    for (Item item : currentRoom.getItems()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToTake = item;
                            break;
                        }
                    }
                    
                    if (itemToTake != null) {
                        if (player.getCarry() + itemToTake.getWeight() <= player.getCarry_cap()) {
                            if (itemToTake.getName().toLowerCase().contains("feather")) {
                                currentRoom.removeItem(itemToTake);
                                player.addItem(itemToTake);
                                System.out.println("You struggle greatly to lift this feather, it seems you can't hold anything else...");
                            } else {
                                currentRoom.removeItem(itemToTake);
                                player.addItem(itemToTake);
                                System.out.println("You take the " + itemToTake.getName() + ".");
                                if (itemToTake.getId().toLowerCase().contains("dmg")) {
                                    player.take_dmg(5, true);
                                    System.out.println("Seems like this " + itemName + " wasn't safe to pick up...");
                                }
                            }
                        } else {
                            if (itemToTake.getName().toLowerCase().contains("feather")) {
                                System.out.println("It's a really heavy feather, perhaps you should drop a few things first.");
                            } else {
                                System.out.println("You are carrying too much, drop something first.");
                            }
                        }
                    } else {
                        System.out.println("There is no " + itemName + " here.");
                    }
                }
                break;
            
            case "drop", "d":
                if (words.length < 2) {
                    System.out.print("Drop what?\n\n> ");
                    String[] in = scanner.nextLine().trim().toLowerCase().split("\\s+");
                    String itemName;
                    if (in[0].equalsIgnoreCase("drop")) {
                        itemName = in[1];
                    } else {
                        itemName = in[0];
                    }
                        Item itemToDrop = null;
                        for (Item item : player.getInventory()) {
                            if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                                itemToDrop = item;
                                break;
                            }
                        }
                        if (itemToDrop != null) {
                            player.removeItem(itemToDrop);
                            currentRoom.addItem(itemToDrop);
                            System.out.println("You drop the " + itemToDrop.getName() + ".");
                            if (itemToDrop.getUse().equalsIgnoreCase("light") && player.getTags().contains("light")) {
                                player.removeTag("light");
                                System.out.println("The area around you gets darker...");
                            }
                        } else {
                            System.out.println("You don't have a " + itemName + ".");
                        } 
                } else {
                    String itemName = words[1];
                    Item itemToDrop = null;
                    for (Item item : player.getInventory()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToDrop = item;
                            break;
                        }
                    }
                    if (itemToDrop != null) {
                        player.removeItem(itemToDrop);
                        currentRoom.addItem(itemToDrop);
                        System.out.println("You drop the " + itemToDrop.getName() + ".");
                        if (itemToDrop.getUse().equalsIgnoreCase("light") && player.getTags().contains("light")) {
                            player.removeTag("light");
                            System.out.println("The area around you gets darker...");
                        }
                    } else {
                        System.out.println("You don't have a " + itemName + ".");
                    }
                }
                break;

            case "use", "u":
                if (words.length < 2) {
                    System.out.print("Use what?\n\n> ");
                    String[] in = scanner.nextLine().trim().toLowerCase().split("\\s+");
                    String itemName;
                    if (in[0].equalsIgnoreCase("use")) {
                        itemName = in[1];
                    } else {
                        itemName = in[0];
                    }
                    Item itemToUse = null;
                    for (Item item : player.getInventory()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToUse = item;
                            break;
                        }
                    }

                    if (itemToUse != null) {
                        String use = itemToUse.getUse();
                        
                        switch (use) {
                            case "light":
                                player.addTag("light");
                                System.out.println("Your " + itemName + " makes the area brighter.");
                                break;
                            case "unlock":
                                String itemID = itemToUse.getId();
                                
                                String directionToUnlock = null;
                                for (String dir : currentRoom.getExits().keySet()) {
                                    if (currentRoom.isExitLocked(dir) && itemID.equalsIgnoreCase(currentRoom.getRequiredKeyId(dir))) {
                                        directionToUnlock = dir;
                                        break;
                                    }
                                }
                                
                                if (directionToUnlock != null) {
                                    currentRoom.unlockExit(directionToUnlock);
                                    System.out.println("You twist the key and hear a click. The way " + directionToUnlock + " is now open.");
                                } else {
                                    System.out.println("The key doesn't seem to fit.");
                                }
                                break;

                            case "weapon":
                                System.out.println("You look at the " + itemName + " admiring its sharp edge and the way the light reflects off of it.\n");
                                break;

                            default:
                                System.out.println("This item has no use.");
                                break;
                        }
                    } else {
                        System.out.println("You don't have a " + itemName + ".");
                    }
                } else {
                    String itemName = words[1];
                    Item itemToUse = null;
                    for (Item item : player.getInventory()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToUse = item;
                            break;
                        }
                    }

                    if (itemToUse != null) {
                        String use = itemToUse.getUse();
                        
                        switch (use) {
                            case "light":
                                player.addTag("light");
                                System.out.println("Your " + itemName + " makes the area brighter.");
                                System.out.println(currentRoom.getLongDescription(player));
                                break;
                            case "unlock":
                                String itemID = itemToUse.getId();
                                
                                if (itemID.equalsIgnoreCase("wire") || itemID.equalsIgnoreCase("wiredmg")) {
                                Item secondWire = null;
                                for (Item inv : player.getInventory()) {
                                    if ((inv.getId().equalsIgnoreCase("wire") || inv.getId().equalsIgnoreCase("wiredmg"))
                                            && !inv.getId().equalsIgnoreCase(itemID)) {
                                        secondWire = inv;
                                        break;
                                    }
                                }

                                if (secondWire != null) {
                                    String directionToUnlock = null;
                                    for (String dir : currentRoom.getExits().keySet()) {
                                        if (currentRoom.isExitLocked(dir) && currentRoom.getRequiredKeyId(dir).equalsIgnoreCase("finkey")) {
                                            directionToUnlock = dir;
                                            break;
                                        }
                                    }

                                    if (directionToUnlock != null) {
                                        currentRoom.unlockExit(directionToUnlock);
                                        player.removeItem(itemToUse);
                                        player.removeItem(secondWire);
                                        System.out.println("You splice the two wires together. The golden lock sparks and clicks open.");
                                    } else {
                                        System.out.println("You fiddle with the wires, but there's nothing to connect them to here.");
                                    }
                                } else {
                                    System.out.println("You only have one wire. You'll need another to do anything useful.");
                                }
                                break;
                            }

                                String directionToUnlock = null;
                                for (String dir : currentRoom.getExits().keySet()) {
                                    if (currentRoom.isExitLocked(dir) && itemID.equalsIgnoreCase(currentRoom.getRequiredKeyId(dir))) {
                                        directionToUnlock = dir;
                                        break;
                                    }
                                }
                                
                                if (directionToUnlock != null) {
                                    currentRoom.unlockExit(directionToUnlock);
                                    System.out.println("You twist the key and hear a click. The way " + directionToUnlock + " is now open.");
                                } else {
                                    System.out.println("The key doesn't seem to fit.");
                                }
                                break;

                            case "weapon":
                                System.out.println("You look at the " + itemName + " admiring its sharp edge and the way the light reflects off of it.\n");
                                break;
                                
                            default:
                                System.out.println("This item has no use.");
                                break;
                        }
                    } else {
                        System.out.println("You don't have a " + itemName + ".");
                    }
                }
                break;

            case "kill", "attack", "k", "a":
                if (words.length < 2) {
                    List<Monster> monsters = currentRoom.getMonsters();
                    if (!monsters.isEmpty()) {
                        System.out.print("Kill what?\n\n> ");

                        String[] in = scanner.nextLine().trim().toLowerCase().split("\\s+");
                        String cmd;
                        if (in[0].equalsIgnoreCase("kill") || in[0].equalsIgnoreCase("attack")) {
                            cmd = in[1];
                        } else {
                            cmd = in[0];
                        }

                        if (cmd.equalsIgnoreCase("myself")) {
                            String itemName = null;    

                            for (Item item : player.getInventory()) {
                                if (item instanceof Weapon w) {
                                    itemName = w.getName();
                                    break;
                                }
                            }

                            if (itemName != null) {
                                System.out.println("Questionable decision, but okay...\n\nYou kill yourself with "+ itemName);
                                player.take_dmg(player.getHp(), false);
                                player.die();
                            } else {
                                System.out.println("You don't have anything to kill yourself with");
                            }
                        } else {
                            
                            Monster monsterToAtk = null;
                            int dmg = player.getDmg();
                            
                            for (Monster monster : monsters) {
                                if (monster.getName().toLowerCase().contains(cmd)) {
                                    monsterToAtk = monster;
                                    break;
                                }
                            }

                            if (monsterToAtk != null) {
                                List<Weapon> weapons = new ArrayList<>();

                                for (Item item : player.getInventory()) {
                                    if (item instanceof Weapon w) {
                                        weapons.add(w);
                                    }
                                }

                                Weapon weaponToUse = null;

                                if (weapons.size() != 0) {

                                    System.out.println("Available weapons:");
                                    for (Weapon w : weapons) {
                                        System.out.println("- " + w.getName() + " (" + w.getDmg() + " dmg)");
                                    }
                                    boolean done = false;
                                    while (!done) {
                                        System.out.print("What weapon do you want to use?\n\n> ");
                                        String weaponName = scanner.nextLine();

                                        for (Weapon weapon : weapons) {
                                            if (weapon.getName().toLowerCase().contains(weaponName)) {
                                                weaponToUse = weapon;
                                                done = true;
                                            } else {
                                                System.out.println("Pick a valid option");
                                            }
                                        }
                                    }
                                }

                                if (weaponToUse != null) {
                                    dmg = weaponToUse.getDmg();
                                }

                                try {
                                    String monsterName = monsterToAtk.getName();
                                    System.out.println("You attack the " + monsterName);
                                    monsterToAtk.take_dmg(dmg);
                                    monsterToAtk.die(currentRoom, player);

                                    boolean fighting = true;
                                    while (fighting && currentRoom.getMonsters().contains(monsterToAtk)) {
                                        System.out.println("\nThe " + monsterName + " attacks...");
                                        Thread.sleep(2000);
                                        if (player.take_dmg(monsterToAtk.getDMG(), false).equals("hit")) {
                                            System.out.println("The " + monsterName + " strikes you!\n");
                                            player.die();
                                            if (!player.isAlive()) {
                                                break;
                                            }
                                        } else {
                                            System.out.println("The " + monsterName + " tries to strike you, but you evade!\n");
                                        }
                                        
                                        Thread.sleep(2000);
                                        System.out.print("Flee or attack?\n\n> ");
                                        String choice = scanner.nextLine();

                                        if (choice.equalsIgnoreCase("flee")) {
                                            System.out.println("You run...");
                                            Thread.sleep(2000);
                                            String temp = player.getCurrentRoomId();
                                            player.setCurrentRoomId(player.getPrevRoomId());
                                            player.setPrevRoomId(temp);
                                            currentRoom = rooms.get(player.getCurrentRoomId());
                                            System.out.println(currentRoom.getLongDescription(player));
                                            break;
                                        } else if (choice.equalsIgnoreCase("attack")) {
                                            System.out.println("You attack again!");
                                            Thread.sleep(2000);
                                            if (weaponToUse != null) {
                                                System.out.println("You swing your " + weaponToUse.getName() + " and strike the " + monsterName + "!");
                                            } else {
                                                System.out.println("You punch the " + monsterName + "!");
                                            }
                                            monsterToAtk.take_dmg(dmg);
                                            monsterToAtk.die(currentRoom, player);
                                        } else {
                                            System.out.println("Guess I'll make you attack again...");
                                            System.out.println("You attack hesitantly...");
                                            monsterToAtk.take_dmg(dmg);
                                            monsterToAtk.die(currentRoom, player);
                                        }
                                    }
                                } catch (Exception e) {
                                    Thread.currentThread().interrupt();
                                }
                            } else {
                                System.out.println("There is no " + cmd + " here");
                            }
                        }    
                    } else {
                        System.out.println("There are no monsters here");
                    }
                } else {
                    String cmd = words[1];

                    if (cmd.equalsIgnoreCase("myself")) {
                        String itemName = null;    

                        for (Item item : player.getInventory()) {
                            if (item instanceof Weapon w) {
                                itemName = w.getName();
                                break;
                            }
                        }

                        if (itemName != null) {
                            System.out.println("Questionable decision, but okay...\n\nYou kill yourself with "+ itemName);
                            player.take_dmg(player.getHp(), false);
                            player.die();
                        } else {
                            System.out.println("You don't have anything to kill yourself with");
                        }
                    } else {
                        List<Monster> monsters = currentRoom.getMonsters();
                        Monster monsterToAtk = null;
                        int dmg = player.getDmg();
                        
                        for (Monster monster : monsters) {
                            if (monster.getName().toLowerCase().contains(cmd)) {
                                monsterToAtk = monster;
                                break;
                            }
                        }

                        if (monsterToAtk != null) {
                            List<Weapon> weapons = new ArrayList<>();

                            for (Item item : player.getInventory()) {
                                if (item instanceof Weapon w) {
                                    weapons.add(w);
                                }
                            }

                            Weapon weaponToUse = null;

                            if (weapons.size() != 0) {

                                System.out.println("Available weapons:");
                                for (Weapon w : weapons) {
                                    System.out.println("- " + w.getName() + " (" + w.getDmg() + " dmg)");
                                }
                                boolean done = false;
                                while (!done) {
                                    System.out.print("What weapon do you want to use?\n\n> ");
                                    String weaponName = scanner.nextLine();

                                    for (Weapon weapon : weapons) {
                                        if (weapon.getName().toLowerCase().contains(weaponName)) {
                                            weaponToUse = weapon;
                                            done = true;
                                        } else {
                                            System.out.println("Pick a valid option");
                                        }
                                    }
                                }
                            }

                            if (weaponToUse != null) {
                                dmg = weaponToUse.getDmg();
                            }

                            try {
                                String monsterName = monsterToAtk.getName();
                                System.out.println("You attack the " + monsterName);
                                monsterToAtk.take_dmg(dmg);
                                monsterToAtk.die(currentRoom, player);

                                boolean fighting = true;
                                while (fighting && currentRoom.getMonsters().contains(monsterToAtk)) {
                                    System.out.println("\nThe " + monsterName + " attacks...");
                                    Thread.sleep(2000);
                                    if (player.take_dmg(monsterToAtk.getDMG(), false).equals("hit")) {
                                        System.out.println("The " + monsterName + " strikes you!\n");
                                        player.die();
                                        if (!player.isAlive()) {
                                            break;
                                        }
                                    } else {
                                        System.out.println("The " + monsterName + " tries to strike you, but you evade!\n");
                                    }
                                    
                                    Thread.sleep(2000);
                                    System.out.print("Flee or attack?\n\n> ");
                                    String choice = scanner.nextLine();

                                    if (choice.equalsIgnoreCase("flee")) {
                                        System.out.println("You run...");
                                        Thread.sleep(2000);
                                        String temp = player.getCurrentRoomId();
                                        player.setCurrentRoomId(player.getPrevRoomId());
                                        player.setPrevRoomId(temp);
                                        currentRoom = rooms.get(player.getCurrentRoomId());
                                        System.out.println(currentRoom.getLongDescription(player));
                                        break;
                                    } else if (choice.equalsIgnoreCase("attack")) {
                                        System.out.println("You attack again!");
                                        Thread.sleep(2000);
                                        if (weaponToUse != null) {
                                            System.out.println("You swing your " + weaponToUse.getName() + " and strike the " + monsterName + "!");
                                        } else {
                                            System.out.println("You punch the " + monsterName + "!");
                                        }
                                        monsterToAtk.take_dmg(dmg);
                                        monsterToAtk.die(currentRoom, player);
                                    } else {
                                        System.out.println("Guess I'll make you attack again...");
                                        System.out.println("You attack hesitantly...");
                                        monsterToAtk.take_dmg(dmg);
                                        monsterToAtk.die(currentRoom, player);
                                    }
                                }
                            } catch (Exception e) {
                                Thread.currentThread().interrupt();
                            }
                        } else {
                            System.out.println("There is no " + cmd + " here");
                        }
                    }
                }
                break;
            
            case "help":
                System.out.println("Available commands: look, go [direction], take [item], drop [item], use [item], inventory, help");
                break;
            
                default:
                System.out.println("I don't understand that command.");
                break;
        }
    }
}