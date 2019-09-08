import java.util.*;


//define exception classes

class GameWinnerException extends Exception
{
    public GameWinnerException(String message)
    {
        super(message);
    }
}

class SnakeBiteException extends Exception
{
    public SnakeBiteException(String message)
    {
        super(message);
    }
}


class VultureBiteException extends Exception
{
    public VultureBiteException(String message)
    {
        super(message);
    }
}

class CricketBiteException extends Exception
{
    public CricketBiteException(String message)
    {
        super(message);
    }
}

class TrampolineBiteException extends Exception
{

    public TrampolineBiteException(String messgae)
    {
        super(messgae);
    }
}

class WhiteTileException extends Exception
{
    public WhiteTileException(String message)
    {
        super(message);
    }
}


abstract class Tile
{
    abstract void shake() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineBiteException, WhiteTileException;
}

class Game_tile extends Tile
{
    private final String tile_type;
    private final int tile_to_jump;
    private boolean direction; //true => forward

    //getters and setters coming up ahead

    /**
     * @return the tile_to_jump
     */
    public int getTile_to_jump() {
        return tile_to_jump;
    }

    /**
     * @return the direction
     */
    public boolean isDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * @return the tile_type
     */
    public String getTile_type() {
        return tile_type;
    }

    public Game_tile(int x, int snake, int vulture, int cricket, int trampoline)
    {
        switch(x)
        {
            case 1: this.tile_type = "snake";
                    this.tile_to_jump = snake;
                    this.setDirection(false);
                break;
            case 2: this.tile_type = "vulture";
                    this.tile_to_jump = vulture;
                    this.setDirection(false);
                break;
            case 3: this.tile_type = "cricket";
                    this.tile_to_jump = cricket;
                    this.setDirection(false);
                break;
            case 4: this.tile_type = "trampoline";
                    this.tile_to_jump = trampoline;
                    this.setDirection(true);
                break;
            default:this.tile_type = "white";
                    this.setDirection(true);
                    this.tile_to_jump = 0;
        }
    }

    @Override
    public void shake() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineBiteException, WhiteTileException
    {
        System.out.println("\t Trying to shake the tile ");
        
        if(this.getTile_type().equals("snake"))
        {
            throw new SnakeBiteException("Hiss! I am a snake, you go back "+ this.getTile_to_jump()+" Tile");
        }

        else if(this.getTile_type().equals("vulture"))
        {
            throw new VultureBiteException("Yapping...! I am a vulture, you go back "+ this.getTile_to_jump()+" Tile");
        }

        else if(this.getTile_type().equals("cricket"))
        {
            throw new CricketBiteException( "Chirp...! I am a cricket, you go back "+ this.getTile_to_jump()+" Tile" );
        }

        else if( this.getTile_type().equals("trampoline"))
        {
            throw new TrampolineBiteException("PingPong...! I am a Trampoline, you advance "+ this.getTile_to_jump()+" Tile");
        }
        else
        {
            throw new WhiteTileException("I am a White tile");
        }
    }
}


class White_tile extends Game_tile
{
    public White_tile(int snake, int vulture, int cricket, int Trampoline)
    {
        super( 5, snake, vulture, cricket, Trampoline);
    }
}

class Snake_tile extends Game_tile
{
    public Snake_tile(int snake, int vulture, int cricket, int Trampoline)
    {
        super( 1, snake, vulture, cricket, Trampoline);
    }
}

class Vulture_tile extends Game_tile
{
    public Vulture_tile(int snake, int vulture, int cricket, int Trampoline)
    {
        super( 2, snake, vulture, cricket, Trampoline);
    }
}

class Cricket_tile extends Game_tile
{
    public Cricket_tile(int snake, int vulture, int cricket, int Trampoline)
    {
        super( 3, snake, vulture, cricket, Trampoline);
    }
}

class Trampoline_tile extends Game_tile
{
    public Trampoline_tile(int snake, int vulture, int cricket, int Trampoline)
    {
        super( 4, snake, vulture, cricket, Trampoline);
    }
}

