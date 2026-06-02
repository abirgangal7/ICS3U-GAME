import java.util.Map;
import java.util.Scanner;

public class Game {
    private Map<String, Room> rooms;
    private Player player;
    private CommandParser commandParser;

    public Game() {
        RoomLoader roomLoader = new RoomLoader();
        rooms = roomLoader.loadRooms("rooms.json");
        player = new Player("startingRoom");
        // player.addItem(new Item("feather", "Tungsten Feather", "feather", "feather", 0));
        // player.addItem(new Item("light", "light", "light", "light", 0));
        commandParser = new CommandParser();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to The Pale. You were a prisoner that was left in this dungeon after it was abandoned but there is a monster that awaits you. Your goal is to find the pale pendant and escape to the pale forest.");
        System.out.println("The commands for this game are:\n   Look, go [direction], take [item], drop [item], use [item], inventory, and help. \nHave fun!");
        Room currentRoom = rooms.get(player.getCurrentRoomId());
        System.out.println(currentRoom.getLongDescription(player));

        while (player.isAlive()) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            commandParser.parse(input, player, rooms, scanner);
        }

        System.out.println("**** You have died ****");
        System.out.println("Score: " + player.getScore());

        scanner.close();
    }
}
