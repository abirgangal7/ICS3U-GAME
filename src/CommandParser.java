import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

public class CommandParser {
    public void parse(String input, Player player, Map<String, Room> rooms) {
        String[] words = input.trim().toLowerCase().split("\\s+");
        if (words.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }

        String command = words[0];

        switch (command) {
            case "go":
                if (words.length < 2) {
                    System.out.println("Go where?");
                } else if (words[1].toLowerCase().equalsIgnoreCase("back")) {
                    Room currentRoom = rooms.get(player.getCurrentRoomId());
                    if (player.getPrevRoomId() != player.getCurrentRoomId()) {
                        player.setCurrentRoomId(player.getPrevRoomId());
                        currentRoom = rooms.get(player.getCurrentRoomId());
                        System.out.println(currentRoom.getLongDescription());
                    } else {
                        System.out.println("You haven't even moved yet...");
                    }
                } else {
                    String direction = words[1];
                    Room currentRoom = rooms.get(player.getCurrentRoomId());

                    String nextRoomId = currentRoom.getExits().get(direction);

                    if (nextRoomId != null) {
                        player.setPrevRoomId(currentRoom.getId());;
                        player.setCurrentRoomId(nextRoomId);
                        System.out.println("You move " + direction + ".\n");
                        currentRoom = rooms.get(player.getCurrentRoomId());
                        System.out.println(currentRoom.getLongDescription());
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
                break;
            case "look":
                Room currentRoom = rooms.get(player.getCurrentRoomId());
                System.out.println(currentRoom.getLongDescription());
                break;
            case "inventory":
                if (player.getInventory().isEmpty()) {
                    System.out.println("Your inventory is empty.");
                } else {
                    System.out.println("You are carrying:");
                    for (Item item : player.getInventory()) {
                        System.out.println("- " + item.getName() + "    " + item.getWeight() + " lb(s))");
                    }
                    System.out.println(player.getCarry() + "/" + player.getCarry_cap() + " lbs");
                }
                break;
            case "take":
                if (words.length < 2) {
                    System.out.println("Take what?");
                } else {
                    String itemName = Arrays.stream(words).skip(1).collect(Collectors.joining());
                    Room room = rooms.get(player.getCurrentRoomId());
                    Item itemToTake = null;
                    for (Item item : room.getItems()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToTake = item;
                            break;
                        }
                    }
                    
                    if (itemToTake != null) {
                        if (player.getCarry() + itemToTake.getWeight() <= player.getCarry_cap()) {
                            room.removeItem(itemToTake);
                            player.addItem(itemToTake);
                            System.out.println("You take the " + itemToTake.getName() + ".");
                        } else {
                            System.out.println("You are carrying too much, drop something first.");
                        }
                    } else {
                        System.out.println("There is no " + itemName + " here.");
                    }
                }
                break;
            case "drop":
                if (words.length < 2) {
                    System.out.println("Drop what?");
                } else {
                    String itemName = Arrays.stream(words).skip(1).collect(Collectors.joining());
                    Item itemToDrop = null;
                    for (Item item : player.getInventory()) {
                        if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                            itemToDrop = item;
                            break;
                        }
                    }
                    if (itemToDrop != null) {
                        player.removeItem(itemToDrop);
                        Room room = rooms.get(player.getCurrentRoomId());
                        room.addItem(itemToDrop);
                        System.out.println("You drop the " + itemToDrop.getName() + ".");
                    } else {
                        System.out.println("You don't have a " + itemName + ".");
                    }
                }
                break;
            case "use":
                String itemName = Arrays.stream(words).skip(1).collect(Collectors.joining());
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
                            System.out.println("Your " + itemName + "makes the area brighter.");
                            break;
                        case "farm":
                            System.out.println("to be implemented");
                            break;
                        case "unlock":
                            String itemID = itemToUse.getId();
                            String roomToUnlock = itemID.substring(0, itemID.indexOf("k"));
                            
                            System.out.println("To be implemented");
                            break;
                        default:
                            System.out.println("This item has no use.");
                            break;
                    }
                } else {
                    System.out.println("You don't have a " + itemName + ".");
                }
                break;
            case "help":
                System.out.println("Available commands: go [direction], look, take [item], drop [item], inventory, help");
                break;
            default:
                System.out.println("I don't understand that command.");
                break;
        }
    }
}