class Dice
{
   public int Roll()
   {
       Random rand = new Random();
       int num = rand.nextInt(6)+1;
       return num;
   }
}


class Gameplay_Engine
{
    private Dice mydice;
    private int num_rolls;
    private final String player_name;
    private final int game_board_length;
    private int current_position;
    private boolean game_won;
    private int num_snake_tiles;
    private int num_vulture_tiles;
    private int num_cricket_tiles;
    private int num_trampoline_tiles;
    private int num_white_tiles;
    private ArrayList<Game_tile> GameBoard = new ArrayList<Game_tile>();

    private int snake_bites=0;
    private int vulture_bites=0;
    private int cricket_bites=0;
    private int Trampoline_bites=0;
    private boolean inside_cage = true;


    /**
     * @return the game_won
     */
    public boolean isGame_won() {
        return game_won;
    }

    /**
     * @return the inside_cage
     */
    public boolean isInside_cage() {
        return inside_cage;
    }

    /**
     * @param inside_cage the inside_cage to set
     */
    public void setInside_cage(boolean inside_cage) {
        this.inside_cage = inside_cage;
    }

    public String take_player_name() throws InputMismatchException
    {
        String name;
        Scanner sc = new Scanner(System.in);
        try
        {
            name = sc.nextLine();
            if(name.equals(""))
            {
                throw new InputMismatchException();
            }

            return name;
        }
        catch(InputMismatchException e)
        {
            System.out.println("Please Enter a correct String literal");
            return take_player_name();
        }
    }


    public int getNumberofTiles()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the total number of tiles on the race track (length)");
        int num_tiles;
        String s = sc.nextLine();
        try
        {
            num_tiles = Integer.parseInt(s);
            return num_tiles;
        }
        catch(Exception e)
        {
            System.out.println("Wrong Input, Try Again");
            return getNumberofTiles();
        }
        
        
    }


    public void Generate_game_layout(int num_tiles)
    {
        Random rand = new Random();
        int snake_go_back = rand.nextInt(5)+5;
        int vulture_go_back = rand.nextInt(5)+1;
        int cricket_go_back = rand.nextInt(3)+1;
        int trampoline_go_forward = rand.nextInt(7)+1;

        Game_tile cage = new White_tile(snake_go_back, vulture_go_back, cricket_go_back, trampoline_go_forward);
        GameBoard.add(cage);
        
        for (int i=0; i<num_tiles; i++)
        {
            int tile_choice = rand.nextInt(100);
            
            if(tile_choice<10)
            {
                //Generate a snake tile
                num_snake_tiles+=1;
                Game_tile mytile = new Snake_tile(snake_go_back, vulture_go_back, cricket_go_back, trampoline_go_forward);
                GameBoard.add(mytile);
            }
            else if(tile_choice>=10 && tile_choice<20)
            {
                //Generate a vulture tile
                num_vulture_tiles+=1;
                Game_tile mytile = new Vulture_tile(snake_go_back, vulture_go_back, cricket_go_back, trampoline_go_forward);
                GameBoard.add(mytile);
            }
            else if(tile_choice>=20 && tile_choice<30)
            {
                num_cricket_tiles+=1;
                Game_tile mytile = new Cricket_tile(snake_go_back, vulture_go_back, cricket_go_back, trampoline_go_forward);
                GameBoard.add(mytile);
            }
            else if(tile_choice>=30 && tile_choice<40)
            {
                num_trampoline_tiles+=1;
                Game_tile mytile = new Trampoline_tile(snake_go_back, vulture_go_back, cricket_go_back, trampoline_go_forward);
                GameBoard.add(mytile);
            }
            else if(tile_choice>=40)
            {
                //generate a white tile
                num_white_tiles+=1;
                Game_tile mytile = new White_tile(snake_go_back, vulture_go_back, cricket_go_back, trampoline_go_forward);
                GameBoard.add(mytile);
            }
        }

        System.out.println("Setting up the race track...");
        System.out.println("Danger: There are "+num_snake_tiles+", "+num_cricket_tiles+", "+num_vulture_tiles+" numbers of Snakes, Crickets and Vultures respectively on your track");
        System.out.println("Danger: Each Snake, Cricket and Vulture can throw you back by "+snake_go_back+", "+cricket_go_back+", "+vulture_go_back+" Number of Tiles respectively");
        System.out.println("Good News: There are "+num_trampoline_tiles+" number of Trampolines on your track!");
        System.out.println("Good News: Each Trampoline can help you advance by "+trampoline_go_forward+" number of Tiles!"); 
    }

    public String Take_Player_Name()
    {
        Scanner sc = new Scanner(System.in);
        String player_name;
        System.out.println("Enter the Player Name");
        try
        {
            player_name = sc.nextLine();
            return player_name;
        }
        catch(NullPointerException e)
        {
            return Take_Player_Name();
        }
    }

    public void Hit_Enter() throws InputMismatchException
    {
        System.out.println("Hit Enter to start the game");
        Scanner sc = new Scanner(System.in);
        String input_string = sc.nextLine();
        try
        {
            if(!input_string.equals(""))
            {
                throw new InputMismatchException("only enter allowed");
            }
            return;
        }
        catch(InputMismatchException e)
        {
            Hit_Enter();
        }
    }

    public Gameplay_Engine()
    {
        this.game_board_length = getNumberofTiles();
        this.Generate_game_layout(this.game_board_length);
        this.game_won = false;
        this.current_position = 0;
        this.mydice = new Dice();
        this.player_name = Take_Player_Name();
        System.out.println("Starting the game with "+player_name+" at Tile 0");
        this.num_rolls = 0;
        System.out.println("Control Transferred to Computer for rolling the Dice for "+this.player_name);
        Hit_Enter();
        System.out.println("Game Started ========================================>");
    }

    public void play_game() throws GameWinnerException
    {
        while(isInside_cage())
        {
            //you are on the cage tile
            int x = this.mydice.Roll();


            if (x==6)
            {
                System.out.println("[Roll "+num_rolls+"]: "+ player_name +" rolled a 6 at Tile 0. You are out of the cage. You get a free roll");
                setInside_cage(false);
                current_position=1;
            } 
            else
            {
                System.out.println("[Roll "+num_rolls+"]: "+ player_name +" rolled a "+ x +" at Tile 0. OOPS, you need a 6 to start!");
                continue;
            }
        }

        if(isInside_cage()==false)
        {
            //you are out of the cage
            int x = this.mydice.Roll();
            this.num_rolls+=1;
            int base_pos = this.current_position;
            this.current_position+=x;
            
            if(this.current_position>game_board_length)
            {
                //The player cannot advance, not enough tiles, make the roll happen again
                System.out.println("[Roll "+num_rolls+"]: "+player_name+" rolled a "+x+" on Tile "+base_pos+" Landed on Tile "+base_pos);
                this.current_position=base_pos;
            }
            else
            {
                //The move is valid
                System.out.println("[Roll "+num_rolls+"]: "+player_name+" rolled a "+x+" on Tile "+base_pos+" Landed on Tile "+current_position);
                
                try
                {
                    Game_tile current_tile = GameBoard.get(current_position);
                    current_tile.shake();
                }
                catch(SnakeBiteException e)
                {
                    System.out.println("\t "+e.getMessage());
                    Game_tile current_tile = GameBoard.get(current_position);
                    int number = current_tile.getTile_to_jump();
                    int base=this.current_position;
                    snake_bites+=1;
                    if( current_tile.isDirection())
                    {
                        this.current_position += number;
                        if(this.current_position>game_board_length)
                        {
                            this.current_position = base;
                        }
                    }
                    else 
                    {
                        this.current_position -= number;
                        if(this.current_position<=0)
                        {
                            setInside_cage(true);
                            this.current_position=0;
                        }
                    }

                    System.out.println("\t "+player_name+" Moved to Tile "+this.current_position);
                }
                catch(VultureBiteException e)
                {
                    System.out.println("\t "+e.getMessage());
                    Game_tile current_tile = GameBoard.get(current_position);
                    int number = current_tile.getTile_to_jump();
                    int base=this.current_position;
                    vulture_bites+=1;
                    if( current_tile.isDirection())
                    {
                        this.current_position += number;
                        if(this.current_position>game_board_length)
                        {
                            this.current_position = base;
                        }
                    }
                    else 
                    {
                        this.current_position -= number;
                        if(this.current_position<=0)
                        {
                            setInside_cage(true);
                            this.current_position=0;
                        }
                    }
                    System.out.println("\t "+player_name+" Moved to Tile "+this.current_position);
                }
                catch(CricketBiteException e)
                {
                    System.out.println("\t "+e.getMessage());
                    Game_tile current_tile = GameBoard.get(current_position);
                    int number = current_tile.getTile_to_jump();
                    int base=this.current_position;
                    cricket_bites+=1;
                    if( current_tile.isDirection())
                    {
                        this.current_position += number;
                        if(this.current_position>game_board_length)
                        {
                            this.current_position = base;
                        }
                    }
                    else 
                    {
                        this.current_position -= number;
                        if(this.current_position<=0)
                        {
                            setInside_cage(true);
                            this.current_position=0;
                        }
                    }
                    System.out.println("\t "+player_name+" Moved to Tile "+this.current_position);
                }
                catch(TrampolineBiteException e)
                {
                    System.out.println("\t "+e.getMessage());
                    Game_tile current_tile = GameBoard.get(current_position);
                    int number = current_tile.getTile_to_jump();
                    int base=this.current_position;
                    Trampoline_bites+=1;
                    if( current_tile.isDirection())
                    {
                        this.current_position += number;
                        if(this.current_position>game_board_length)
                        {
                            this.current_position = base;
                        }
                    }
                    else 
                    {
                        this.current_position -= number;
                        if(this.current_position<=0)
                        {
                            setInside_cage(true);
                            this.current_position=0;
                        }
                    }
                    System.out.println("\t "+player_name+" Moved to Tile "+this.current_position);
                }
                catch(WhiteTileException e)
                {
                    System.out.println("\t "+e.getMessage());
                    Game_tile current_tile = GameBoard.get(current_position);
                    int number = current_tile.getTile_to_jump();
                    int base=this.current_position;

                    if( current_tile.isDirection())
                    {
                        this.current_position += number;
                        if(this.current_position>game_board_length)
                        {
                            this.current_position = base;
                        }
                    }
                    else 
                    {
                        this.current_position -= number;
                        if(this.current_position<=0)
                        {
                            setInside_cage(true);
                            this.current_position=0;
                        }
                    }
                    System.out.println("\t "+player_name+" Moved to Tile "+this.current_position);
                }

                if( this.current_position == this.game_board_length)
                {
                    throw new GameWinnerException("You have Successfully Won the Game");
                }
            }
            
            
        }
    }


    public void Main_gameplay_function()
    {
        while(!game_won)
        {
            try
            {
                play_game();
            }
            catch (GameWinnerException e)
            {
                game_won = true;
                System.out.println("\t\t "+player_name+" Won in "+num_rolls+" Rolls");
                System.out.println("\t\t Total Snake Bites : "+snake_bites);
                System.out.println("\t\t Total Vulture Bites : "+vulture_bites);
                System.out.println("\t\t Total Cricket Bites : "+cricket_bites);
                System.out.println("\t\t Total Trampolines : "+Trampoline_bites);
            }
        }
    }

}

class lab_5
{
    public static void main(String[] args)
    {
        Gameplay_Engine myEngine = new Gameplay_Engine();
        myEngine.Main_gameplay_function();
    }
}